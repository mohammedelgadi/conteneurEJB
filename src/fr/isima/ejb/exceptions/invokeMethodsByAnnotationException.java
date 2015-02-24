package fr.isima.ejb.exceptions;

import java.lang.reflect.Method;

public class invokeMethodsByAnnotationException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public invokeMethodsByAnnotationException(Method method) {
		 super( "La methode " + method.getName() + " ne peux pas etre invoke" );
	}


}
