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
	public static boolean[] buildMiddleTrue(int size) {
        return buildMiddle(size, true);
    }

	/**
	 * @param size
	 * @return
	 */
	public static boolean[] buildMiddleFalse(int size) {
        return buildMiddle(size, false);
    }

	/**
	 * @param size
	 * @return
	 */
	public static boolean[] buildAllTrue(int size) {
        return buildAll(size, true);
    }
	
	/**
	 * @param size
	 * @return
	 */
	public static boolean[] buildAllFalse(int size) {
        return buildAll(size, false);
    }

	/**
	 * @param size
	 * @param value
	 * @return
	 */
	private static boolean[] buildMiddle(int size, boolean value) {
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
	private static boolean[] buildAll(int size, boolean value) {
		boolean[] result = new boolean[size];
		Arrays.fill(result, value);
		return result;
    }
	
	/**
	 * @param size
	 * @return
	 */
	public static boolean[] buildStartTrue(int size) {
        return buildStart(size, true);
    }
	
	/**
	 * @param size
	 * @return
	 */
	public static boolean[] buildStartFalse(int size) {
        return buildStart(size, false);
    }
	
	/**
	 * @param size
	 * @param value
	 * @return
	 */
	private static boolean[] buildStart(int size, boolean value) {
		boolean[] result = new boolean[size];
		Arrays.fill(result, value);
		result[size - 1] = !value;
		return result;
    }
}