package mro.base.sso;

/**
 * Main exception thrown by classes in this package. May contain an error
 * message and/or another nested exception.
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = 5674766183988387738L;

	/**
	 * Constructs a new instance of this class with <code>null</code> as its
	 * detail message.
	 */
	public LoginException() {
		super();
	}

	/**
	 * Constructs a new instance of this class with the specified detail
	 * message.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public LoginException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of this class with the specified detail message
	 * and root cause.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 * @param rootCause
	 *            root failure cause
	 */
	public LoginException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	/**
	 * Constructs a new instance of this class with the specified root cause.
	 * 
	 * @param rootCause
	 *            root failure cause
	 */
	public LoginException(Throwable rootCause) {
		super(rootCause);
	}
}
