package com.github.openborders.wcomponents.examples.petstore;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.openborders.wcomponents.MessageContainer;
import com.github.openborders.wcomponents.WApplication;
import com.github.openborders.wcomponents.WCardManager;
import com.github.openborders.wcomponents.WColumn;
import com.github.openborders.wcomponents.WComponent;
import com.github.openborders.wcomponents.WHeading;
import com.github.openborders.wcomponents.WHorizontalRule;
import com.github.openborders.wcomponents.WMessages;
import com.github.openborders.wcomponents.WPanel;
import com.github.openborders.wcomponents.WRow;
import com.github.openborders.wcomponents.WStyledText;
import com.github.openborders.wcomponents.WText;
import com.github.openborders.wcomponents.layout.FlowLayout;
import com.github.openborders.wcomponents.layout.FlowLayout.Alignment;
import com.github.openborders.wcomponents.util.Config;

import com.github.openborders.wcomponents.examples.petstore.beanprovider.CrtBeanProvider;
import com.github.openborders.wcomponents.examples.petstore.model.CartBean;
import com.github.openborders.wcomponents.examples.petstore.model.ConfirmOrderBean;
import com.github.openborders.wcomponents.examples.petstore.model.PlaceOrderService;
import com.github.openborders.wcomponents.examples.petstore.model.PlaceOrderService.OrderStatus;

/**
 * The Pet Store application main component.
 * This is the component that should be run from the LDE.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
public class PetStoreApp extends WApplication implements MessageContainer
{
    /** The logger instance for this class. */
    private static final Log log = LogFactory.getLog(PetStoreApp.class);

    /** The navigation bar provides an easy way for users to navigate around the app. */
    private final NavigationBar navBar = new NavigationBar();

    /** The messagebox used to display messages to the user. */
    private final WMessages messages = new WMessages();

    /** The main application panel, which holds the product listing, product details and cart views. */
    private final WCardManager contentPanel = new WCardManager();

    /** A simple footer that shows the current date. */
    private final WPanel footerPanel = new WPanel();

    /** The default display is the product table, which shows summary information for each product. */
    private final ProductTable productTable = new ProductTable();

    /** The product details view shows more detailed information for a single product. */
    private final ProductDetailView productDetails = new ProductDetailView();

    /** The cart panel displays the current contents of the user's "shopping cart". */
    private final CartPanel cartPanel = new CartPanel();

    /** The order confirmation panel lets the user complete their order. */
    private final ConfirmOrderPanel confirmOrder = new ConfirmOrderPanel();

    static
    {
        // Normally, applications include a main "wcomponent_app.properties" file
        // at the top-level of their jar(s), which includes other application
        // property files.
        //
        // Since this example is normally run in the LDE we add in the PetStore
        // properties manually, so developers don't have to fiddle with their
        // local_app.properties.

        try
        {
            Properties properties = new Properties();
            properties.load(PetStoreApp.class.getResourceAsStream("/com/github/openborders/wcomponents/examples/petstore/resources/petstore.properties"));

            CompositeConfiguration config = new CompositeConfiguration(Config.getInstance());
            config.addConfiguration(new MapConfiguration(properties));
            Config.setConfiguration(config);
        }
        catch (Exception e)
        {
            log.error("Failed to load PetStoreApp properties", e);
        }
    }

    /**
     * Creates a PetStoreApp.
     */
    public PetStoreApp()
    {
        WPanel mainPanel = new WPanel();
        mainPanel.setLayout(new FlowLayout(Alignment.VERTICAL, 0, 5));
        add(mainPanel);

        // Header
        WRow headerPanel = new WRow();
        WColumn leftCol = new WColumn(70);
        WColumn rightCol = new WColumn(30);
        headerPanel.add(leftCol);
        headerPanel.add(rightCol);
        mainPanel.add(headerPanel);

        leftCol.add(new WHeading(WHeading.TITLE, "The WComponent Pet Store"));
        rightCol.add(navBar);

        // Motd
        WStyledText motd = new WStyledText();
        motd.setWhitespaceMode(WStyledText.WhitespaceMode.PARAGRAPHS);
        motd.setBeanProvider(new CrtBeanProvider("message_of_the_day", "DEFAULT"));
        mainPanel.add(motd);

        mainPanel.add(new WHorizontalRule());

        // Application messages
        mainPanel.add(messages);

        // Main content
        mainPanel.add(contentPanel);
        contentPanel.add(productTable);
        contentPanel.add(productDetails);
        contentPanel.add(cartPanel);
        contentPanel.add(confirmOrder);

        mainPanel.add(new WHorizontalRule());

        // Footer
        mainPanel.add(footerPanel);
        footerPanel.setLayout(new FlowLayout(Alignment.RIGHT));

        WText footerText = new WText()
        {
            @Override
            public String getText()
            {
                return new Date().toString();
            }
        };

        footerPanel.add(footerText);
    }

    /**
     * Retrieves the current active section in the application.
     *
     * @return the component for the active section.
     */
    protected WComponent getActiveSection()
    {
        return contentPanel.getVisible();
    }

    /**
     * Displays the product listing for a single user.
     */
    public void showProductListing()
    {
        contentPanel.makeVisible(productTable);
    }

    /**
     * Displays the product details for a single user.
     * @param productId the id of the product to display.
     */
    public void showProductDetails(final int productId)
    {
        productDetails.setBeanId(Integer.valueOf(productId));

        contentPanel.makeVisible(productDetails);
    }

    /**
     * Displays the order confirmation screen for a single user.
     */
    public void showOrderConfirmation()
    {
        contentPanel.makeVisible(confirmOrder);

        // If this is the first time through the confirmation screen,
        // set the bean to be filled in.
        if (confirmOrder.getBean() == null)
        {
            // TODO: Add support to demonstrate (pre-filled) known users?
            confirmOrder.setBean(new ConfirmOrderBean());
        }
    }

    /**
     * Displays the shopping cart for a single user.
     */
    public void showCart()
    {
        contentPanel.makeVisible(cartPanel);
    }

    /**
     * Places a user's order.
     */
    public void placeOrder()
    {
        ConfirmOrderBean orderDetails = (ConfirmOrderBean) confirmOrder.getBean();

        OrderStatus orderStatus =
            PlaceOrderService.getInstance().placeOrder(getCart(), orderDetails);

        switch (orderStatus.getStatus())
        {
            case OrderStatus.SUCCESS:
            {
                String message = "Thanks " + orderDetails.getFirstName()
                    + ", your order has been placed successfully.\n"
                    + "Your order number is #12345, confirmation will be sent to "
                    + orderDetails.getEmailAddress() + '.';

                // Now that the order has been placed successfully, we need to return
                // everything to its default state. Since everything is added to this
                // component, the reset call effectively clears out the entire session.
                reset();

                // Show a confirmation to the user. This is done after the reset, so it isn't wiped out.
                WMessages.getInstance(this).success(message);
                break;
            }
            case OrderStatus.INSUFFICIENT_STOCK:
            {
                WMessages.getInstance(this).error(orderStatus.getUserMessage());
                showProductListing();
                break;
            }
            case OrderStatus.UNKOWN_FAILURE:
            {
                WMessages.getInstance(this).error(orderStatus.getUserMessage());
                log.error("Unknown error: " + orderStatus.getDetails());
            }
            default:
            {
                WMessages.getInstance(this).error(orderStatus.getUserMessage());
                log.error("Unknown response code (" + orderStatus.getStatus() + "): " + orderStatus.getDetails());
            }
        }
    }

    /**
     * Retrieves the contents of the given user's cart.
     *
     * @return the user's cart.
     */
    public List<CartBean> getCart()
    {
        return cartPanel.getCart();
    }

    /**
     * @return the messages
     */
    public WMessages getMessages()
    {
        return messages;
    }
}