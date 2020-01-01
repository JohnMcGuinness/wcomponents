package com.github.bordertech.wcomponents.subordinate;

import com.github.bordertech.wcomponents.subordinate.builder.*;
import com.github.bordertech.wcomponents.AbstractWComponentTestCase;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WComponentGroup;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WTextField;
import static com.github.bordertech.wcomponents.subordinate.Actions.*;
import static com.github.bordertech.wcomponents.subordinate.Conditions.*;
import static com.github.bordertech.wcomponents.subordinate.Subordinates.*;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for the {@link SubordinateBuilder} class.
 *
 * @author John McGuinness */
public final class Subordinates_Test extends AbstractWComponentTestCase {

	public void testInvalidSyntax() {
		
		WTextField txf = new WTextField();
		
		WSubordinateControl control 
			= subordinate(
				equal(
					new WTextField(), "x"
				),
				actionsOnTrue(
					show(txf)
				), 
				actionsOnFalse(
					hide(txf)
				)
			);
	}

	@Test(expected = NullPointerException.class)
	public void missingConditionTest() {

		subordinate(
			null,
			actionsOnTrue(
				show(new WTextField())
			), 
			actionsOnFalse(
				hide(new WTextField())
			)
		);
	}

	@Test(expected = NullPointerException.class)
	public void missingActionOnTrueTest() {

		subordinate(
			equal(
				new WTextField(), "x"
			),
			null, 
			actionsOnFalse(
				hide(new WTextField())
			)
		);
	}

	@Test(expected = NullPointerException.class)
	public void missingActionOnFalseTest() {

		subordinate(
			equal(
				new WTextField(), "x"
			),
			actionsOnTrue(
				hide(new WTextField())
			), 
			null
		);
	}

	@Test
	public void enableTest() {

		WCheckBox input = new WCheckBox();

		WSubordinateControl control 
			= subordinate(
				equal(input, "false"), 
				actionsOnTrue(
					disable(input)
				),
				actionsOnFalse(
					enable(input)
				)
			);
		
		setActiveContext(createUIContext());
		Assert.assertFalse("Component should be initially enabled", input.isDisabled());

		control.applyTheControls();
		Assert.assertTrue("Component should be disabled", input.isDisabled());
	}

	@Test
	public void disableTest() {

		WCheckBox input = new WCheckBox();

		WSubordinateControl control 
			= subordinate(
				equal(input, "false"),
				actionsOnTrue(
					disable(input)
				),
				actionsOnFalse(
					enable(input)
				)
			);

		setActiveContext(createUIContext());
		Assert.assertFalse("Component should be initially enabled", input.isDisabled());

		control.applyTheControls();
		Assert.assertTrue("Component should be disabled", input.isDisabled());
	}

	@Test
	public void showTest() {

		WCheckBox input = new WCheckBox();

		WSubordinateControl control 
			= subordinate(
				equal(input, "false"),
				actionsOnTrue(
					show(input)
				),
				actionsOnFalse(
					hide(input)
				)
			);
		
		setActiveContext(createUIContext());
		setFlag(input, ComponentModel.HIDE_FLAG, true);
		Assert.assertTrue("Component should initially be hidden", input.isHidden());

		control.applyTheControls();
		Assert.assertFalse("Component should be visible", input.isHidden());
	}

	@Test
	public void hideTest() {

		WCheckBox input = new WCheckBox();

		WSubordinateControl control 
			= subordinate(
				equal(input, "false"),
				actionsOnTrue(
					hide(input)
				),
				actionsOnFalse(
					show(input)
				)
			);
		
		setActiveContext(createUIContext());
		Assert.assertFalse("Component should be initially visible", input.isHidden());

		control.applyTheControls();
		Assert.assertTrue("Component should be hidden", input.isHidden());
	}

	@Test
	public void mandatoryTest() {

		WCheckBox input = new WCheckBox();

		WSubordinateControl control 
			= subordinate(
				equal(input, "false"),
				actionsOnTrue(
					mandatory(input)
				),
				actionsOnFalse(
					optional(input)
				)
			);
		
		setActiveContext(createUIContext());
		Assert.assertFalse("Component should be initially optional", input.isMandatory());

		control.applyTheControls();
		Assert.assertTrue("Component should be required", input.isMandatory());
	}

	@Test
	public void optionalTest() {

		WCheckBox input = new WCheckBox();

		WSubordinateControl control 
			= subordinate(
				equal(input, "false"),
				actionsOnTrue(
					optional(input)
				),
				actionsOnFalse(
					mandatory(input)
				)
			);

		setActiveContext(createUIContext());
		input.setMandatory(true);
		Assert.assertTrue("Component should be initially mandatory", input.isMandatory());

		control.applyTheControls();
		Assert.assertFalse("Component should be mandatory", input.isHidden());
	}

	@Test
	public void showInTest() {

		WCheckBox input1 = new WCheckBox();
		WCheckBox input2 = new WCheckBox();
		WCheckBox input3 = new WCheckBox();

		WComponentGroup<SubordinateTarget> group = new WComponentGroup<>();
		group.addToGroup(input1);
		group.addToGroup(input2);
		group.addToGroup(input3);

		WSubordinateControl control 
			= subordinate(
				equal(new WCheckBox(), "false"),
				actionsOnTrue(
					showInGroup(input2, group)
				),
				actionsOnFalse(
					hideInGroup(input2, group)
				)
			);

		setActiveContext(createUIContext());

		// Set initial states (opposite to end state)
		setFlag(input1, ComponentModel.HIDE_FLAG, false);
		setFlag(input2, ComponentModel.HIDE_FLAG, true);
		setFlag(input3, ComponentModel.HIDE_FLAG, false);

		Assert.assertFalse("showIn - Input1 Component should not be hidden", input1.isHidden());
		Assert.assertTrue("showIn - Input2 Component should be initially hidden", input2.isHidden());
		Assert.assertFalse("showIn - Input3 Component should not be hidden", input3.isHidden());

		control.applyTheControls();

		Assert.assertTrue("showIn - Input1 Component should be hidden", input1.isHidden());
		Assert.assertFalse("showIn - Input2 Component should not be hidden", input2.isHidden());
		Assert.assertTrue("showIn - Input3 Component should be hidden", input3.isHidden());
	}

	@Test
	public void hideInTest() {

		WCheckBox input1 = new WCheckBox();
		WCheckBox input2 = new WCheckBox();
		WCheckBox input3 = new WCheckBox();

		WComponentGroup<SubordinateTarget> group = new WComponentGroup<>();
		group.addToGroup(input1);
		group.addToGroup(input2);
		group.addToGroup(input3);

		WSubordinateControl control 
			= subordinate(
				equal(new WCheckBox(), "false"),
				actionsOnTrue(
					hideInGroup(input2, group)
				),
				actionsOnFalse(
					showInGroup(input2, group)
				)
			);

		setActiveContext(createUIContext());

		// Set initial states (opposite to end state)
		setFlag(input1, ComponentModel.HIDE_FLAG, true);
		setFlag(input2, ComponentModel.HIDE_FLAG, false);
		setFlag(input3, ComponentModel.HIDE_FLAG, true);

		Assert.assertTrue("hideIn - Input1 Component should be hidden", input1.isHidden());
		Assert.assertFalse("hideIn - Input2 Component should not be hidden", input2.isHidden());
		Assert.assertTrue("hideIn - Input3 Component should be hidden", input3.isHidden());

		control.applyTheControls();

		Assert.assertFalse("hideIn - Input1 Component should not be hidden", input1.isHidden());
		Assert.assertTrue("hideIn - Input2 Component should be hidden", input2.isHidden());
		Assert.assertFalse("hideIn - Input3 Component should not be hidden", input3.isHidden());
	}

	@Test
	public void enableInTest() {

		WCheckBox input1 = new WCheckBox();
		WCheckBox input2 = new WCheckBox();
		WCheckBox input3 = new WCheckBox();

		WComponentGroup<SubordinateTarget> group = new WComponentGroup<>();
		group.addToGroup(input1);
		group.addToGroup(input2);
		group.addToGroup(input3);

		WSubordinateControl control 
			= subordinate(
				equal(new WCheckBox(), "false"),
				actionsOnTrue(
					enableInGroup(input2, group)
				),
				actionsOnFalse(
					disableInGroup(input2, group)
				)
			);

		setActiveContext(createUIContext());

		// Set initial states (opposite to end state)
		input1.setDisabled(false);
		input2.setDisabled(true);
		input3.setDisabled(false);

		Assert.assertFalse("enableIn - Input1 Component should be enabled", input1.isDisabled());
		Assert.assertTrue("enableIn - Input2 Component should be disabled", input2.isDisabled());
		Assert.assertFalse("enableIn - Input3 Component should be enabled", input3.isDisabled());

		control.applyTheControls();

		Assert.assertTrue("enableIn - Input1 Component should be disabled", input1.isDisabled());
		Assert.assertFalse("enableIn - Input2 Component should be enabled", input2.isDisabled());
		Assert.assertTrue("enableIn - Input3 Component should be disabled", input3.isDisabled());
	}

	@Test
	public void disableInTest() {

		WCheckBox input1 = new WCheckBox();
		WCheckBox input2 = new WCheckBox();
		WCheckBox input3 = new WCheckBox();

		WComponentGroup<SubordinateTarget> group = new WComponentGroup<>();
		group.addToGroup(input1);
		group.addToGroup(input2);
		group.addToGroup(input3);

		WSubordinateControl control 
			= subordinate(
				equal(new WCheckBox(), "false"),
				actionsOnTrue(
					disableInGroup(input2, group)
				),
				actionsOnFalse(
					enableInGroup(input2, group)
				)
			);

		setActiveContext(createUIContext());

		// Set initial states (opposite to end state)
		input1.setDisabled(true);
		input2.setDisabled(false);
		input3.setDisabled(true);

		Assert.assertTrue("disableIn - Input1 Component should be disabled", input1.isDisabled());
		Assert.assertFalse("disableIn - Input2 Component should be enabled", input2.isDisabled());
		Assert.assertTrue("disableIn - Input3 Component should be disabled", input3.isDisabled());

		control.applyTheControls();

		Assert.assertFalse("disableIn - Input1 Component should be enabled", input1.isDisabled());
		Assert.assertTrue("disableIn - Input2 Component should be disabled", input2.isDisabled());
		Assert.assertFalse("disableIn - Input3 Component should be enabled", input3.isDisabled());
	}

	@Test
	public void whenTrueTest() {

		WTextField comp1 = new WTextField();
		WTextField comp2 = new WTextField();

		WSubordinateControl control 
			= subordinate(
				equal(comp1, "x"), 
				actionsOnTrue(
					disable(comp1)
				),
				actionsOnFalse(
					enable(comp2)
				)
			);

		setActiveContext(createUIContext());
		Assert.assertFalse("comp1 should not be disabled until rule executes", comp1.isDisabled());
		Assert.assertFalse("comp2 should not be disabled", comp2.isDisabled());

		comp1.setText("x");
		control.applyTheControls();

		Assert.assertTrue("comp1 should be disabled", comp1.isDisabled());
		Assert.assertFalse("comp2 should not be disabled", comp2.isDisabled());
	}

	@Test
	public void whenFalseTest() {

		WTextField comp1 = new WTextField();
		WTextField comp2 = new WTextField();

		WSubordinateControl control 
			= subordinate(
				equal(comp1, "x"), 
				actionsOnTrue(
					enable(comp2)
				),
				actionsOnFalse(
					disable(comp1)
				)
			);

		Assert.assertFalse("comp1 should not be disabled until rule executes", comp1.isDisabled());
		Assert.assertFalse("comp2 should not be disabled", comp2.isDisabled());

		setActiveContext(createUIContext());
		comp1.setText("y");
		control.applyTheControls();

		Assert.assertTrue("comp1 should be disabled", comp1.isDisabled());
		Assert.assertFalse("comp2 should not be disabled", comp2.isDisabled());
	}


	@Test
	public void multipleActionsTest() {

		WCheckBox comp1 = new WCheckBox();
		WTextField comp2 = new WTextField();

		WSubordinateControl control 
			= subordinate(
				equal(comp1, "true"),
				actionsOnTrue(
					mandatory(comp2),
					show(comp2)
				),
				actionsOnFalse(
					optional(comp2),
					hide(comp2)
				)
			);
		
		setActiveContext(createUIContext());
		Assert.assertFalse("Component should not initially mandatory", comp2.isMandatory());
		Assert.assertFalse("Component should not initially be hidden", comp2.isHidden());
		
		comp1.setSelected(true);
		control.applyTheControls();

		Assert.assertTrue("Component should be mandatory", comp2.isMandatory());
		Assert.assertFalse("Component should not be hidden", comp2.isHidden());
		
		comp1.setSelected(false);
		control.applyTheControls();

		Assert.assertFalse("Component should not be mandatory", comp2.isMandatory());
		Assert.assertTrue("Component should not be hidden", comp2.isHidden());
	}
	
	@Test
	public void orConditionTest() {
	
		WDropdown trigger = new WDropdown(Arrays.asList("A", "B", "C", "D"));
		SubordinateTarget target = new WTextField();
		
		WSubordinateControl control 
			= subordinate(
				or(
					equal(trigger, "A"),
					equal(trigger, "B")
				),
				actionsOnTrue(
					show(target)
				),
				actionsOnFalse(
					hide(target)
				)
			);

		setActiveContext(createUIContext());
		Assert.assertFalse("Component should not initially be hidden", target.isHidden());

		trigger.setSelected("C");
		control.applyTheControls();
		Assert.assertTrue("Component should be hidden", target.isHidden());

		trigger.setSelected("A");
		control.applyTheControls();
		Assert.assertFalse("Component should not be hidden", target.isHidden());
	}
	
	@Test
	public void andConditionTest() {
	
		WDropdown trigger = new WDropdown(Arrays.asList("A", "B", "C", "D"));
		SubordinateTarget target = new WTextField();
		
		WSubordinateControl control 
			= subordinate(
				and(
					notEqual(trigger, "A"),
					notEqual(trigger, "B")
				),
				actionsOnTrue(
					show(target)
				),
				actionsOnFalse(
					hide(target)
				)
			);

		setActiveContext(createUIContext());
		Assert.assertFalse("Component should not initially be hidden", target.isHidden());

		trigger.setSelected("C");
		control.applyTheControls();
		Assert.assertFalse("Component should not be hidden", target.isHidden());

		trigger.setSelected("A");
		control.applyTheControls();
		Assert.assertTrue("Component should be hidden", target.isHidden());
	}
	
	@Test
	public void greaterThanConditionTest() {
	
		WDropdown trigger = new WDropdown(Arrays.asList("A", "B", "C", "D"));
		SubordinateTarget target = new WTextField();
		
		WSubordinateControl control 
			= subordinate(
				greaterThan(trigger, "B"),
				actionsOnTrue(
					show(target)
				),
				actionsOnFalse(
					hide(target)
				)
			);

		setActiveContext(createUIContext());
		Assert.assertFalse("Component should not initially be hidden", target.isHidden());

		trigger.setSelected("C");
		control.applyTheControls();
		Assert.assertFalse("Component should not be hidden", target.isHidden());

		trigger.setSelected("A");
		control.applyTheControls();
		Assert.assertTrue("Component should be hidden", target.isHidden());
	}
	
	@Test
	public void greaterThanOrEqualConditionTest() {
	
		WDropdown trigger = new WDropdown(Arrays.asList("A", "B", "C", "D"));
		SubordinateTarget target = new WTextField();
		
		WSubordinateControl control 
			= subordinate(
				greaterThanOrEqual(trigger, "B"),
				actionsOnTrue(
					show(target)
				),
				actionsOnFalse(
					hide(target)
				)
			);

		setActiveContext(createUIContext());
		Assert.assertFalse("Component should not initially be hidden", target.isHidden());

		trigger.setSelected("B");
		control.applyTheControls();
		Assert.assertFalse("Component should not be hidden", target.isHidden());

		trigger.setSelected("A");
		control.applyTheControls();
		Assert.assertTrue("Component should be hidden", target.isHidden());
	}
}
