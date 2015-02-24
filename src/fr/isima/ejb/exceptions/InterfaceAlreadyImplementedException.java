package fr.isima.ejb.exceptions;

public class InterfaceAlreadyImplementedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InterfaceAlreadyImplementedException( Class<?> theinterface ) {
        super( "l interface " + theinterface.getName() + " est déjà implementé " );
    }

}
