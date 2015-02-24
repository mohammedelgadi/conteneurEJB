package fr.isima.ejb.container;

import java.lang.reflect.Proxy;

public class ProxyFactory {

	public static Object createWithBean(Object bean) {

		Class<?> ejbClass = bean.getClass();
		Class<?>[] interfaces = ejbClass.getInterfaces();
		EjbHandler handler = new EjbHandler(bean);
		Object proxy = Proxy.newProxyInstance(ejbClass.getClassLoader(),
				interfaces, handler);
		return proxy;
	}
}
