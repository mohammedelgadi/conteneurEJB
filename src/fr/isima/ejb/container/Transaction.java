package fr.isima.ejb.container;

import java.util.logging.Logger;

public class Transaction {
	private String beanClassTransaction;
	private Class<?> beanClass;

	public Transaction(Object bean) {
		beanClassTransaction = "Transaction " + bean.getClass().getSimpleName()
				+ "\t";
		beanClass = bean.getClass();
	}

	public void begin() {
		Logger.getLogger(beanClass.getName()).info(
				beanClassTransaction + "Create");
	}

	public void sleep() {
		Logger.getLogger(beanClass.getName()).info(
				beanClassTransaction + "Sleep");
	}

	public void awake() {
		Logger.getLogger(beanClass.getName()).info(
				beanClassTransaction + "Awake");
	}

	public void end() {
		Logger.getLogger(beanClass.getName())
				.info(beanClassTransaction + "End");
	}
}