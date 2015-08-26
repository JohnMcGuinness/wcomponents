package com.github.openborders.wcomponents.examples;

import com.github.openborders.wcomponents.examples.AutoReFocusExample;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.github.openborders.wcomponents.WComponent;
import com.github.openborders.wcomponents.WDropdown;
import com.github.openborders.wcomponents.util.TreeUtil;

import com.github.openborders.wcomponents.test.selenium.MultiBrowserRunner;
import com.github.openborders.wcomponents.test.selenium.WComponentSeleniumTestCase;

/**
 * Selenium unit tests for {@link AutoReFocusExample}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
@Category(SeleniumTests.class)
@RunWith(MultiBrowserRunner.class)
public class AutoReFocusExample_Test extends WComponentSeleniumTestCase
{
    /**
     * Creates a new AutoReFocusExample_Test.
     */
    public AutoReFocusExample_Test()
    {
        super(new AutoReFocusExample());
    }

    @Test
    public void testAutoReFocus()
    {
        // Launch the web browser to the LDE
        WebDriver driver = getDriver();

        String[] paths =
        {
            "TextDuplicator/WButton",
            "WRadioButtonTriggerActionExample/WRadioButton",
            "WDropdownSubmitOnChangeExample/WDropdown[0]",
            "WDropdownTriggerActionExample/WDropdown[0]"
        };

        for (String path : paths)
        {
            driver.findElement(byWComponentPath(path)).click();

            // The dropdowns in the example need something to be selected to trigger the submit
            WComponent comp = TreeUtil.findWComponent(getUi(), path.split("/")).getComponent();

            if (comp instanceof WDropdown)
            {
                WDropdown dropdown = (WDropdown) comp;
                driver.findElement(byWComponentPath(path, dropdown.getOptions().get(0))).click();
            }

            Assert.assertEquals("Incorrect focus for " + path,
                                driver.findElement(byWComponentPath(path)).getAttribute("id"),
                                driver.switchTo().activeElement().getAttribute("id"));
        }
    }
}