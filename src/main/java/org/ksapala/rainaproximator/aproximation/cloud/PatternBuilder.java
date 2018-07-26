package org.ksapala.rainaproximator.aproximation.cloud;

import java.util.Arrays;

/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class PatternBuilder {

	/**
	 * @param size
	 * @return
	 */
	public boolean[] buildMiddleTrue(int size) {
		boolean[] result = buildMiddle(size, true);
		return result;
    }

	/**
	 * @param size
	 * @return
	 */
	public boolean[] buildMiddleFalse(int size) {
		boolean[] result = buildMiddle(size, false);
		return result;
    }

	/**
	 * @param size
	 * @return
	 */
	public boolean[] buildAllTrue(int size) {
		boolean[] result = buildAll(size, true);
		return result;
    }
	
	/**
	 * @param size
	 * @return
	 */
	public boolean[] buildAllFalse(int size) {
		boolean[] result = buildAll(size, false);
		return result;
    }

	/**
	 * @param size
	 * @param value
	 * @return
	 */
	private boolean[] buildMiddle(int size, boolean value) {
		boolean[] result = new boolean[size];
		Arrays.fill(result, value);
		result[0] = !value;
		result[size - 1] = !value;
		return result;
    }

	/**
	 * @param size
	 * @param value
	 * @return
	 */
	private boolean[] buildAll(int size, boolean value) {
		boolean[] result = new boolean[size];
		Arrays.fill(result, value);
		return result;
    }
	
	/**
	 * @param size
	 * @return
	 */
	public boolean[] buildStartTrue(int size) {
		boolean[] result = buildStart(size, true);
		return result;
    }
	
	/**
	 * @param size
	 * @return
	 */
	public boolean[] buildStartFalse(int size) {
		boolean[] result = buildStart(size, false);
		return result;
    }
	
	/**
	 * @param size
	 * @param value
	 * @return
	 */
	private boolean[] buildStart(int size, boolean value) {
		boolean[] result = new boolean[size];
		Arrays.fill(result, value);
		result[size - 1] = !value;
		return result;
    }
}