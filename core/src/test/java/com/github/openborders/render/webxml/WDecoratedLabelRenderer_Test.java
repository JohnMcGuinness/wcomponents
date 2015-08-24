package com.github.openborders.render.webxml;

import java.io.IOException;

import junit.framework.Assert;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.github.openborders.WCheckBox;
import com.github.openborders.WContainer;
import com.github.openborders.WDecoratedLabel;
import com.github.openborders.WLabel;
import com.github.openborders.WText;
import com.github.openborders.render.webxml.WDecoratedLabelRenderer;

/**
 * Junit test case for {@link WDecoratedLabelRenderer}.
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
public class WDecoratedLabelRenderer_Test extends AbstractWebXmlRendererTestCase
{
    @Test
    public void testRendererCorrectlyConfigured()
    {
        WDecoratedLabel decoratedLabel = new WDecoratedLabel();
        Assert.assertTrue("Incorrect renderer supplied", getWebXmlRenderer(decoratedLabel) instanceof WDecoratedLabelRenderer);
    }

    @Test
    public void testDoPaint() throws IOException, SAXException, XpathException
    {
        final String bodyText = "WDecoratedLabelRenderer_Test.testDoPaint.bodyText";
        final String headText = "WDecoratedLabelRenderer_Test.testDoPaint.headText";
        final String tailText = "WDecoratedLabelRenderer_Test.testDoPaint.tailText";

        // Test minimal text content
        WDecoratedLabel decoratedLabel = new WDecoratedLabel(bodyText);

        assertSchemaMatch(decoratedLabel);
        assertXpathEvaluatesTo(bodyText, "normalize-space(//ui:decoratedLabel/ui:labelBody)", decoratedLabel);
        assertXpathNotExists("//ui:decoratedLabel/@labelFocus", decoratedLabel);

        decoratedLabel.setHead(new WText(headText));
        decoratedLabel.setTail(new WText(tailText));

        // Test all text content
        assertSchemaMatch(decoratedLabel);
        assertXpathEvaluatesTo(headText, "normalize-space(//ui:decoratedLabel/ui:labelHead)", decoratedLabel);
        assertXpathEvaluatesTo(bodyText, "normalize-space(//ui:decoratedLabel/ui:labelBody)", decoratedLabel);
        assertXpathEvaluatesTo(tailText, "normalize-space(//ui:decoratedLabel/ui:labelTail)", decoratedLabel);
        assertXpathNotExists("//ui:decoratedLabel/@labelFocus", decoratedLabel);

        // Test complex content
        WContainer complexContent = new WContainer();
        complexContent.add(new WLabel("Select"));
        complexContent.add(new WCheckBox());
        decoratedLabel.setBody(complexContent);

        assertXpathExists("//ui:decoratedLabel/ui:labelBody/ui:label/following-sibling::ui:checkBox", decoratedLabel);
    }
}
