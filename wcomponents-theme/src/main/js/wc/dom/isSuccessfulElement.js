/**
 * @module
 * @requires module:wc/dom/shed
 * @requires module:wc/dom/tag
 */
define(["wc/dom/shed", "wc/dom/tag"], /** @param shed wc/dom/shed @param tag wc/dom/tag @ignore */ function(shed, tag) {
	"use strict";
	/**
	 * Tests if a form control is "successful" is the W3C sense of the word:
	 * {@link http://www.w3.org/TR/html401/interact/forms.html#h-17.13.2}
	 *
	 * @function module:wc/dom/isSuccessfulElement
	 * @param {Element} element A form control element.
	 * @param {boolean} [buttonsAlwaysSucceed] If true and element is a button that meets other criteria for being
	 * successful then the button will be successful.
	 * @returns {boolean} true if the element is successful
	 */
	function isSuccessfulElement(element, buttonsAlwaysSucceed) {
		var tagName,
			type;
		if (shed.isDisabled(element) || !element.name) {
			return false;
		}

		tagName = element.tagName;
		type = element.type;

		switch (tagName) {
			case tag.INPUT:
				switch (type) {
					case "button":
						if (buttonsAlwaysSucceed) {
							break;
						}
					/* falls through */
					case "submit": case "reset": case "image":  // removed case:file since this has changed and file is always successful just like text
						return false;
					case "checkbox": case "radio":
						return shed.isSelected(element);
				}
				break;
			case tag.BUTTON:
				return !!(buttonsAlwaysSucceed && type !== "submit");
			case tag.SELECT:
				return (element.selectedIndex >= 0);  // don't submit if selectedIndex -1
			case tag.OBJECT:
				return !element.hasAttribute("declare");
		}
		return true;
	}
	return isSuccessfulElement;
});
