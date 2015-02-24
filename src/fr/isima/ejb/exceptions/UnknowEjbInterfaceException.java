package fr.isima.ejb.exceptions;

public class UnknowEjbInterfaceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnknowEjbInterfaceException(Class<?> theInterface ){
		super( "interface Ejb inconue " + theInterface.getName() );
	}

}
