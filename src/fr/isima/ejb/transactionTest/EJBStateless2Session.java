package fr.isima.ejb.transactionTest;

import fr.isima.ejb.annotations.Stateless;
import fr.isima.ejb.annotations.TransactionAttribute;
import fr.isima.ejb.annotations.TransactionAttribute.Type;

@Stateless
public class EJBStateless2Session implements StatelessEjb2Interface {
	


	@TransactionAttribute(Type.REQUIRES_NEW)
	@Override
	public void requiredNewMethode() {
		
	}

}
