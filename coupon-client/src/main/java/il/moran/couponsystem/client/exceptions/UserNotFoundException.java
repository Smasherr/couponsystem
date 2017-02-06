package il.moran.couponsystem.client.exceptions;



/*
 * UserNotFoundException throws exceptions for User not found
 */
public class UserNotFoundException extends Exception {
	
	
	
	private static final long serialVersionUID = 7179939217576154177L;

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
	
	
}
