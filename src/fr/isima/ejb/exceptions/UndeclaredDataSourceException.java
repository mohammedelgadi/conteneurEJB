package fr.isima.ejb.exceptions;

public class UndeclaredDataSourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UndeclaredDataSourceException(String dataSourceName){
		super("la DataSource + "+dataSourceName+" n'est pas declarée dans le fichier persistance.xml");
	}
	

}
