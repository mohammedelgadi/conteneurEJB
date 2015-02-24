package fr.isima.ejb.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;

public class ReflectionServices {

	public static void invokeMethodsByAnnotation(Object object,
			Class<? extends Annotation> annotation)
			throws invokeMethodsByAnnotationException {
		// Retrieve all methods annotated with the given annotation
		Reflections reflections = new Reflections(object.getClass().getName(),
				new MethodAnnotationsScanner());
		Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);

		// Invoking them
		for (Method method : methods) {
			try {
				method.invoke(object, new Object[] {});
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new invokeMethodsByAnnotationException(method);
			}
		}
	}

	public static boolean isClassAnnotatedWith(Class<?> theClass,
			Class<?> annotation) {
		Annotation[] typeAnnotations = theClass.getDeclaredAnnotations();
		for (Annotation typeAnnotation : typeAnnotations) {
			if (annotation.isInstance(typeAnnotation))
				return true;
		}
		return false;
	}
}
