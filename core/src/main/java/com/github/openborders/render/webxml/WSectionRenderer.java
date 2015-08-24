package com.github.openborders.render.webxml;

import com.github.openborders.AjaxHelper;
import com.github.openborders.WComponent;
import com.github.openborders.WSection;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.WSection.SectionMode;
import com.github.openborders.servlet.WebXmlRenderContext;
import com.github.openborders.util.SystemException;

/**
 * The Renderer for {@link WSection}.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
final class WSectionRenderer extends AbstractWebXmlRenderer
{
    /**
     * Paints the given WSection.
     * 
     * @param component the WSection to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WSection section = (WSection) component;
        XmlStringBuilder xml = renderContext.getWriter();

        boolean renderChildren = isRenderContent(section);
        
        xml.appendTagOpen("ui:section");
        xml.appendAttribute("id", component.getId());
        xml.appendOptionalAttribute("track", component.isTracking(), "true");
        if (SectionMode.LAZY.equals(section.getMode()))
        {
            xml.appendOptionalAttribute("hidden", !renderChildren, "true");
        }
        else
        {
            xml.appendOptionalAttribute("hidden", component.isHidden(), "true");
        }

        SectionMode mode = section.getMode();
        if (mode != null)
        {
            switch (mode)
            {
                case LAZY:
                    xml.appendAttribute("mode", "lazy");
                    break;
                case EAGER:
                    xml.appendAttribute("mode", "eager");
                    break;
                default:
                    throw new SystemException("Unknown section mode: " + section.getMode());
            }
        }

        xml.appendClose();

        // Render margin
        MarginRendererUtil.renderMargin(section, renderContext);

        if (renderChildren)
        {
            // Label
            section.getDecoratedLabel().paint(renderContext);
            // Content
            section.getContent().paint(renderContext);
        }

        xml.appendEndTag("ui:section");
    }
    
    /**
     * @param section the section to paint.
     * @return true if the section content needs to be rendered
     */
    private boolean isRenderContent(final WSection section)
    {
        SectionMode mode = section.getMode();

        // EAGER sections only render content if the section is the current AJAX trigger
        if (SectionMode.EAGER.equals(mode))
        {
            return AjaxHelper.isCurrentAjaxTrigger(section);
        }
        // LAZY sections render content if the section is not hidden or it is the current AJAX Trigger (ie content has
        // been requested)
        else if (SectionMode.LAZY.equals(mode))
        {
            return (!section.isHidden() || AjaxHelper.isCurrentAjaxTrigger(section));
        }

        return true;
    }

}
