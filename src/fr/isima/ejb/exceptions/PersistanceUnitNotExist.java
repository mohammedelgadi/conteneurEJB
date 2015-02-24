package fr.isima.ejb.exceptions;

public class PersistanceUnitNotExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  PersistanceUnitNotExist(Object theClasse){
		super("la classe"+ theClasse.getClass().getName()+" n'est pas connue.");
	}
	
	
}
