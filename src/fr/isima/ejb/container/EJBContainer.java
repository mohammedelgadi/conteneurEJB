package fr.isima.ejb.container;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.annotations.PersistenceContext;
import fr.isima.ejb.annotations.PreDestroy;
import fr.isima.ejb.annotations.Singleton;
import fr.isima.ejb.annotations.Stateful;
import fr.isima.ejb.annotations.Stateless;
import fr.isima.ejb.exceptions.*;

public class EJBContainer {
	/* instance singleton */
	private static EJBContainer INSTANCE = null;
	Logger LOG = Logger.getLogger(this.getClass().getName());
	private Map<Class<?>, Object> mapSingletonInstances; // Map des singletons
															// de l'EJBContainer
	private Map<Class<?>, Class<?>> mapEjbImplementations; // Map des interfaces
															// EJB et de leurs
															// impl�mentations
	public ClientManager clientManager; // Continent la liste des clients pour
										// gerer les EJB statful

	private EJBContainer() throws InterfaceAlreadyImplementedException {
		mapSingletonInstances = new HashMap<Class<?>, Object>();
		mapEjbImplementations = new HashMap<Class<?>, Class<?>>();
		// mapDataSourceENtityManager = new HashMap<String,Class<? extends
		// EntityManager>>();
		clientManager = new ClientManager();
		bootstrapInit();
	}

	private void bootstrapInit() throws InterfaceAlreadyImplementedException {
		Reflections reflections = new Reflections();

		// Scan all classes of the classloader having annotations (Singleton _
		// Statefull _ Stateless)

		Set<Class<?>> classes = reflections
				.getTypesAnnotatedWith(Singleton.class);
		classes.addAll(reflections.getTypesAnnotatedWith(Stateful.class));
		classes.addAll(reflections.getTypesAnnotatedWith(Stateless.class));
		for (Class<?> ejbImplementation : classes) {
			Class<?> interfaces[] = ejbImplementation.getInterfaces();
			for (Class<?> ejbInterface : interfaces) {
				if (mapEjbImplementations.containsKey(ejbInterface)) {
					throw new InterfaceAlreadyImplementedException(ejbInterface);
				}
				mapEjbImplementations.put(ejbInterface, ejbImplementation);
			}
		}
	}

	public void inject(Object theObject) throws Exception {
		
	
		theObject = EjbPool.getInstance().getBeanFromProxy(theObject);
		// Scan all classes having EJB and inject there EJB implementations
		Class<? extends Object> objectClass = theObject.getClass();

		// Search all fields annotated with EJB annotation
		Reflections reflections = new Reflections(objectClass.getName(),
				new FieldAnnotationsScanner());
		Set<Field> fields = reflections.getFieldsAnnotatedWith(EJB.class);
		// Inject them all
		for (Field field : fields) {
			injectEJB(theObject, field);
			field.setAccessible(true);
			this.inject(field.get(theObject));
		}
	}

	public void injectEJB(Object theObject, Field field) throws Exception {
		// Search the implementation of the interface
		Class<?> fieldClass = field.getType();

		if (mapEjbImplementations.containsKey(fieldClass)) {

			fieldClass = mapEjbImplementations.get(fieldClass);
		} else {
			throw new UnknowEjbInterfaceException(fieldClass);
		}

		Object instance = createProxyInstance(fieldClass, theObject);

		// Inject the instance
		try {
			field.setAccessible(true);
			field.set(theObject, instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new EjbInjectionException(field);
		}
	}

	public Object createProxyInstance(Class<?> ejbClass, Object theClient)
			throws Exception {
		Object bean = null;
		// Manage stateful behavior
		if (ReflectionServices.isClassAnnotatedWith(ejbClass, Stateful.class)) {
			bean = clientManager.getClient(theClient).getBean(ejbClass);
		}
		// Manage stateless behavior
		else if (ReflectionServices.isClassAnnotatedWith(ejbClass,
				Stateless.class)) {
			bean = EjbPool.getInstance().create(ejbClass);
		}
		// Manage singleton behavior
		else if (ReflectionServices.isClassAnnotatedWith(ejbClass,
				Singleton.class)) {
			// If the instance of the singleton already exists, get it
			if (mapSingletonInstances.containsKey(ejbClass)) {
				bean = mapSingletonInstances.get(ejbClass);
			} else // Else, instantiate it in the map
			{
				bean = EjbPool.getInstance().create(ejbClass);
				mapSingletonInstances.put(ejbClass, bean);
			}
		}
		return bean;
	}

	public void descructEJB(Object theObject)
			throws invokeMethodsByAnnotationException,
			IllegalArgumentException, IllegalAccessException {
		ReflectionServices.invokeMethodsByAnnotation(theObject,
				PreDestroy.class);
		disInjectPersistanceContext(theObject);
		disInjectEjb(theObject);
	}

	public void disInjectEjb(Object theObject) throws IllegalArgumentException,
			IllegalAccessException, invokeMethodsByAnnotationException {
		// Search all fields annotated with @EJB
		Reflections reflections = new Reflections(theObject.getClass()
				.getName(), new FieldAnnotationsScanner());
		Set<Field> fields = reflections.getFieldsAnnotatedWith(EJB.class);

		// Remove them all
		for (Field field : fields) {
			// Desinjection of ejb imbriqued
			this.descructEJB(field);
			field.setAccessible(true);
			field.set(theObject, null);
		}
	}

	public void disInjectPersistanceContext(Object theObject)
			throws IllegalArgumentException, IllegalAccessException {

		theObject = EjbPool.getInstance().getBeanFromProxy(theObject);
		Reflections reflections = new Reflections(theObject.getClass()
				.getName(), new FieldAnnotationsScanner());
		Set<Field> fields = reflections
				.getFieldsAnnotatedWith(PersistenceContext.class);

		// Remove them all
		for (Field field : fields) {
			field.setAccessible(true);
			field.set(theObject, null);
		}
	}

	/* methode getInstance of singleton pattern */
	public static synchronized EJBContainer getInstance()
			throws InterfaceAlreadyImplementedException {
		if (INSTANCE == null)
			INSTANCE = new EJBContainer();
		return INSTANCE;
	}

}
