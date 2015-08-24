package com.github.openborders.render.webxml;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.openborders.WComponent;
import com.github.openborders.WDateField;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.servlet.WebXmlRenderContext;

/**
 * The Renderer for {@link WDateField}.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
final class WDateFieldRenderer extends AbstractWebXmlRenderer
{

    /** This date format is used internally to exchange dates between the client and server. */
    private static final String INTERNAL_DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * Paints the given WDateField.
     * 
     * @param component the WDateField to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WDateField dateField = (WDateField) component;
        XmlStringBuilder xml = renderContext.getWriter();

        WComponent submitControl = dateField.getDefaultSubmitButton();
        String submitControlId = submitControl == null ? null : submitControl.getId();
        Date date = dateField.getDate();
        Date minDate = dateField.getMinDate();
        Date maxDate = dateField.getMaxDate();

        xml.appendTagOpen("ui:dateField");
        xml.appendAttribute("id", component.getId());
        xml.appendOptionalAttribute("track", component.isTracking(), "true");
        xml.appendOptionalAttribute("disabled", dateField.isDisabled(), "true");
        xml.appendOptionalAttribute("hidden", dateField.isHidden(), "true");
        xml.appendOptionalAttribute("required", dateField.isMandatory(), "true");
        xml.appendOptionalAttribute("readOnly", dateField.isReadOnly(), "true");
        xml.appendOptionalAttribute("tabIndex", dateField.hasTabIndex(), String.valueOf(dateField.getTabIndex()));
        xml.appendOptionalAttribute("toolTip", dateField.getToolTip());
        xml.appendOptionalAttribute("accessibleText", dateField.getAccessibleText());
        xml.appendOptionalAttribute("buttonId", submitControlId);
        
        if (date != null)
        {
            xml.appendAttribute("date", new SimpleDateFormat(INTERNAL_DATE_FORMAT).format(date));
        }
        
        if (minDate != null)
        {
            xml.appendAttribute("min", new SimpleDateFormat(INTERNAL_DATE_FORMAT).format(minDate));
        }
        
        if (maxDate != null)
        {
            xml.appendAttribute("max", new SimpleDateFormat(INTERNAL_DATE_FORMAT).format(maxDate));
        }
        
        xml.appendClose();
        
        if (date == null)
        {
            xml.appendEscaped(dateField.getText());
        }
        
        xml.appendEndTag("ui:dateField");
    }
}
