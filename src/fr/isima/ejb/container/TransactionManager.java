package fr.isima.ejb.container;

import java.util.Stack;

public class TransactionManager {

	private Stack<Transaction> transactions;

	public TransactionManager() {
		transactions = new Stack<>();
	}

	public void push(Transaction inTransaction) {
		transactions.push(inTransaction);
	}

	public Transaction pop() {
		return transactions.pop();
	}

	public Transaction first() {
		if (transactions.empty())
			return null;
		return transactions.firstElement();
	}

	public boolean hasOne() {
		return !transactions.empty();
	}

	public int size() {
		return transactions.size();
	}

	public void sleep() {
		if (!transactions.empty()) {
			transactions.firstElement().sleep();
		}
	}

	public void awake() {
		if (!transactions.empty()) {
			transactions.firstElement().awake();
		}
	}
}
