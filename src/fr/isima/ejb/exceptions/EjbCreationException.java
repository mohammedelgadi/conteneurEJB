package fr.isima.ejb.exceptions;

public class EjbCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EjbCreationException( Class<?> theClass ) {
        super( "Impossible de cr�er une nouvelle instance de " + theClass.getName() );
    }

}
