package fr.isima.ejb.statlessTest;

import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.annotations.PersistenceContext;
import fr.isima.ejb.annotations.Stateless;
import fr.isima.ejb.container.EntityManager;

@Stateless
public class EJBStatlessSession implements StatelessEjbInterface {

	@EJB
	StatelessEjbInterface statlessEjb = null; 
	
	@PersistenceContext(unitName = "unit.persistance.statlessJpaProvider")
	EntityManager entityManager = null;
	
	@Override
	public void businessMethode1() {
		System.out.println("Calling buisness method 1");
	}

	@Override
	public void businessMethode2() {
		System.out.println("Calling buisness method 2");
	}



}
