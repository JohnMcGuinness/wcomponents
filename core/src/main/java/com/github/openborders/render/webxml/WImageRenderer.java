package com.github.openborders.render.webxml;

import java.awt.Dimension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.openborders.Renderer;
import com.github.openborders.WComponent;
import com.github.openborders.WImage;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.servlet.WebXmlRenderContext;
import com.github.openborders.util.I18nUtilities;

/**
 * {@link Renderer} for the {@link WImage} component.
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
final class WImageRenderer extends AbstractWebXmlRenderer
{
    /** The logger instance for this class. */
    private static final Log log = LogFactory.getLog(WImageRenderer.class);

    /**
     * Paints the given {@link WImage}.
     * 
     * @param component the WImage to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WImage imageComponent = (WImage) component;
        XmlStringBuilder xml = renderContext.getWriter();

        // No image set
        if (imageComponent.getImage() == null && imageComponent.getImageUrl() == null)
        {
            return;
        }

        // Check for alternative text on the image
        String alternativeText = imageComponent.getAlternativeText();
        if (alternativeText == null)
        {
            log.warn("Image should have a description.");
            alternativeText = "";
        }
        else
        {
            alternativeText = I18nUtilities.format(null, alternativeText);
        }

        xml.appendTagOpen("ui:image");
        xml.appendAttribute("id", component.getId());
        xml.appendOptionalAttribute("track", component.isTracking(), "true");
        xml.appendAttribute("src", imageComponent.getTargetUrl());
        xml.appendAttribute("alt", alternativeText);
        xml.appendOptionalAttribute("hidden", component.isHidden(), "true");

        // Check for size information on the image
        Dimension size = imageComponent.getSize();
        if (size != null)
        {
            if (size.getHeight() >= 0)
            {
                xml.appendAttribute("height", size.height);
            }

            if (size.getWidth() >= 0)
            {
                xml.appendAttribute("width", size.width);
            }
        }

        xml.appendEnd();
    }
}
