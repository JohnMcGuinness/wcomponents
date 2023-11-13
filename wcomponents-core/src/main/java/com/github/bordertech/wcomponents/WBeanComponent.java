package com.github.bordertech.wcomponents;

import com.github.bordertech.wcomponents.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * WBeanComponent provides a default implementation of a bean-aware component, and is the basis for most bean-aware
 * WComponents. It can be used as a starting point for custom application bean-aware components.
 * <p>
 * A fix has been made to the logic for a bean container, that has a bean property set, to pass the correct bean value
 * to its child bean components. This fix is an "opt in". To enable the correct bean container logic set the parameter
 * "bordertech.wcomponents.bean.logic.correct=true".
 * </p>
 *
 * @author Yiannis Paschalidis
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WBeanComponent extends AbstractWComponent implements DataBound {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(WBeanComponent.class);

	/**
	 * Returns the data for this component. The following are searched in order:
	 * <ul>
	 * <li>a value set explicitly in the ui context using {@link #setData(Object)};</li>
	 * </ul>
	 *
	 * @return the current value of this component for the given context
	 */
	@Override
	public Object getData() {
		return getComponentModel().getData();
	}

	/**
	 * Sets the data that this component displays/edits. For bean aware components, this should only be called from
	 * handleRequest to set user-entered data.
	 *
	 * @param data the data to set
	 */
	@Override
	public void setData(final Object data) {
		UIContext uic = UIContextHolder.getCurrent();

		if (uic == null) {
			getComponentModel().setData(data);
		} else {
			Object sharedValue = ((DataBoundComponentModel) getDefaultModel()).getData();

			getOrCreateComponentModel().setData(data);
			setFlag(ComponentModel.USER_DATA_SET, !Util.equals(data, sharedValue));
		}
	}

	/**
	 * Indicates whether this component's data has changed from the default value.
	 *
	 * TODO: This needs to be added to the databound interface after the bulk of the components have been converted.
	 *
	 * @return true if this component's current value differs from the default value for the given context.
	 */
	public boolean isChanged() {
		Object currentValue = getData();
		Object sharedValue = ((DataBoundComponentModel) getDefaultModel()).getData();

		return !Util.equals(currentValue, sharedValue);
	}

	/**
	 * Resets the data back to the default value, which may either be from a bean or the shared model.
	 */
	public void resetData() {
		getOrCreateComponentModel().resetData();
	}

	/**
	 * Creates a new model appropriate for this component.
	 *
	 * @return a new {@link DataBoundComponentModel}.
	 */
	@Override
	protected DataBoundComponentModel newComponentModel() {
		return new DataBoundComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override // For type safety only
	protected DataBoundComponentModel getComponentModel() {
		return (DataBoundComponentModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override // For type safety only
	protected DataBoundComponentModel getOrCreateComponentModel() {
		return (DataBoundComponentModel) super.getOrCreateComponentModel();
	}
}
