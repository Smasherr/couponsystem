package il.moran.couponsystem.client.exceptions;

/*
 * ManagerSQLException throws exceptions for SQL
 */

public class ManagerSQLException extends Exception {

	
	private static final long serialVersionUID = -3364757945540126205L;

	public ManagerSQLException() {
		super();
	}

	public ManagerSQLException(String msg) {
		super(" SQL Manager error: " + msg);
	}
}
