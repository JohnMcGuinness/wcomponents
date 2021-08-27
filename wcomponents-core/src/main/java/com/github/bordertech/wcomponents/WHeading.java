package com.github.bordertech.wcomponents;

import java.util.List;

/**
 * This component is used to render the different types of headings within an application.
 *
 * @author Adam Millard
 * @author Yiannis Paschalidis
 */
public class WHeading extends WText implements Container, AjaxTarget, Marginable {

	/**
	 * The label for the heading.
	 */
	private final WDecoratedLabel label;

	/**
	 * Creates a WHeading.
	 *
	 * @param headingLevel the heading level
	 * @param text the heading text
	 */
	public WHeading(final HeadingLevel headingLevel, final String text) {
		super(text);
		this.label = null;
		setHeadingLevel(headingLevel);
	}

	/**
	 * Creates a WHeading.
	 *
	 * @param headingLevel the heading level
	 * @param label the heading
	 */
	public WHeading(final HeadingLevel headingLevel, final WDecoratedLabel label) {
		this.label = label;
		add(label);
		setupLabel();
		setHeadingLevel(headingLevel);
	}

	/**
	 * Set up the label.
	 */
	private void setupLabel() {
		// To retain compatibility with the WText API, create a WText for this component,
		// which gets added to the label body.
		WText textBody = new WText() {
			@Override
			public boolean isEncodeText() {
				return WHeading.this.isEncodeText();
			}

			@Override
			public String getText() {
				return WHeading.this.getText();
			}
		};

		if (label.getBody() == null) {
			label.setBody(textBody);
		} else {
			WComponent oldBody = label.getBody();

			WContainer newBody = new WContainer();
			label.setBody(newBody);

			newBody.add(textBody);
			newBody.add(oldBody);
		}
	}

	/**
	 * @return the heading level
	 */
	public HeadingLevel getHeadingLevel() {
		return getComponentModel().headingLevel;
	}

	/**
	 * @param headingLevel the heading level
	 */
	public void setHeadingLevel(final HeadingLevel headingLevel) {
		if (headingLevel == null) {
			throw new IllegalArgumentException("Cannot set a null heading level.");
		}
		getOrCreateComponentModel().headingLevel = headingLevel;
	}

	/**
	 * @return the decorated label which displays the heading's text/icon etc.
	 */
	public WDecoratedLabel getDecoratedLabel() {
		return label;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMargin(final Margin margin) {
		getOrCreateComponentModel().margin = margin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Margin getMargin() {
		return getComponentModel().margin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// to make public
	public int getChildCount() {
		return super.getChildCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// to make public
	public WComponent getChildAt(final int index) {
		return super.getChildAt(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// to make public
	public int getIndexOfChild(final WComponent childComponent) {
		return super.getIndexOfChild(childComponent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<WComponent> getChildren() {
		return super.getChildren();
	}

	/**
	 * @return a String representation of this component, for debugging purposes.
	 */
	@Override
	public String toString() {
		String text = getText();
		text = text == null ? "null" : ('"' + text + '"');
		return toString(text, -1, -1);
	}

	/**
	 * Creates a new Component model.
	 *
	 * @return a new HeadingModel.
	 */
	@Override
	// For type safety only
	protected HeadingModel newComponentModel() {
		return new HeadingModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// For type safety only
	protected HeadingModel getComponentModel() {
		return (HeadingModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// For type safety only
	protected HeadingModel getOrCreateComponentModel() {
		return (HeadingModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the component.
	 */
	public static class HeadingModel extends TextModel {

		/**
		 * The margins to be used on the heading.
		 */
		private Margin margin;

		/**
		 * The heading level.
		 */
		private HeadingLevel headingLevel;
	}

}
