package fr.isima.ejb.singletonTest;

import java.util.logging.Logger;

import fr.isima.ejb.container.EntityManager;



public class EntityManagerImplementation implements EntityManager {

	
	Logger LOG = Logger.getLogger(this.getClass().getName());
	
	public EntityManagerImplementation() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object entity) {
		
		
		return false;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void merge(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(Object object) {
		
		LOG.info("Persistance of the object");
		
	}

	@Override
	public void remove(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

}
