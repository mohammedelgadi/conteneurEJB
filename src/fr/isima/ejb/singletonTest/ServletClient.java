package fr.isima.ejb.singletonTest;

import fr.isima.ejb.annotations.EJB;

public class ServletClient /*implements SingletonEjbInterface*/ {
	

	
	@EJB
	SingletonEjbInterface ejbSingleton1;
	
	@EJB
	SingletonEjbInterface ejbSingleton2;



}
