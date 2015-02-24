package fr.isima.ejb.exceptions;

public class UnknowEntityManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  UnknowEntityManagerException(Object theClasse){
		super("la classe"+ theClasse.getClass().getName()+" n'est pas connue.");
	}

}
