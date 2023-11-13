package com.github.bordertech.wcomponents;

import com.github.bordertech.wcomponents.util.Config;
import org.junit.Assert;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unit tests for {@link WBeanComponent}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
@Ignore("Fails because of bean binding being removed")
public class WBeanComponent_Test extends AbstractWComponentTestCase {

	/**
	 * When these tests are done put things back as they were.
	 */
	@After
	public void tearDownClass() {
		Config.reset();
	}

	@Test
	public void testIsChanged() {
		WBeanComponent component = new WBeanComponent();

		String text1 = "WBeanComponent_Test.testIsChanged.text1";
		String text2 = "WBeanComponent_Test.testIsChanged.text2";

		component.setLocked(true);
		setActiveContext(createUIContext());
		Assert.assertFalse("Should not be changed by default", component.isChanged());

		component.setData(text1);
		Assert.assertTrue("Should be changed if text differs from default", component.isChanged());

//		component.setBean(text1);
//		component.setBeanProperty(".");
//		Assert.assertFalse("Should not be changed if text equals bean", component.isChanged());

		component.setData(text2);
		Assert.assertTrue("Should be changed if text differs from bean", component.isChanged());
	}

	@Test
	public void testDataAccessors() {
		assertAccessorsCorrect(new WBeanComponent(), WBeanComponent::getData, WBeanComponent::setData, null, "A", "B");
	}

	@Test
	public void testDuplicateComponentModels() {
		WBeanComponent beanComponent = new WBeanComponent();
		assertNoDuplicateComponentModels(beanComponent, "beanProperty", "testBean");
	}
}
