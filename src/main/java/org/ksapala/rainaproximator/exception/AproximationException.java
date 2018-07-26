/**
 * 
 */
package org.ksapala.rainaproximator.exception;

/**
 * @author krzysztof
 *
 */
public class AproximationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8370595903688409511L;

	/**
	 * 
	 */
	public AproximationException() {
	}

	/**
	 * @param message
	 */
	public AproximationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AproximationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AproximationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AproximationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
