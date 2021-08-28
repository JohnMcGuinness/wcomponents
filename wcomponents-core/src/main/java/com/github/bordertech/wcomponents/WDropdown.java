package com.github.bordertech.wcomponents;

import com.github.bordertech.wcomponents.autocomplete.AutocompleteUtil;
import com.github.bordertech.wcomponents.autocomplete.AutocompleteableText;
import com.github.bordertech.wcomponents.autocomplete.segment.AddressPart;
import com.github.bordertech.wcomponents.autocomplete.segment.AddressType;
import com.github.bordertech.wcomponents.autocomplete.segment.AutocompleteSegment;
import com.github.bordertech.wcomponents.autocomplete.segment.PhoneFormat;
import com.github.bordertech.wcomponents.autocomplete.segment.PhonePart;
import com.github.bordertech.wcomponents.autocomplete.type.DateType;
import com.github.bordertech.wcomponents.autocomplete.type.Email;
import com.github.bordertech.wcomponents.autocomplete.type.Numeric;
import com.github.bordertech.wcomponents.autocomplete.type.Password;
import com.github.bordertech.wcomponents.autocomplete.type.Telephone;
import com.github.bordertech.wcomponents.autocomplete.type.Url;
import com.github.bordertech.wcomponents.util.Util;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * The WDropdown component is used to let the user select a single option from a drop-down list. The list of options
 * that can be selected are supplied at construction time as a parameter in the constructor or via the
 * {@link #setOptions(List)} method. The list of options are java objects that are rendered using their toString() by
 * default.</p>
 *
 * <p>
 * Use the {@link #getSelected() getSelected} method to determine which of the list of options was chosen by the user.
 * Note that getSelected returns one of the object instances supplied in the original list of options.</p>
 *
 * @author James Gifford
 * @author Jonathan Austin
 * @author Mark Reeves
 * @since 1.0.0
 */
public class WDropdown extends AbstractWSingleSelectList implements AjaxTrigger, AjaxTarget, SubordinateTrigger, SubordinateTarget,
		AutocompleteableText {

	@Override
	public String getAutocomplete() {
		return getComponentModel().autocomplete;
	}

	@Override
	public void setAutocompleteOff() {
		if (!isAutocompleteOff()) {
			getOrCreateComponentModel().autocomplete = AutocompleteUtil.getOff();
		}
	}

	@Override
	public void addAutocompleteSection(final String sectionName) {
		String newValue = AutocompleteUtil.getCombinedForAddSection(sectionName, this);
		if (!Util.equals(getAutocomplete(), newValue)) {
			getOrCreateComponentModel().autocomplete = newValue;
		}
	}

	@Override
	public void clearAutocomplete() {
		if (getAutocomplete() != null) {
			getOrCreateComponentModel().autocomplete = null;
		}
	}

	/**
	 * does the work of converting the various types of autocomplete helper to the {@code autocomplete} attribute values.
	 * @param value the value for the {@code autocomplete} attribute
	 */
	private void setAutocomplete(final String value) {
		if (!Util.equals(getAutocomplete(), value)) {
			getOrCreateComponentModel().autocomplete = value;
		}
	}

	@Override
	public void setAutocomplete(final DateType value) {
		final String strType = value == null ? null : value.getValue();
		setAutocomplete(strType);
	}

	@Override
	public void setAutocomplete(final Email value) {
		final String strType = value == null ? null : value.getValue();
		setAutocomplete(strType);
	}

	@Override
	public void setAutocomplete(final Numeric value) {
		final String strType = value == null ? null : value.getValue();
		setAutocomplete(strType);
	}

	@Override
	public void setAutocomplete(final Password value) {
		final String strType = value == null ? null : value.getValue();
		setAutocomplete(strType);
	}

	@Override
	public void setAutocomplete(final Url value) {
		final String strType = value == null ? null : value.getValue();
		setAutocomplete(strType);
	}

	@Override
	public void setAutocomplete(final AutocompleteSegment value) {
		final String strType = value == null ? null : value.getValue();
		setAutocomplete(strType);
	}

	@Override
	public void setAutocomplete(final Telephone phone, final PhoneFormat phoneType) {
		setAutocomplete(AutocompleteUtil.getCombinedFullPhone(phoneType, phone));
	}

	@Override
	public void setPhoneSegmentAutocomplete(final PhoneFormat phoneType, final PhonePart phoneSegment) {
		setAutocomplete(AutocompleteUtil.getCombinedPhoneSegment(phoneType, phoneSegment));
	}

	@Override
	public void setAddressAutocomplete(final AddressType addressType, final AddressPart addressPart) {
		setAutocomplete(AutocompleteUtil.getCombinedAddress(addressType, addressPart));
	}

	/**
	 * Creates an empty WDropdown.
	 */
	public WDropdown() {
		this((List) null);
	}

	/**
	 * Creates a WDropdown with the specified options.
	 *
	 * @param options the drop down options.
	 */
	public WDropdown(final Object[] options) {
		this(Arrays.asList(options));
	}

	/**
	 * Creates a WDropdown with the specified options.
	 *
	 * @param options the drop down options.
	 */
	public WDropdown(final List options) {
		super(options, false);
	}

	/**
	 * Creates a WDropdown with the options provided by the given table.
	 *
	 * @param table the table to obtain the dropdown's options from.
	 */
	public WDropdown(final Object table) {
		super(table, false);
	}

	/**
	 * Creates a new DropdownModel which holds Extrinsic state management of the drop down.
	 *
	 * @return a new DropdownModel.
	 */
	@Override
	protected DropdownModel newComponentModel() {
		return new DropdownModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override // For type safety only
	protected DropdownModel getComponentModel() {
		return (DropdownModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override // For type safety only
	protected DropdownModel getOrCreateComponentModel() {
		return (DropdownModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the drop down.
	 */
	public static class DropdownModel extends SelectionModel {

		/**
		 * The auto-fill hint for the field.
		 */
		private String autocomplete;
	}
}
