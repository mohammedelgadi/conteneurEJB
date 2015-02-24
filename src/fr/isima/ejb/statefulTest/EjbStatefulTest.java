package fr.isima.ejb.statefulTest;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.isima.ejb.container.EJBContainer;
import fr.isima.ejb.exceptions.InterfaceAlreadyImplementedException;
import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;


public class EjbStatefulTest {

	ServletClientOne client1 = new ServletClientOne();
	ServletClientTwo client2 = new ServletClientTwo();

	
	public EjbStatefulTest() throws InterfaceAlreadyImplementedException, Exception{
		EJBContainer.getInstance().inject(client1);
		EJBContainer.getInstance().inject(client2);
		EJBContainer.getInstance().inject(client2);
	}
	
	
	@Test
	public void testInjectionEjbClient1(){
		assertNotNull(client1.ejb1);
		assertNotNull(client1.ejb2);
	}
	
	@Test
	public void testInjectionEjbClient2(){
		assertNotNull(client2.ejb1);
		assertNotNull(client2.ejb2);
	}
	
	@Test
	public void testInjectionClients() {
		assertNotNull(client1.ejb1);
		assertNotNull(client2.ejb1);
	}
	
	@Test
	public void differentsClientsCreated(){
		assertNotSame(client1,client2);
	}
	

	@Test
	public void ejbTestDestruct() throws invokeMethodsByAnnotationException, InterfaceAlreadyImplementedException, IllegalArgumentException, IllegalAccessException{	
		// destruction of the Ejb singleton
		EJBContainer.getInstance().descructEJB(client1);
		EJBContainer.getInstance().descructEJB(client2);
		// Testing dejinction of clients
		assertSame(client1.ejb1,null);
		assertSame(client1.ejb2,null);
	}
	
	@Test
	public void testClientManager() throws InterfaceAlreadyImplementedException{
		assertEquals(EJBContainer.getInstance().clientManager.getClient(client2).getSize(), 1);
	}

}
