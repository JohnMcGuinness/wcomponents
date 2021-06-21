package com.github.bordertech.wcomponents.util;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

/**
 * A storage point for utility methods that are generally useful.
 *
 * @author James Gifford
 * @since 1.0.0
 */
public final class Util {

	/**
	 * No instance methods here.
	 */
	private Util() {
	}

	/**
	 * Determines whether the given string is null or "empty".
	 *
	 * @param aString the string to check.
	 * @return true if the given String is null or contains only whitespace.
	 * 
	 * @deprecated use org.apache.commons.lang3.StringUtils.isBlank instead.
	 */
	@Deprecated
	public static boolean empty(final String aString) {
		return StringUtils.isBlank(aString);
	}

	/**
	 * Determines if to objects are equal. This method handles null references.
	 *
	 * @param obj1 the first object to check.
	 * @param obj2 the second object to check.
	 * @return true if the two objects are the same according to Object.equals.
	 * 
	 * @deprecated use java.util.Objects.equals(Object, Object) instead.
	 */
	@Deprecated
	public static boolean equals(final Object obj1, final Object obj2) {
		return Objects.equals(obj1, obj2);
	}

	/**
	 * Compares two comparable objects where either may be null. Null is regarded as the smallest value, and 2 nulls are
	 * considered equal.
	 *
	 * @param c1 the first comparable
	 * @param c2 the second comparable
	 *
	 * @return a negative integer, zero, or a positive integer if c1 is less than, equal to, or greater than the c2.
	 * 
	 * @deprecated use org.apache.commons.lang3.ObjectUtils.compare(T, T) instead.
	 */
	@Deprecated
	public static int compareAllowNull(final Comparable c1, final Comparable c2) {
		return ObjectUtils.compare(c1, c2);
	}

	/**
	 * Converts a string to upper case, where the string may be null.
	 *
	 * @param aString the string to convert.
	 * @return the string converted to upper case, or null if the supplied string was null.
	 * 
	 * @deprecated Use org.apache.commons.lang3.StringUtils.upperCase(String) instead.
	 */
	@Deprecated
	public static String upperCase(final String aString) {
		return StringUtils.upperCase(aString);
	}

	/**
	 * Copies this String removing white space characters from the end of the string.
	 *
	 * @param aString the String to trim.
	 * @return a new String with characters <code>\\u0020</code> removed from the end
	 * 
	 * @deprecated Don't use. No replacement.
	 */
	@Deprecated
	public static String rightTrim(final String aString) {
		if (aString == null) {
			return null;
		}
		int end = aString.length() - 1;
		while ((end >= 0) && (aString.charAt(end) <= ' ')) {
			end--;
		}
		if (end == aString.length() - 1) {
			return aString;
		}
		return aString.substring(0, end + 1);
	}

	/**
	 * Copies this String removing white space characters from the beginning of the string.
	 *
	 * @param aString the String to trim.
	 * @return a new String with characters <code>\\u0020</code> removed from the beginning
	 * 
	 * @deprecated Don't use. No replacement.
	 */
	@Deprecated
	public static String leftTrim(final String aString) {
		if (aString == null) {
			return null;
		}
		int start = 0;
		while ((start < aString.length()) && (aString.charAt(start) <= ' ')) {
			start++;
		}
		if (start == 0) {
			return aString;
		}
		return aString.substring(start);
	}

}
