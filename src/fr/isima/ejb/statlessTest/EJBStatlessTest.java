package fr.isima.ejb.statlessTest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.container.EJBContainer;
import fr.isima.ejb.exceptions.InterfaceAlreadyImplementedException;
import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;

public class EJBStatlessTest {

	
	@EJB
	StatelessEjbInterface statlessEjb1; 
	
	@EJB
	StatelessEjbInterface statlessEjb2; 
	
	public EJBStatlessTest() throws Exception{
		EJBContainer.getInstance().inject(this);
	}
	
	@PostConstruct
	public void injectingTest() throws InterfaceAlreadyImplementedException, Exception{
		//EJBContainer.getInstance().inject(this);
	}
	
	@PreDestroy
	public void dejinctingTest() throws IllegalArgumentException, IllegalAccessException, invokeMethodsByAnnotationException, InterfaceAlreadyImplementedException {
		EJBContainer.getInstance().descructEJB(this);	
	}

}
