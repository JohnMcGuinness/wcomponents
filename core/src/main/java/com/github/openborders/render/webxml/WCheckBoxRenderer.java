package com.github.openborders.render.webxml;

import com.github.openborders.Renderer;
import com.github.openborders.WCheckBox;
import com.github.openborders.WComponent;
import com.github.openborders.WComponentGroup;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.servlet.WebXmlRenderContext;

/**
 * The {@link Renderer} for {@link WCheckBox}.
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
final class WCheckBoxRenderer extends AbstractWebXmlRenderer
{
    /**
     * Paints the given WCheckBox.
     * 
     * @param component the WCheckBox to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WCheckBox checkBox = (WCheckBox) component;
        XmlStringBuilder xml = renderContext.getWriter();
        WComponent submitControl = checkBox.getDefaultSubmitButton();
        String submitControlId = submitControl == null ? null : submitControl.getId();
        WComponentGroup<WCheckBox> group = checkBox.getGroup();
        String groupName = group == null ? null : group.getId();

        xml.appendTagOpen("ui:checkBox");
        xml.appendAttribute("id", component.getId());
        xml.appendOptionalAttribute("track", component.isTracking(), "true");
        xml.appendOptionalAttribute("groupName", groupName);
        xml.appendOptionalAttribute("disabled", checkBox.isDisabled(), "true");
        xml.appendOptionalAttribute("hidden", checkBox.isHidden(), "true");
        xml.appendOptionalAttribute("required", checkBox.isMandatory(), "true");
        xml.appendOptionalAttribute("readOnly", checkBox.isReadOnly(), "true");
        xml.appendOptionalAttribute("selected", checkBox.isSelected(), "true");
        xml.appendOptionalAttribute("submitOnChange", checkBox.isSubmitOnChange(), "true");
        xml.appendOptionalAttribute("tabIndex", component.hasTabIndex(), component.getTabIndex());
        xml.appendOptionalAttribute("toolTip", checkBox.getToolTip());
        xml.appendOptionalAttribute("accessibleText", checkBox.getAccessibleText());
        xml.appendOptionalAttribute("buttonId", submitControlId);        
        xml.appendEnd();
    }

}
