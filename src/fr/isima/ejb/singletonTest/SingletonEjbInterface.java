package fr.isima.ejb.singletonTest;



import fr.isima.ejb.container.EntityManager;


public interface SingletonEjbInterface  {
	
	
	public void businessMethode1();
	public void businessMethode2();
	
	public EntityManager getEntityManager();
	

}
