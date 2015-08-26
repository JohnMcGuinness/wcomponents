package com.github.openborders.wcomponents.render.webxml;

import java.io.IOException;

import junit.framework.Assert;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.github.openborders.wcomponents.AjaxTarget;
import com.github.openborders.wcomponents.WAjaxControl;
import com.github.openborders.wcomponents.WButton;
import com.github.openborders.wcomponents.WContainer;
import com.github.openborders.wcomponents.WPanel;

/**
 * Junit test case for {@link WAjaxControlRenderer}.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WAjaxControlRenderer_Test extends AbstractWebXmlRendererTestCase
{
    @Test
    public void testRendererCorrectlyConfigured()
    {
        WAjaxControl component = new WAjaxControl(new WButton("x"));
        Assert.assertTrue("Incorrect renderer supplied", getWebXmlRenderer(component) instanceof WAjaxControlRenderer);
    }

    @Test
    public void testDoPaint() throws IOException, SAXException, XpathException
    {
        WContainer root = new WContainer();
        WButton trigger = new WButton("x");
        WPanel target1 = new WPanel();
        WPanel target2 = new WPanel();
        WPanel target3 = new WPanel();
        WAjaxControl control = new WAjaxControl(trigger);

        root.add(trigger);
        root.add(target1);
        root.add(target2);
        root.add(target3);
        root.add(control);

        // No Targets
        assertSchemaMatch(root);
        assertXpathEvaluatesTo("0", "count(//ui:ajaxTrigger)", root);

        // With Targets
        control.addTargets(new AjaxTarget[] { target1, target2, target3 });

        setActiveContext(createUIContext());
        assertSchemaMatch(root);
        assertXpathEvaluatesTo(trigger.getId(), "//ui:ajaxTrigger/@triggerId", root);
        assertXpathEvaluatesTo("", "//ui:ajaxTrigger/@allowedUses", root);
        assertXpathEvaluatesTo("", "//ui:ajaxTrigger/@delay", root);
        assertXpathEvaluatesTo("3", "count(//ui:ajaxTrigger/ui:ajaxTargetId)", root);
        assertXpathEvaluatesTo(target1.getId(), "//ui:ajaxTrigger/ui:ajaxTargetId[1]/@targetId", root);
        assertXpathEvaluatesTo(target2.getId(), "//ui:ajaxTrigger/ui:ajaxTargetId[2]/@targetId", root);
        assertXpathEvaluatesTo(target3.getId(), "//ui:ajaxTrigger/ui:ajaxTargetId[3]/@targetId", root);

        // With Targets and optional attributes
        control.setLoadCount(6);
        control.setDelay(1000);

        assertSchemaMatch(root);
        assertXpathEvaluatesTo(trigger.getId(), "//ui:ajaxTrigger/@triggerId", root);
        assertXpathEvaluatesTo("6", "//ui:ajaxTrigger/@allowedUses", root);
        assertXpathEvaluatesTo("1000", "//ui:ajaxTrigger/@delay", root);
        assertXpathEvaluatesTo("3", "count(//ui:ajaxTrigger/ui:ajaxTargetId)", root);
    }
}