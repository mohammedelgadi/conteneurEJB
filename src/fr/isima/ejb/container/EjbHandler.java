package fr.isima.ejb.container;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import fr.isima.ejb.annotations.TransactionAttribute;

public class EjbHandler implements InvocationHandler {

	// EJB bean instance
	private Object bean;

	// EJB bean class
	private Class<?> beanClass;

	private static TransactionManager transactionManager = new TransactionManager();

	public Transaction transaction = null;

	TransactionAttribute.Type classTransactionType;

	public EjbHandler(Object bean) {
		setBean(bean);
	}

	private void beginInvoke(TransactionAttribute.Type transactionType) {
		try {
			PersistanceManager.persistanceOnCall(this.bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (transactionType) {
		case NEVER:
			break;

		case REQUIRED:
			if (!transactionManager.hasOne()) {
				transaction = new Transaction(bean);
				transaction.begin();
				transactionManager.push(transaction);
			}
			break;

		case REQUIRES_NEW:
			transactionManager.sleep();
			transaction = new Transaction(bean);
			transaction.begin();
			transactionManager.push(transaction);
			break;
		}
	}

	private void endInvoke(TransactionAttribute.Type transactionType) {
		switch (transactionType) {
		case NEVER:
			break;

		case REQUIRED:
			if (transaction != null
					&& transactionManager.first() == transaction)
				transactionManager.pop().end();
			break;

		case REQUIRES_NEW:
			transactionManager.pop().end();
			if (transactionManager.hasOne())
				transactionManager.awake();
			break;
		}
		try {
			PersistanceManager.persistancePostCall(this.bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TransactionAttribute.Type getTransactionAttribute(Method method)
			throws NoSuchMethodException, SecurityException {
		// Retrieving the original method
		Class<?>[] params = method.getParameterTypes();
		Method beanMethod = beanClass.getMethod(method.getName(),
				(params.length > 0 ? params : null));
		TransactionAttribute methodAttribute = beanMethod
				.getAnnotation(TransactionAttribute.class);

		// Extract the current transaction attribute
		if (methodAttribute == null)
			return classTransactionType;
		else
			return methodAttribute.value();
	}

	@Override
	public Object invoke(Object inPoxy, Method method, Object[] arguments)
			throws Exception {

		// post traitement
		Object value;

		Method realMethod = null;
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();
		realMethod = beanClass.getDeclaredMethod(methodName, parameterTypes);

		// Manage begin of transaction
		TransactionAttribute.Type transactionType = getTransactionAttribute(method);
		beginInvoke(transactionType);

		try {
			// Invoke it
			value = realMethod.invoke(bean, arguments);
		} catch (Exception e) {
			throw e;
		} finally {
			// pretraitement
			endInvoke(transactionType);
		}

		return value;
	}

	public void setBean(Object bean) {
		this.bean = bean;
		beanClass = bean.getClass();

		TransactionAttribute classTransaction = beanClass
				.getAnnotation(TransactionAttribute.class);
		if (classTransaction == null)
			classTransactionType = TransactionAttribute.Type.NEVER;
		else
			classTransactionType = classTransaction.value();

	}

	public Object getBean() {
		return bean;
	}

}
