/**
 * Provides a means to invoke a form submission directly from the change event of a form control. If a form control is
 * marked as 'submitOnChange' then we need to queue up a form submission request when it changes.
 *
 * **NOTE:** this has certain negative accessibility implications around unexpectedly changing context. As a consequence
 * we recommend submitOnChange not be used and it may be removed from future releases.
 *
 * @module
 * @requires module:wc/dom/attribute
 * @requires module:wc/dom/event
 * @requires module:wc/dom/initialise
 * @requires module:wc/dom/shed
 * @requires module:wc/ajax/triggerManager
 * @requires module:wc/dom/serialize
 * @requires module:wc/dom/Widget
 * @requires module:wc/timers
 *
 * @todo document private members, check source order.
 * @deprecated
 */
define(["wc/dom/attribute", "wc/dom/event", "wc/dom/initialise", "wc/dom/shed",
		"wc/ajax/triggerManager",
		"wc/dom/serialize", "wc/dom/Widget", "wc/timers"],
	/** @param attribute wc/dom/attribute @param event wc/dom/event @param initialise wc/dom/initialise @param shed wc/dom/shed @param triggerManager wc/ajax/triggerManager @param serialize wc/dom/serialize @param Widget wc/dom/Widget @param timers wc/timers @ignore */
	function(attribute, event, initialise, shed, triggerManager, serialize, Widget, timers) {
		"use strict";

		/**
		 * @constructor
		 * @alias module:wc/ui/onchangeSubmit~OnchangeSubmit
		 * @private
		 */
		function OnchangeSubmit() {
			var submitting = false,  // this is a safety net to prevent double submits if both the change event and shed subscriber fire.
				SUBMITTER = new Widget("", "wc_soc"),
				LOAD_SELECT = SUBMITTER.extend("", {"${wc.ui.selectLoader.attribute.dataListId}": null}),
				TRIGGERS = [SUBMITTER.extend("", {"type": "checkbox"}),
							SUBMITTER.extend("", {"type": "radio"}),
							SUBMITTER.extend("", {"role": "checkbox"}),
							SUBMITTER.extend("", {"role": "radio"})],
				FORM = new Widget("form"),
				optionOnLoad = [],
				ignoreChange = false,
				DEP_WARNING = "DEPRECATION WARNING: onChangeSubmit is deprecated as it causes accessibility problems. Use AJAX or a submit button.";

			/**
			 * Registry setter helper for selects which are loaded dynamically via a datalist. Stores the option which
			 * was selected on load.
			 * @function
			 * @private
			 * @param {Element} element The select element to store.
			 */
			function setLoadedOptionRegistry(element) {
				var elementId = element.id;
				optionOnLoad[elementId] = getElementValue(element);
			}

			/**
			 * Registry getter helper for selects which are loaded dynamically via a datalist. Get the option which was
			 * selected on load.
			 * @function
			 * @private
			 * @param {Element} element A select element.
			 */
			function getLoadedOptionRegistry(element) {
				var elementId = element.id;
				return optionOnLoad[elementId];
			}

			/**
			 * Registry unsetter helper for selects which are loaded dynamically via a datalist: removes the reference
			 * to the option which was selected on load.
			 * @function
			 * @private
			 * @param {Element} element The select to unset.
			 */
			function removeLoadedOptionRegistry(element) {
				var elementId = element.id;
				optionOnLoad[elementId] = null;
			}

			/**
			 * Get the serialized value of an element if it is a select which is loaded dynamically via a datalist.
			 * @function
			 * @private
			 * @param {Element} element The element to serialize.
			 * @returns {?String} The serialized value of element if it is a cacheable SELECT.
			 */
			function getElementValue(element) {
				var result;
				if (LOAD_SELECT.isOneOfMe(element)) {
					result = serialize.serialize([element]);
				}
				return result;
			}

			/**
			 * Fire the custom submit event if needed. Start the custom event rolling by checking if we have an ajax
			 * trigger if so, don't go any further. Otherwise, check if we have a submitOnChange element and queue a
			 * form submission if we do.
			 * @function
			 * @private
			 * @param {Element} element The element firing the submitOnChange.
			 */
			function fireElement(element) {
				var form, loadedOption, testValue;
				if (!submitting) {
					if (!triggerManager || !triggerManager.getTrigger(element)) {
						if (SUBMITTER.isOneOfMe(element) && (form = FORM.findAncestor(element))) {
							if (LOAD_SELECT.isOneOfMe(element)) {
								loadedOption = getLoadedOptionRegistry(element);
								testValue = getElementValue(element);

								if (loadedOption !== testValue) {
									console.warn(DEP_WARNING);
									timers.setTimeout(event.fire, 0, form, event.TYPE.submit);
								}
								removeLoadedOptionRegistry(element);
							}
							else {
								submitting = true;
								console.warn(DEP_WARNING);
								timers.setTimeout(event.fire, 0, form, event.TYPE.submit);
							}
						}
					}
				}
				else {
					console.warn("onchange submit fired twice");  // this is going to be hard to spot when the page is submitting
				}
			}


			/**
			 * IE focusin listener used to wire up the change listener on the  target element.
			 * @function
			 * @private
			 * @param {Event} $event The focusin event.
			 */
			function focusEvent($event) {
				var target = $event.target,
					inited = "wc/ui/onchangeSubmit.bootstrap";
				if (SUBMITTER.isOneOfMe(target)) {
					if (!attribute.get(target, inited)) {
						attribute.set(target, inited, true);
						event.add(target, event.TYPE.change, changeEvent);
					}
					if (LOAD_SELECT.isOneOfMe(target)) {
						setLoadedOptionRegistry(target);
					}
				}
			}

			/**
			 * Focus event listener for focus for browsers which can wire directly on focus.
			 * Needed by FF & chrome and used to set up the loaded option of a select with options dynamically loaded
			 * from a datalist.
			 * @function
			 * @private
			 * @param {Event} $event The focus event.
			 */
			function domFocusEvent($event) {
				var target = $event.target;
				if (LOAD_SELECT.isOneOfMe(target)) {
					setLoadedOptionRegistry(target);
				}
			}

			/**
			 * Change event listener to start the submit process rolling. This is not for checkboxes and radiobuttons
			 * which will fire through the shed observer it is essentially for dropdowns.
			 * @function
			 * @private
			 * @param {Event} $event the change event.
			 */
			function changeEvent($event) {
				var element = $event.target;
				try {
					if (!$event.defaultPrevented && !ignoreChange && SUBMITTER.isOneOfMe(element) && !Widget.isOneOfMe(element, TRIGGERS)) {
						fireElement(element);
					}
				}
				finally {
					ignoreChange = false;
				}
			}

			/**
			 * Listens to select, deselect, collapse state changes to fire submit on change as required.
			 *
			 * @function
			 * @private
			 * @param {Element} element The element being acted upon.
			 */
			function shedObserver(element) {
				if (Widget.isOneOfMe(element, TRIGGERS)) {
					fireElement(element);
				}
			}

			/**
			 * Set up the core body listeners for submit on change.
			 * @function module:wc/ui/onchangeSubmit.initialise
			 * @public
			 * @param {Element} element The element being initialised - document.body.
			 */
			this.initialise = function(element) {
				if (event.canCapture) {
					event.add(element, event.TYPE.focus, domFocusEvent, null, null, true);
					event.add(element, event.TYPE.change, changeEvent, null, null, true);
				}
				else {
					event.add(element, event.TYPE.focusin, focusEvent);
				}
			};

			/**
			 * Call to initialise this instance - wires up boostrap listeners.
			 * @function module:wc/ui/onchangeSubmit.postInit
			 * @public
			 */
			this.postInit = function() {
				shed.subscribe(shed.actions.SELECT, shedObserver);
				shed.subscribe(shed.actions.DESELECT, shedObserver);
				shed.subscribe(shed.actions.COLLAPSE, shedObserver);
			};

			/**
			 * Set a flag to ignore a change event.
			 * @function module:wc/ui/onchangeSubmit.ignoreNextChange
			 * @public
			 */
			this.ignoreNextChange = function() {
				ignoreChange = true;
			};

			/**
			 * Allow external scripts to clear their own prevent submit on next change event before any change event is
			 * fired.
			 * @function module:wc/ui/onchangeSubmit.clearIgnoreChange
			 * @public
			 */
			this.clearIgnoreChange = function() {
				ignoreChange = false;
			};
		}

		var /** @alias module:wc/ui/onchangeSubmit */ instance = new OnchangeSubmit();
		initialise.register(instance);
		return instance;
	});
