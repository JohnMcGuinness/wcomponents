package com.github.bordertech.wcomponents.render.webxml;

import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.OptionGroup;
import com.github.bordertech.wcomponents.WCheckBoxSelect;
import com.github.bordertech.wcomponents.util.SystemException;
import java.io.IOException;
import java.util.Arrays;
import junit.framework.Assert;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Junit test case for {@link WCheckBoxSelectRenderer}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
public class WCheckBoxSelectRenderer_Test extends AbstractWebXmlRendererTestCase {

	@Test
	public void testRendererCorrectlyConfigured() {
		WCheckBoxSelect component = new WCheckBoxSelect();
		Assert.assertTrue("Incorrect renderer supplied",
				getWebXmlRenderer(component) instanceof WCheckBoxSelectRenderer);
	}

	@Test
	public void testDoPaint() throws IOException, SAXException, XpathException {
		WCheckBoxSelect wcbTest = new WCheckBoxSelect(new String[]{"a", "b", "c"});
		assertSchemaMatch(wcbTest);
		assertXpathEvaluatesTo("3", "count(//ui:checkBoxSelect/ui:option)", wcbTest);

		// Check selected
		assertXpathNotExists("//ui:checkBoxSelect/ui:option[@selected='true']", wcbTest);

		setActiveContext(createUIContext());
		wcbTest.setSelected(Arrays.asList(new String[]{"b"}));
		assertSchemaMatch(wcbTest);
		assertXpathEvaluatesTo("1", "count(//ui:checkBoxSelect/ui:option[@selected='true'])",
				wcbTest);
		assertXpathEvaluatesTo("b", "//ui:checkBoxSelect/ui:option[@selected='true']", wcbTest);

		// Check Readonly - only render selected option
		wcbTest.setReadOnly(true);
		assertSchemaMatch(wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@readOnly", wcbTest);
		assertXpathEvaluatesTo("1", "count(//ui:checkBoxSelect/ui:option)", wcbTest);
		assertXpathEvaluatesTo("1", "count(//ui:checkBoxSelect/ui:option[@selected='true'])",
				wcbTest);
		assertXpathEvaluatesTo("b", "//ui:checkBoxSelect/ui:option[@selected='true']", wcbTest);
	}

	@Test
	public void testDoPaintAllOptions() throws IOException, SAXException, XpathException {
		WCheckBoxSelect wcbTest = new WCheckBoxSelect(new String[]{"a", "b", "c"});
		assertSchemaMatch(wcbTest);

		wcbTest.setDisabled(true);
		setFlag(wcbTest, ComponentModel.HIDE_FLAG, true);
		wcbTest.setMandatory(true);
		wcbTest.setReadOnly(true);
		wcbTest.setSubmitOnChange(true);
		wcbTest.setToolTip("tool tip");
		wcbTest.setAccessibleText("accessible text");
		wcbTest.setFrameless(true);
		wcbTest.setMinSelect(1);
		wcbTest.setMaxSelect(2);

		assertSchemaMatch(wcbTest);

		assertXpathEvaluatesTo(wcbTest.getId(), "//ui:checkBoxSelect/@id", wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@disabled", wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@hidden", wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@required", wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@readOnly", wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@submitOnChange", wcbTest);
		assertXpathEvaluatesTo("tool tip", "//ui:checkBoxSelect/@toolTip", wcbTest);
		assertXpathEvaluatesTo("accessible text", "//ui:checkBoxSelect/@accessibleText", wcbTest);
		assertXpathEvaluatesTo("true", "//ui:checkBoxSelect/@frameless", wcbTest);
		assertXpathEvaluatesTo("1", "//ui:checkBoxSelect/@min", wcbTest);
		assertXpathEvaluatesTo("2", "//ui:checkBoxSelect/@max", wcbTest);

		// Button Layouts
		wcbTest.setButtonLayout(WCheckBoxSelect.LAYOUT_COLUMNS);
		wcbTest.setButtonColumns(3);
		assertXpathEvaluatesTo("column", "//ui:checkBoxSelect/@layout", wcbTest);
		assertXpathEvaluatesTo("3", "//ui:checkBoxSelect/@layoutColumnCount", wcbTest);

		wcbTest.setButtonLayout(WCheckBoxSelect.LAYOUT_FLAT);
		assertXpathEvaluatesTo("flat", "//ui:checkBoxSelect/@layout", wcbTest);
		assertXpathNotExists("//ui:checkBoxSelect/@layoutColumnCount", wcbTest);

		wcbTest.setButtonLayout(WCheckBoxSelect.LAYOUT_STACKED);
		assertXpathEvaluatesTo("stacked", "//ui:checkBoxSelect/@layout", wcbTest);
		assertXpathNotExists("//ui:checkBoxSelect/@layoutColumnCount", wcbTest);

	}

	@Test(expected = SystemException.class)
	public void testOptGroupException() throws IOException, SAXException, XpathException {
		OptionGroup optionGroup = new OptionGroup("Test", Arrays.asList(new String[]{"A", "B"}));
		WCheckBoxSelect group = new WCheckBoxSelect(Arrays.asList(new Object[]{"X", optionGroup}));
		assertSchemaMatch(group);
	}

	@Test
	public void testXssEscaping() throws IOException, SAXException, XpathException {
		WCheckBoxSelect wcb = new WCheckBoxSelect(Arrays.asList(
				new Object[]{getInvalidCharSequence(),
					getMaliciousContent()}));

		assertSafeContent(wcb);

		wcb.setToolTip(getMaliciousAttribute("ui:checkBoxSelect"));
		assertSafeContent(wcb);

		wcb.setAccessibleText(getMaliciousAttribute("ui:checkBoxSelect"));
		assertSafeContent(wcb);
	}
}
