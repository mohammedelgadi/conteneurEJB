package fr.isima.ejb.container;

import java.util.HashMap;
import java.util.Map;
import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;

public class Client {
	private Object client;
	private Map<Class<?>, Object> mapBeans;

	public Client(Object client) {
		this.client = client;
		mapBeans = new HashMap<Class<?>, Object>();
	}

	public Object getClient() {
		return client;
	}

	public Object getBean(Class<?> inBeanClass) throws Exception {
		if (mapBeans.containsKey(inBeanClass))
			return mapBeans.get(inBeanClass);

		Object bean = EjbPool.getInstance().create(inBeanClass);
		mapBeans.put(inBeanClass, bean);
		return bean;
	}

	public void release() throws invokeMethodsByAnnotationException {
		for (Object bean : mapBeans.values()) {
			EjbPool.getInstance().release(bean);
		}
	}

	public int getSize() {
		return mapBeans.size();
	}

}
