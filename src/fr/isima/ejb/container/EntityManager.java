package fr.isima.ejb.container;

public interface EntityManager {
	void clear();

	boolean contains(Object entity);

	void flush();

	<T> void merge(T entity);

	void persist(Object object);

	void remove(Object object);

	void close();

	void detach();

	boolean isOpen();

	void joinTransaction();
}
