package com.github.bordertech.wcomponents.render.webxml;

import com.github.bordertech.wcomponents.OptionGroup;
import com.github.bordertech.wcomponents.Renderer;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.XmlStringBuilder;
import com.github.bordertech.wcomponents.servlet.WebXmlRenderContext;
import com.github.bordertech.wcomponents.util.Util;
import java.util.List;

/**
 * The {@link Renderer} for {@link WDropdown}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
final class WDropdownRenderer extends AbstractWebXmlRenderer {

	/**
	 * Paints the given WDropdown.
	 *
	 * @param component the WDropdown to paint.
	 * @param renderContext the RenderContext to paint to.
	 */
	@Override
	public void doRender(final WComponent component, final WebXmlRenderContext renderContext) {
		WDropdown dropdown = (WDropdown) component;
		XmlStringBuilder xml = renderContext.getWriter();
		boolean readOnly = dropdown.isReadOnly();
		String dataKey = dropdown.getListCacheKey();

		// Start tag
		xml.appendTagOpen("ui:dropdown");
		xml.appendAttribute("id", component.getId());
		xml.appendOptionalAttribute("class", component.getHtmlClass());
		xml.appendOptionalAttribute("track", component.isTracking(), "true");
		xml.appendOptionalAttribute("hidden", dropdown.isHidden(), "true");
		if (readOnly) {
			xml.appendAttribute("readOnly", "true");
		} else {
			xml.appendOptionalAttribute("data", dataKey != null, dataKey);
			xml.appendOptionalAttribute("disabled", dropdown.isDisabled(), "true");
			xml.appendOptionalAttribute("required", dropdown.isMandatory(), "true");
			xml.appendOptionalAttribute("submitOnChange", dropdown.isSubmitOnChange(), "true");
			xml.appendOptionalAttribute("toolTip", dropdown.getToolTip());
			xml.appendOptionalAttribute("accessibleText", dropdown.getAccessibleText());
			String autocomplete = dropdown.getAutocomplete();
			xml.appendOptionalAttribute("autocomplete", !Util.empty(autocomplete), autocomplete);
		}
		xml.appendClose();

		// Options
		List<?> options = dropdown.getOptions();
		Object selectedOption = dropdown.getSelected();

		if (options != null) {
			int optionIndex = 0;
			boolean renderSelectionsOnly = readOnly || dataKey != null;

			for (Object option : options) {
				if (option instanceof OptionGroup) {
					xml.appendTagOpen("ui:optgroup");
					xml.appendAttribute("label", ((OptionGroup) option).getDesc());
					xml.appendClose();

					for (Object nestedOption : ((OptionGroup) option).getOptions()) {
						renderOption(dropdown, nestedOption, optionIndex++, xml, selectedOption,
								renderSelectionsOnly);
					}

					xml.appendEndTag("ui:optgroup");
				} else {
					renderOption(dropdown, option, optionIndex++, xml, selectedOption,
							renderSelectionsOnly);
				}
			}
		}

		if (!readOnly) {
			DiagnosticRenderUtil.renderDiagnostics(dropdown, renderContext);
		}
		// End tag
		xml.appendEndTag("ui:dropdown");
	}

	/**
	 * Renders a single option within the dropdown.
	 *
	 * @param dropdown the dropdown being rendered.
	 * @param option the option to render.
	 * @param optionIndex the index of the option. OptionGroups are not counted.
	 * @param html the XmlStringBuilder to paint to.
	 * @param selectedOption the dropdown's selected option.
	 * @param renderSelectionsOnly true to only render selected options, false to render all options.
	 */
	private void renderOption(final WDropdown dropdown, final Object option,
			final int optionIndex, final XmlStringBuilder html, final Object selectedOption,
			final boolean renderSelectionsOnly) {
		boolean selected = Util.equals(option, selectedOption);

		if (selected || !renderSelectionsOnly) {
			// Get Code and Desc
			String code = dropdown.getCode(option, optionIndex);
			String desc = dropdown.getDesc(option, optionIndex);

			// Check for null option (ie null or empty). Match isEmpty() logic.
			boolean isNull = option == null ? true : (option.toString().length() == 0);

			// Render option
			html.appendTagOpen("ui:option");
			html.appendAttribute("value", code);
			html.appendOptionalAttribute("selected", selected, "true");
			html.appendOptionalAttribute("isNull", isNull, "true");
			html.appendClose();
			html.appendEscaped(desc);
			html.appendEndTag("ui:option");
		}
	}
}
