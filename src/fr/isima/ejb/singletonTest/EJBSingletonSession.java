package fr.isima.ejb.singletonTest;

import java.util.logging.Logger;

import fr.isima.ejb.annotations.PersistenceContext;
import fr.isima.ejb.annotations.Singleton;
import fr.isima.ejb.container.EntityManager;

@Singleton
public class EJBSingletonSession implements SingletonEjbInterface {

	
	Logger LOG = Logger.getLogger(this.getClass().getName());

	
	
	@PersistenceContext(unitName = "unit.persistance.singletonJpaProvider")
	EntityManager entityManager = null;
	
	

	@Override
	public void businessMethode1() {
		LOG.info("**** Calling buisness method 1 ******");
		entityManager.persist(new Object());
		LOG.info("**** end of calling buisness method 1 ******");

		System.out.println("**** end of calling buisness method 1 ******");
		
		
	}

	@Override
	public void businessMethode2() {
		
		System.out.println("Calling buisness method 2");
		
	}


	@Override
	public EntityManager getEntityManager() {
		return entityManager;
		
	}

}
