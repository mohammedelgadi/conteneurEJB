package fr.isima.ejb.exceptions;

import java.lang.reflect.Field;

public class EjbInjectionException extends Exception {
	
	private static final long serialVersionUID = 7066075608635102983L;

    public EjbInjectionException( Field field ) {
        super( "impossible d'injecter l'instance EJB dans le field " + field );
    }
}
