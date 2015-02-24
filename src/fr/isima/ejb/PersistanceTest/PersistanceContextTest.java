package fr.isima.ejb.PersistanceTest;
import org.junit.Test;
import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.container.EJBContainer;
import fr.isima.ejb.exceptions.PersistanceUnitNotExist;

public class PersistanceContextTest {

	
	@EJB
	ejbInterface ejbStatless = null;
	
	@Test(expected = PersistanceUnitNotExist.class)  
	public void notDeclaredPersistanceContextText() throws Exception {
		EJBContainer.getInstance().inject(this);
	}
	
	

}
