package fr.isima.ejb.singletonTest;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.container.EJBContainer;
import fr.isima.ejb.exceptions.InterfaceAlreadyImplementedException;
import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;
import fr.isima.ejb.statefulTest.StatefulEjbInterface;

public class EjbSingletonTest {


	@EJB
	SingletonEjbInterface ejbSingleton1;
	
	@EJB
	SingletonEjbInterface ejbSingleton2;
	
	@EJB
	StatefulEjbInterface ejbStateful;
	
	public EjbSingletonTest() throws InterfaceAlreadyImplementedException, Exception {
		
		EJBContainer.getInstance().inject(this);
		
		
	}
	
	@Before
	public void injectingTest() throws InterfaceAlreadyImplementedException, Exception{
		//EJBContainer.getInstance().inject(this);
	}
	
	@Test 
	public void BeanCreatedInPool(){
		// deux EJB sont creer dans la Base de donnée
		//assertEquals(EjbPool.getInstance().getBeans().size(),3);
	}
	
	@Test
	public void testInjection(){
		assertNotNull(ejbSingleton1);
		assertNotNull(ejbSingleton2);
	}
	
	@Test
	public void testSingletonUnicity() throws  Exception{
		assertSame(ejbSingleton1,ejbSingleton2);
	}
	
	@Test
	public void testSingletonWithMultipleClients() throws  Exception
	{
		ServletClient servlet = new ServletClient();
		EJBContainer.getInstance().inject(servlet);
		assertSame(ejbSingleton1,servlet.ejbSingleton1);
	}
	
	@Test 
	public void testEntityManagerInjection(){
		assertNotNull(ejbSingleton1.getEntityManager());
	}
	
	/*
	
	@Test
	public void TestInjectionPersistanceManager() throws InterfaceAlreadyImplementedException, Exception{
		assertSame(ejbSingleton1.getEntityManager(), null);
		//assertNotEquals(EJBContainer.getInstance().mapEntityManagers.size(),0);
	}
	*/
	
	@Test
	public void TestInjectionFonctionality(){
		// pas le méme proxy utilisé
		assertNotSame(ejbSingleton1,ejbStateful);
	}
	
	@Test
	public void EjbTestDestruct() throws invokeMethodsByAnnotationException, InterfaceAlreadyImplementedException, IllegalArgumentException, IllegalAccessException{	
		EJBContainer.getInstance().descructEJB(this);
		assertSame(ejbSingleton1,null);
		assertSame(ejbSingleton2,null);
	}
	
	@Test  
	public void PersistanceUnitMappings() {  
		assertTrue(true);
	}
	
	
	@After
	public void dejinctingTest() throws IllegalArgumentException, IllegalAccessException, invokeMethodsByAnnotationException, InterfaceAlreadyImplementedException{
		EJBContainer.getInstance().descructEJB(this);
	}
	

	

	
	


}
