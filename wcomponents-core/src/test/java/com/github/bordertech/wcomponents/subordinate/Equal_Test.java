package com.github.bordertech.wcomponents.subordinate;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.SubordinateTrigger;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WNumberField;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link Equal}.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Equal_Test {

	/**
	 * Equal to value.
	 */
	private static final BigDecimal EQ_VALUE = BigDecimal.valueOf(10);
	/**
	 * Less than value.
	 */
	private static final BigDecimal LT_VALUE = BigDecimal.valueOf(9);
	/**
	 * Greater than value.
	 */
	private static final BigDecimal GT_VALUE = BigDecimal.valueOf(11);

	@Test
	public void testConstructor() {
		SubordinateTrigger trigger = new MyTrigger();
		Object value = new Object();
		Equal compare = new Equal(trigger, value);

		Assert.assertEquals("Value for Equal is incorrect", value, compare.getValue());
		Assert.
				assertEquals("Trigger for Equal should be the trigger", trigger, compare.
						getTrigger());
	}

	@Test
	public void testCompareType() {
		Equal compare = new Equal(new MyTrigger(), null);
		Assert.assertEquals("Incorrect Compare Type", AbstractCompare.CompareType.EQUAL, compare.
				getCompareType());
	}

	@Test
	public void testDoCompare() {
		WNumberField trigger = new WNumberField();

		// ------------------------------
		// Setup EQUAL - with value
		Equal compare = new Equal(trigger, EQ_VALUE);

		trigger.setNumber(null);
		Assert.assertFalse("Equal Type - Compare for null value should be false", compare.execute());

		trigger.setNumber(LT_VALUE);
		Assert.assertFalse("Equal Type - Compare for less value should be false", compare.execute());

		trigger.setNumber(EQ_VALUE);
		Assert.assertTrue("Equal Type - Compare for equal value should be true", compare.execute());

		trigger.setNumber(GT_VALUE);
		Assert.assertFalse("Equal Type - Compare for greater value should be false", compare.
				execute());

		// ------------------------------
		// Setup EQUAL - with null value
		compare = new Equal(trigger, null);

		trigger.setNumber(null);
		Assert.assertTrue("Equal Type With Null Value - Compare for null value should be true",
				compare.execute());

		trigger.setNumber(EQ_VALUE);
		Assert.assertFalse("Equal Type With Null Value - Compare for value should be false",
				compare.execute());
	}

	@Test
	public void testToString() {
		MyTrigger trigger = new MyTrigger();

		Equal compare = new Equal(trigger, "1");
		Assert.assertEquals("Incorrect toString for compare", "MyTrigger=\"1\"", compare.toString());

		WLabel label = new WLabel("test label", trigger);
		Assert.assertEquals("Incorrect toString for compare with a label",
				label.getText() + "=\"1\"", compare.toString());
	}

	/**
	 * Test component that implements the SubordinateTrigger interface.
	 */
	private static class MyTrigger extends AbstractWComponent implements SubordinateTrigger {
	}

}
