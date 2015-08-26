package com.github.openborders.wcomponents.examples;

import com.github.openborders.wcomponents.Action;
import com.github.openborders.wcomponents.ActionEvent;
import com.github.openborders.wcomponents.Message;
import com.github.openborders.wcomponents.Request;
import com.github.openborders.wcomponents.WApplication;
import com.github.openborders.wcomponents.WButton;
import com.github.openborders.wcomponents.WContainer;
import com.github.openborders.wcomponents.WHeading;
import com.github.openborders.wcomponents.WLink;
import com.github.openborders.wcomponents.WMessages;
import com.github.openborders.wcomponents.WPanel;
import com.github.openborders.wcomponents.WText;
import com.github.openborders.wcomponents.layout.FlowLayout;
import com.github.openborders.wcomponents.layout.FlowLayout.Alignment;

/**
 * Demonstrate how to use {@link WApplication} and the unsavedChanges feature.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WApplicationExample extends WContainer
{
    /** Text message. */
    private final WText msg = new WText();

    /** Information Messages. */
    private final WMessages messages = new WMessages();

    /**
     * Create WApplicationExample.
     */
    public WApplicationExample()
    {

        WPanel root = new WPanel();
        root.setLayout(new FlowLayout(Alignment.VERTICAL));

        root.add(messages);

        root.add(new WHeading(1, "Display unsaved changes warning message"));
        root.add(msg);

        // Setup Links to navigate away from page
        WLink link1 = new WLink("lets go to google in a new window", "http://www.google.com");
        root.add(link1);
        WLink link2 = new WLink("lets go to google in the same window", "http://www.google.com");
        link2.setOpenNewWindow(false);
        root.add(link2);

        // Setup buttons to set/reset the unsavedChanges flag
        WPanel buttonPanel = new WPanel();
        buttonPanel.setLayout(new FlowLayout(Alignment.LEFT));
        WButton changed = new WButton("set changed");
        changed.setAction(new Action()
        {
            public void execute(final ActionEvent event)
            {
                WApplication appl = findApplication();
                
                if (appl != null)
                {
                    appl.setUnsavedChanges(true);
                }
            }
        });
        WButton clear = new WButton("clear changed");
        clear.setAction(new Action()
        {
            public void execute(final ActionEvent event)
            {
                WApplication appl = findApplication();
                
                if (appl != null)
                {
                    appl.setUnsavedChanges(false);
                }
            }
        });
        buttonPanel.add(changed);
        buttonPanel.add(clear);

        root.add(buttonPanel);
        add(root);
    }

    /**
     * @param request the request being processed
     */
    @Override
    protected void preparePaintComponent(final Request request)
    {
        WApplication appl = findApplication();
        
        if (appl != null)
        {
            msg.setText("Unsaved changes: " + (appl.hasUnsavedChanges() ? "Yes" : "No"));
        }
    }

    /**
     * Find the closest WApplication instance.
     * 
     * @return the closest WApplication instance
     */
    private WApplication findApplication()
    {
        WApplication appl = WApplication.instance(this);
        
        if (appl == null)
        {
            messages.addMessage(new Message(Message.WARNING_MESSAGE,
                                                 "There is no WApplication available for this example."));
        }
        
        return appl;
    }

}