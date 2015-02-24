package fr.isima.ejb.statefulTest;

import fr.isima.ejb.annotations.PersistenceContext;
import fr.isima.ejb.annotations.Stateful;
import fr.isima.ejb.container.EntityManager;


@Stateful
public class EJBStatefulSession implements StatefulEjbInterface {

	@PersistenceContext(unitName = "unit.persistance.statefulJpaProvider")
	EntityManager entityManager = null;	
	
	@Override
	public void businessMethode1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void businessMethode2() {
		// TODO Auto-generated method stub
		
	}

}
