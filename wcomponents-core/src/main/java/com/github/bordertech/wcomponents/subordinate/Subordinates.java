package com.github.bordertech.wcomponents.subordinate;

import com.github.bordertech.wcomponents.util.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public final class Subordinates {

	private Subordinates() {
		// To prevent instantiation.
	}

	public static WSubordinateControl subordinate(final Condition condition, final ActionsOnTrue actionsOnTrue, final ActionsOnFalse actionsOnFalse) {

		Objects.requireNonNull(condition, "A subordinate requires a condition");
		Objects.requireNonNull(actionsOnTrue, "A subordinate requires at least one action to perform when the condition is true");
		Objects.requireNonNull(actionsOnFalse, "A subordinate requires at least one action to perform when the condition is false");
		
		final WSubordinateControl control = new WSubordinateControl();

		final Rule rule = new Rule(condition);

		for (Action action : actionsOnTrue.getActions()) {
			rule.addActionOnTrue(action);
		}

		for (Action action : actionsOnFalse.getActions()) {
			rule.addActionOnFalse(action);
		}

		control.addRule(rule);

		return control;
	}

	public static ActionsOnTrue actionsOnTrue(final Action firstAction, final Action... otherActions) {
		return new ActionsOnTrue(firstAction, otherActions);
	}

	public static ActionsOnFalse actionsOnFalse(final Action firstAction, final Action... otherActions) {
		return new ActionsOnFalse(firstAction, otherActions);
	}

	public static final class ActionsOnFalse extends ActionsToPerform {
		
		private static final String MSG = "Atleast one action must be provided to execute when the condition is false.";

		private ActionsOnFalse(final Action firstAction, final Action... otherActions) {
			super(MSG, firstAction, otherActions);
		}
	}

	public static final class ActionsOnTrue extends ActionsToPerform {

		private static final String MSG = "Atleast one action must be provided to execute when the condition is true.";
		
		private ActionsOnTrue(final Action firstAction, final Action... otherActions) {
			super(MSG, firstAction, otherActions);
		}
	}
	
	private abstract static class ActionsToPerform {
	
		private final List<Action> actions;
		
		private ActionsToPerform(final String msg, final Action firstAction, final Action... otherActions) {

			this.actions = new ArrayList<>(otherActions.length + 1);

			if (firstAction != null) {
				this.actions.add(firstAction);
			}
			
			if (otherActions.length > 0) {
				for (Action action : otherActions) {
					if (action != null) {
						this.actions.add(action);
					}
				}
			}
			
			if (this.actions.isEmpty()) {
				throw new SystemException(msg);
			}
		}
		
		public final List<Action> getActions() {
			return this.actions;
		}
	}
}
