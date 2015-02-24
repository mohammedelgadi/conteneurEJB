package fr.isima.ejb.transactionTest;

import fr.isima.ejb.annotations.EJB;
import fr.isima.ejb.annotations.PostConstruct;
import fr.isima.ejb.annotations.Stateless;
import fr.isima.ejb.annotations.TransactionAttribute;
import fr.isima.ejb.annotations.TransactionAttribute.Type;

@Stateless
public class EJBStatlessSession implements StatelessEjbInterface {


	@EJB
 	public StatelessEjb2Interface statlessEjb; 


	public int value = 0;
	
	@TransactionAttribute(Type.REQUIRED)
	@Override
	public void requiredMethode() {
		
		
	}
	
	@PostConstruct
	public void changeValue(){
		value = 100;
	}
	
	@TransactionAttribute(Type.REQUIRED)
	@Override
	public void requiredNewMethode() {
		this.statlessEjb.requiredNewMethode();
		
		
	}

	
	
	
	@Override
	public void defaultMethode() {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(Type.NEVER)
	@Override
	public void neverMethode() {
		
	}

	@Override
	public boolean isLocalInjected() {
		if (statlessEjb != null)
			return true;
		return false;
	}

	@Override
	public int getValue() {
		return value;
	}
	


}
