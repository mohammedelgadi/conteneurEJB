package fr.isima.ejb.transactionTest;

public interface StatelessEjbInterface {

	public void defaultMethode();
	public void neverMethode();
	public void requiredMethode();
	public void requiredNewMethode();
	
	// for testing if the ejb intern is injected
	public boolean isLocalInjected();
	
	public int getValue();
}
