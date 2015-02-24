package fr.isima.ejb.statefulTest;

import fr.isima.ejb.annotations.EJB;

public class ServletClientOne {

	
	@EJB
	StatefulEjbInterface ejb1 = null;
	
	@EJB
	StatefulEjbInterface ejb2 = null;
	
	public ServletClientOne(){
		
	}
	
}
