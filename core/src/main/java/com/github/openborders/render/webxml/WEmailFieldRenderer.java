package com.github.openborders.render.webxml;

import com.github.openborders.WComponent;
import com.github.openborders.WEmailField;
import com.github.openborders.WSuggestions;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.servlet.WebXmlRenderContext;

/**
 * The Renderer for {@link WEmailField}. 
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
final class WEmailFieldRenderer extends AbstractWebXmlRenderer
{
    /**
     * Paints the given WEmailField.
     * 
     * @param component the WEmailField to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WEmailField field = (WEmailField) component;
        XmlStringBuilder xml = renderContext.getWriter();
        int cols = field.getColumns();
        int maxLength = field.getMaxLength();
        
        WSuggestions suggestions = field.getSuggestions();
        String suggestionsId = suggestions == null ? null : suggestions.getId();
        
        WComponent submitControl = field.getDefaultSubmitButton();
        String submitControlId = submitControl == null ? null : submitControl.getId(); 

        xml.appendTagOpen("ui:emailField");
        xml.appendAttribute("id", component.getId());
        xml.appendOptionalAttribute("track", component.isTracking(), "true");
        xml.appendOptionalAttribute("disabled", field.isDisabled(), "true");
        xml.appendOptionalAttribute("hidden", component.isHidden(), "true");
        xml.appendOptionalAttribute("required", field.isMandatory(), "true");
        xml.appendOptionalAttribute("readOnly", field.isReadOnly(), "true");
        xml.appendOptionalAttribute("maxLength", maxLength > 0, maxLength);
        xml.appendOptionalAttribute("tabIndex", field.hasTabIndex(), field.getTabIndex());
        xml.appendOptionalAttribute("toolTip", field.getToolTip());
        xml.appendOptionalAttribute("accessibleText", field.getAccessibleText());
        xml.appendOptionalAttribute("size", cols > 0, cols);
        xml.appendOptionalAttribute("buttonId", submitControlId);
        xml.appendOptionalAttribute("list", suggestionsId);
        xml.appendClose();
        
        xml.appendEscaped(field.getText());
        
        xml.appendEndTag("ui:emailField");
    }
}
