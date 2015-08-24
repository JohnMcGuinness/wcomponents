package com.github.openborders.render.webxml; 

import com.github.openborders.WComponent;
import com.github.openborders.WMenuItem;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.servlet.WebXmlRenderContext;
import com.github.openborders.util.Util;

/** 
 * The Renderer for {@link WMenuItem}.
 * 
 * @author Yiannis Paschalidis 
 * @since 1.0.0
 */
final class WMenuItemRenderer extends AbstractWebXmlRenderer
{
    /**
     * Paints the given WMenuItem.
     * 
     * @param component the WMenuItem to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WMenuItem item = (WMenuItem) component;
        XmlStringBuilder xml = renderContext.getWriter();
        
        xml.appendTagOpen("ui:menuItem");
        xml.appendAttribute("id", component.getId());
        xml.appendOptionalAttribute("track", component.isTracking(), "true");
        
        if (item.isSubmit())
        {
            xml.appendAttribute("submit", "true");
        }
        else
        {
            xml.appendOptionalAttribute("url", item.getUrl());
            xml.appendOptionalAttribute("targetWindow", item.getTargetWindow());
        }
        
        xml.appendOptionalAttribute("disabled", item.isDisabled(), "true");
        xml.appendOptionalAttribute("hidden", item.isHidden(), "true");
        xml.appendOptionalAttribute("selected", item.isSelected(), "true");
        xml.appendOptionalAttribute("selectable", item.isSelectable());
        xml.appendOptionalAttribute("accessKey", Util.upperCase(item.getAccessKeyAsString()));
        xml.appendOptionalAttribute("cancel", item.isCancel(), "true");
        xml.appendOptionalAttribute("msg", item.getMessage());
        xml.appendOptionalAttribute("toolTip", item.getToolTip());
        
        xml.appendClose();

        item.getDecoratedLabel().paint(renderContext);
        
        xml.appendEndTag("ui:menuItem");        
    }
}
