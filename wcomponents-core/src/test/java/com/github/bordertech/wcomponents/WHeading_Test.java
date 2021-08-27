package com.github.bordertech.wcomponents;

import org.junit.Assert;
import org.junit.Test;

/**
 * WHeading_Test - Unit tests for {@link WHeading}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
public class WHeading_Test extends AbstractWComponentTestCase {

	@Test
	public void testConstructor1() {
		WHeading heading = new WHeading(HeadingLevel.H1, "dummy");
		Assert.assertEquals("Constructor - Incorrect type", HeadingLevel.H1, heading.
				getHeadingLevel());
	}

	@Test
	public void testConstructor2() {
		WHeading heading = new WHeading(HeadingLevel.H1, new WDecoratedLabel("test"));
		Assert.assertEquals("Constructor - Incorrect type", HeadingLevel.H1, heading.
				getHeadingLevel());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidType1() {
		new WHeading(null, "dummy");
	}

	@Test
	public void testMarginAccessors() {
		assertAccessorsCorrect(new WHeading(HeadingLevel.H1, "test"), WHeading::getMargin, WHeading::setMargin,
			null, new Margin(Size.SMALL), new Margin(Size.MEDIUM));
	}

	@Test
	public void testHeadingLevelAccessors() {
		assertAccessorsCorrect(new WHeading(HeadingLevel.H1, "test"), WHeading::getHeadingLevel, WHeading::setHeadingLevel,
			HeadingLevel.H1, HeadingLevel.H2, HeadingLevel.H3);
	}
}
