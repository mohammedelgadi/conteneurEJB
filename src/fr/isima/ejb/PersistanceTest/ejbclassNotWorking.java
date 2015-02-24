package fr.isima.ejb.PersistanceTest;

import fr.isima.ejb.annotations.PersistenceContext;
import fr.isima.ejb.annotations.Stateful;
import fr.isima.ejb.container.EntityManager;

@Stateful
public class ejbclassNotWorking implements ejbInterface {

	@PersistenceContext(unitName = "unit.persistance.notExistJpaProvider")
	EntityManager entityManager = null;	
	
}
