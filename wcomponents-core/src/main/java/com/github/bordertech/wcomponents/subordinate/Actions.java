package com.github.bordertech.wcomponents.subordinate;

import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WComponentGroup;

/**
 *
 */
public final class Actions {

	private Actions() {
		// To prevent instatiation.
	}

	public static Action show(final SubordinateTarget target) {
		return new Show(target);
	}

	public static Action showInGroup(final SubordinateTarget target,
			final WComponentGroup<? extends SubordinateTarget> group) {
		return new ShowInGroup(target, group);
	}

	public static Action hide(final SubordinateTarget target) {
		return new Hide(target);
	}

	public static Action hideInGroup(final SubordinateTarget target,
			final WComponentGroup<? extends SubordinateTarget> group) {
		return new HideInGroup(target, group);
	}

	public static Action disable(final SubordinateTarget target) {
		return new Disable(target);
	}

	public static Action disableInGroup(final SubordinateTarget target,
			final WComponentGroup<? extends SubordinateTarget> group) {
		return new DisableInGroup(target, group);
	}

	public static Action enable(final SubordinateTarget target) {
		return new Enable(target);
	}

	public static Action enableInGroup(final SubordinateTarget target,
			final WComponentGroup<? extends SubordinateTarget> group) {
		return new EnableInGroup(target, group);
	}
	
	public static Action mandatory(final SubordinateTarget target) {
		return new Mandatory(target);
	}
	
	public static Action optional(final SubordinateTarget target) {
		return new Optional(target);
	}
}
