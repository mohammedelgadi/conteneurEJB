package fr.isima.ejb.container;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import fr.isima.ejb.annotations.PostConstruct;
import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;

public class EjbPool {

	private static EjbPool Instance = null;

	public static synchronized EjbPool getInstance() {
		if (Instance == null)
			Instance = new EjbPool();
		return Instance;
	}

	private Map<Class<?>, Stack<Object>> beans;

	public Map<Class<?>, Stack<Object>> getBeans() {
		return beans;
	}

	private EjbPool() {
		beans = new HashMap<Class<?>, Stack<Object>>();
	}

	protected void verifyMapKey(Class<?> beanClass) {
		// Check if the list exists
		if (!beans.containsKey(beanClass))
			beans.put(beanClass, new Stack<Object>());
	}

	public void release(Object bean) throws invokeMethodsByAnnotationException {
		Class<?> classBean = bean.getClass();
		verifyMapKey(classBean);
		beans.get(classBean).push(bean);
	}

	public Object create(Class<?> beanClass) throws Exception {
		verifyMapKey(beanClass);
		Object bean = null;
		// If there is at least one bean, return it
		if (beans.get(beanClass).size() > 0) {
			bean = beans.get(beanClass).pop();
		} else {
			bean = beanClass.newInstance();
			PersistanceManager.persistanceOnCreate(bean);
			bean = ProxyFactory.createWithBean(bean);
		}
		ReflectionServices.invokeMethodsByAnnotation(
				this.getBeanFromProxy(bean), PostConstruct.class);
		return bean;
	}

	public Object getBeanFromProxy(Object theObject) {
		if (theObject instanceof Proxy) {
			InvocationHandler handler = Proxy.getInvocationHandler(theObject);
			if (handler instanceof EjbHandler) {
				EjbHandler beanHandler = (EjbHandler) handler;
				theObject = beanHandler.getBean();
			}
		}
		return theObject;
	}

}
