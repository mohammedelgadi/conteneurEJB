package fr.isima.ejb.transactionTest;

import java.util.logging.Logger;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.container.EJBContainer;
import fr.isima.ejb.exceptions.InterfaceAlreadyImplementedException;
import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;


public class TransactionTest {
	
	
	@EJB
	StatelessEjbInterface statlessEjb; 
	public TransactionTest() throws InterfaceAlreadyImplementedException, Exception{
			EJBContainer.getInstance().inject(this);
	}
	
	@Test
	public void EjbTestConstruction() throws invokeMethodsByAnnotationException, InterfaceAlreadyImplementedException, IllegalArgumentException, IllegalAccessException{	
		assertNotNull(statlessEjb);
	}
	
	@Test
	public void testInvokPostConstructMethods(){	
		assertEquals(statlessEjb.getValue(), 100);
	}
	
	@Test
	public void testeDefault() throws Exception {
		Logger.getLogger(this.getClass().getName()).info(" ************ TEST DEFAULT AND NEVER **********");
		statlessEjb.defaultMethode();
		statlessEjb.neverMethode();
		Logger.getLogger(this.getClass().getName()).info(" ************ END **********");
		
		
	}
	@Test
	public void testeRequired() throws Exception {
		Logger.getLogger(this.getClass().getName()).info(" ************ TEST REQUIRED **********");
		statlessEjb.requiredMethode();
		statlessEjb.requiredMethode();
		Logger.getLogger(this.getClass().getName()).info(" ************ END **********");
	}
	

	@Test
	public void testeRquired() throws Exception {
		Logger.getLogger(this.getClass().getName()).info(" ************ TEST REQUIREDNEW**********");
		statlessEjb.requiredNewMethode();
		Logger.getLogger(this.getClass().getName()).info(" ************ END **********");
		}
	
	@Test
	public void testLocalInjection(){
		//testing local injection
		assertTrue(statlessEjb.isLocalInjected());
	}
	
	@Test
	public void EjbTestDestruction() throws invokeMethodsByAnnotationException, InterfaceAlreadyImplementedException, IllegalArgumentException, IllegalAccessException{	
		EJBContainer.getInstance().descructEJB(this);
		assertSame(statlessEjb,null);
	}

}
