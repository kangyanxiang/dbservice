package com.ch.util;

public class ArrayUtils {

	public static byte[] subArray(byte[] array, int start, int length) {
		byte[] obj = new byte[length];
		for (int i = 0; i < length; i++) {
			obj[i] = array[i + start];
		}
		return obj;
	}

	public static boolean compare(byte[] array1, byte[] array2) {
		if (array1 == null || array2 == null || array1.length != array2.length)
			return false;

		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}

}
