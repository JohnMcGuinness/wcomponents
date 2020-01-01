package com.github.bordertech.wcomponents.subordinate;

import com.github.bordertech.wcomponents.SubordinateTrigger;

/**
 *
 */
public final class Conditions {

	private Conditions() {
	 // To prevent instantiation.	
	}

	/**
	 * Create a Match condition with a SubordinateTrigger and Compare value.
	 *
	 * @param trigger the trigger input field.
	 * @param compare the regular expression to be matched.
	 * @return a {@link Match} condition.
	 */
	public static Condition match(final SubordinateTrigger trigger, final String compare) {
		return new Match(trigger, compare);
	}

	/**
	 * Create a Equal condition with a SubordinateTrigger and Compare value.
	 *
	 * @param trigger the trigger input field.
	 * @param compare the value to use in the compare.
	 * @return an {@link Equal} condition.
	 */
	public static Condition equal(final SubordinateTrigger trigger, final Object compare) {
		return new Equal(trigger, compare);
	}

	/**
	 * Create a NotEqual condition with a SubordinateTrigger and Compare value.
	 *
	 * @param trigger the trigger input field.
	 * @param compare the value to use in the compare.
	 * @return a {@link NotEqual} condition.
	 */
	public static Condition notEqual(final SubordinateTrigger trigger, final Object compare) {
		return new NotEqual(trigger, compare);
	}

	/**
	 * Create a GreaterThan condition with a SubordinateTrigger and Compare value.
	 *
	 * @param trigger the trigger input field.
	 * @param compare the value to use in the compare.
	 * @return a {@link GreaterThan} condition.
	 */
	public static Condition greaterThan(final SubordinateTrigger trigger, final Object compare) {
		return new GreaterThan(trigger, compare);
	}

	/**
	 * Create a GreaterThanOrEqual condition with a SubordinateTrigger and Compare value.
	 *
	 * @param trigger the trigger input field.
	 * @param compare the value to use in the compare.
	 * @return a {@link GreaterThanOrEqual} condition.
	 */
	public static Condition greaterThanOrEqual(final SubordinateTrigger trigger, final Object compare) {
		return new GreaterThanOrEqual(trigger, compare);
	}

	public static Condition lessThan(final SubordinateTrigger trigger, final Object compare) {
		return new LessThan(trigger, compare);
	}

	public static Condition lessThanOrEqual(final SubordinateTrigger trigger, final Object compare) {
		return new LessThanOrEqual(trigger, compare);
	}

	public static Condition not(final Condition condition) {
		return new Not(condition);
	}

	public static Condition and(final Condition condition1, final Condition condition2) {
		return new And(condition1, condition2);
	}

	public static Condition and(final Condition condition1, final Condition condition2,
			final Condition... conditions3) {
		return new And(condition1, condition2, conditions3);
	}

	public static Condition or(final Condition condition1, final Condition condition2) {
		return new Or(condition1, condition2);
	}

	public static Condition or(final Condition condition1, final Condition condition2,
			final Condition... conditions3) {
		return new Or(condition1, condition2, conditions3);
	}
}
