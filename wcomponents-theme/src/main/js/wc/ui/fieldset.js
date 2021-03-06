/**
 * Provides functionality peculiar to FIELDSET elements around ensuring that the contained controls and legends
 * are correctly synchronised after AJAX transactions.
 *
 * @module
 * @requires module:wc/i18n/i18n
 * @requires module:wc/dom/initialise
 * @requires module:wc/dom/shed
 * @requires module:wc/dom/tag
 * @requires module:wc/ui/ajax/processResponse
 * @requires module:wc/ui/getFirstLabelForElement
 * @requires module:wc/dom/Widget
 * @requires module:wc/ui/internalLink
 * @requires module:wc/ui/label
 */
define(["wc/i18n/i18n",
		"wc/dom/initialise",
		"wc/ui/ajax/processResponse",
		"wc/ui/getFirstLabelForElement",
		"wc/dom/Widget",
		"wc/ui/internalLink",
		"wc/ui/label"],
	/** @param i18n wc/i18n/i18n @param initialise wc/dom/initialise @param processResponse wc/ui/ajax/processResponse @param getFirstLabelForElement wc/ui/getFirstLabelForElement @param Widget wc/dom/Widget @ignore */
	function(i18n, initialise, processResponse, getFirstLabelForElement, Widget) {
		"use strict";
		/*
		 * Implicit dependencies:
		 * wc/ui/internalLink is used to add
		 *   * label like functionality (click to focus) to the visible pseudo-label when a fieldset bound component is
		 *   labelled by a WLabel;
		 *   * focus first tabstop when a fieldset bound component is in an error state and the user clicks the link
		 *   generated by the validation error (not limited to client side validation).
		 * wc/ui/label controls some aspects of the fieldset's legend AND any pseudo-label. It is included explicitly
		 *   although it is a third-step dependency of this code anyway.
		 */
		/**
		 * @constructor
		 * @alias module:wc/ui.fieldset~Fieldset
		 * @private
		 */
		function Fieldset() {
			var FSET = new Widget("fieldset"),
				FALLBACK;

			/**
			 * Inserts content of the fieldset legend based on the previous labelling element's content.
			 *
			 * @function
			 * @private
			 * @param {Element} element The transformed checkableGroup root element.
			 * @param {Element} labelElement The original labelling element, either a label or a legend.
			 */
			function populateLegend(element, labelElement) {
				var newLegend = getFirstLabelForElement(element);

				if (!newLegend) {
					newLegend = document.createElement("legend");
					if (element.firstChild) {
						element.insertBefore(newLegend, element.firstChild);
					}
					else {
						element.appendChild(newLegend);
					}
				}

				if (newLegend.innerHTML !== labelElement.innerHTML) {
					newLegend.innerHTML = "";
					newLegend.innerHTML = labelElement.innerHTML;
				}
			}

			/**
			 * Ensures that compound input components (which are each wrapped in a fieldset element) keep their label
			 * when the component is replaced using AJAX and the label is not part of the AJAX transaction.
			 *
			 * @function
			 * @private
			 * @param {Element} element the reference element (element being replaced).
			 * @param {DocumentFragment} documentFragment the document fragment which will be inserted.
			 */
			function ajaxSubscriber(element, documentFragment) {
				var legend, newLegend, newFS;

				// just split up this overly complex if into readable chunks.
				if (!(element && documentFragment)) {
					return;
				}

				if (!((newFS = FSET.findDescendant(documentFragment)) && (newLegend = getFirstLabelForElement(newFS)))) {
					return;
				}

				if ((newLegend.innerHTML.trim() === "" || newLegend.innerHTML.trim() === FALLBACK) && (legend = getFirstLabelForElement(element))) {
					populateLegend(newFS, legend);
				}
			}

			/**
			 * Gets the {@link module:wc/dom/Widget} descriptor for a fieldset element.
			 *
			 * @function module:wc/ui.fieldset.getWidget
			 * @public
			 * @returns {module:wc/dom/Widget} The description of a fieldset.
			 */
			this.getWidget = function() {
				return FSET;
			};

			/**
			 * Initialiser callback to wire in subscribers for AJAX and shed and to set the initial state of controls
			 * inside any disabled fieldsets.
			 *
			 * @function module:wc/ui.fieldset.postInit
			 * @public
			 */
			this.postInit = function() {
				FALLBACK = i18n.get("${wc.common.i18n.requiredLabel}");
				processResponse.subscribe(ajaxSubscriber);
			};
		}
		var /** @alias module:wc/ui.fieldset */ instance = new Fieldset();
		initialise.register(instance);
		return instance;
	});
