package com.github.openborders.wcomponents.render.webxml;

import com.github.openborders.wcomponents.WComponent;
import com.github.openborders.wcomponents.WMessageBox;
import com.github.openborders.wcomponents.XmlStringBuilder;
import com.github.openborders.wcomponents.servlet.WebXmlRenderContext;

/**
 * The Renderer for the {@link WMessageBox} component.
 * 
 * @author Yiannis Paschalidis
 * @author Jonathan Austin
 * @since 1.0.0
 */
final class WMessageBoxRenderer extends AbstractWebXmlRenderer
{
    /**
     * Paints the given WMessageBox.
     * 
     * @param component the WMessageBox to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WMessageBox messageBox = (WMessageBox) component;
        XmlStringBuilder xml = renderContext.getWriter();
        
        if (messageBox.hasMessages())
        {
            xml.appendTagOpen("ui:messageBox");
            xml.appendAttribute("id", component.getId());
            xml.appendOptionalAttribute("track", component.isTracking(), "true");

            switch (messageBox.getType())
            {
                case SUCCESS:
                    xml.appendOptionalAttribute("type", "success");
                    break;
                    
                case INFO:
                    xml.appendOptionalAttribute("type", "info");
                    break;
                    
                case WARN:
                    xml.appendOptionalAttribute("type", "warn");
                    break;
                    
                case ERROR:
                default:
                    xml.appendOptionalAttribute("type", "error");
                    break;
            }
            
            xml.appendClose();

            for (String message : messageBox.getMessages())
            {
                xml.appendTag("ui:message");
                xml.print(message);
                xml.appendEndTag("ui:message");
            }
            
            xml.appendEndTag("ui:messageBox");
        }
    }
}