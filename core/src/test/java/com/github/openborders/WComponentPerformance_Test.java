package com.github.openborders; 

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.openborders.Action;
import com.github.openborders.ActionEvent;
import com.github.openborders.Request;
import com.github.openborders.UIContext;
import com.github.openborders.WApplication;
import com.github.openborders.WBeanContainer;
import com.github.openborders.WButton;
import com.github.openborders.WLabel;
import com.github.openborders.WTextField;
import com.github.openborders.WebUtilities;
import com.github.openborders.servlet.WServletPerformance_Test;
import com.github.openborders.util.mock.MockRequest;
import com.github.openborders.util.mock.servlet.MockHttpServletRequest;
import com.github.openborders.util.mock.servlet.MockHttpServletResponse;
import com.github.openborders.util.mock.servlet.MockHttpSession;

/**
 * Tests to check the performance of WComponent request processing.
 * This test does not check for correct behaviour - see the respective
 * tests for WApplication etc.
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
@Category(PerformanceTests.class)
// TODO: Parameterise the test so that we can test for "n" components.
public class WComponentPerformance_Test extends AbstractWComponentTestCase
{
    /** The logger instance for this class. */
    private static final Log log = LogFactory.getLog(WServletPerformance_Test.class);
    
    /**
     * Basic sanity-test to ensure that the WComponent implementation is
     * performing all the processing that it should.
     */
    @Test
    public void testWComponentAppCorrectness() throws Exception
    {
        final UIContext uic = createUIContext();
        final SimpleApp app = new SimpleApp();
        sendWComponentRequest(app, uic, 0);
        sendWComponentRequest(app, uic, 1);
        
        setActiveContext(uic);
        Assert.assertEquals("Incorrect property1 value", "p1_1", ((SimpleFormBean) app.beanContainer.getBean()).getProperty1());
        Assert.assertEquals("Incorrect property2 value", "p2_1", ((SimpleFormBean) app.beanContainer.getBean()).getProperty2());
    }

    /**
     * Basic sanity-test to ensure that the basic implementation is
     * performing all the processing that it should.
     */
    @Test
    public void testOtherImplementationCorrectness() throws Exception
    {
        MockHttpSession session = new MockHttpSession();
        sendSimpleRequest(session, 0);
        sendSimpleRequest(session, 1);

        SimpleFormBean bean = (SimpleFormBean) session.getAttribute("formBean");
        Assert.assertEquals("Incorrect property1 value", "p1_1", bean.getProperty1());
        Assert.assertEquals("Incorrect property2 value", "p2_1", bean.getProperty2());
    }
    
    @Test
    public void testRequestHandlingPerformance() throws Exception
    {
        final int numLoops = 2000;

        long simpleTime = timeOtherServlet(numLoops) / numLoops;
        long wcomponentTime = timeWComponentProcessing(numLoops) / numLoops;

        log.info("Simple request handling time: "+ (simpleTime / 1000000.0) + "ms");
        log.info("WComponent request handling time: " + (wcomponentTime / 1000000.0) + "ms");
        
        Assert.assertTrue("WComponent request handling time should not exceed 10x simple time", wcomponentTime < simpleTime * 10);
    }
    
    /**
     * Times the WComponent execution looping the given number of times and returns the elapsed time.
     * 
     * @param count the number of times to loop.
     * @return the elapsed time, in nanoseconds.
     */
    private long timeWComponentProcessing(final int count) throws Exception
    {
        final UIContext uic = createUIContext();
        final SimpleApp app = new SimpleApp();

        // JIT warm-up
        for (int i = 0; i < count; i++)
        {
            sendWComponentRequest(app, uic, i);
        }
        
        final UIContext uic2 = createUIContext();
        
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < count; i++)
                {
                    sendWComponentRequest(app, uic2, i);
                }
            }
        };
        
        return time(runnable);
    }
    
    /**
     * Times the other servlet execution looping the given number of times and returns the elapsed time.
     * 
     * @param count the number of times to loop.
     * @return the elapsed time, in nanoseconds.
     */
    private long timeOtherServlet(final int count) throws Exception
    {
        final HttpSession session = new MockHttpSession();

        // JIT warm-up
        for (int i = 0; i < count; i++)
        {
            sendSimpleRequest(session, i);
        }
        
        final HttpSession session2 = new MockHttpSession();
        
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < count; i++)
                {
                    sendSimpleRequest(session2, i);
                }
            }
        };
        
        return time(runnable);
    }

    /**
     * Invokes WComponent servlet processing.
     * 
     * @param app the component to invoke request processing on.
     * @param uic the user context to use.
     */
    private void sendWComponentRequest(final SimpleApp app, final UIContext uic, final int step)
    {
        // The parameter names are hard-coded to avoid overhead during performance testing
        MockRequest request = new MockRequest();

        request.setParameter("txt1", "p1_" + step);
        request.setParameter("txt2", "p2_" + step);
        request.setParameter("btn", "x");
        
        setActiveContext(uic);
        app.serviceRequest(request);
        app.preparePaint(request);
    }

    /**
     * Invokes simple request processing.
     * 
     * @param servlet the servlet to invoke request processing on.
     * @param uic the user context to use.
     */
    private void sendSimpleRequest(final HttpSession session, final int step)
    {
        MockHttpServletRequest request = new MockHttpServletRequest(session);        
        request.setParameter("formBean.property1", "p1_" + step);
        request.setParameter("formBean.property2", "p2_" + step);
        request.setParameter("submit", "Submit");
        
        doSimpleRequest(request, new MockHttpServletResponse());
    }

    /**
     * A simple WComponent application.
     */
    public static final class SimpleApp extends WApplication 
    {
        private final WBeanContainer beanContainer = new WBeanContainer();
        
        /** Creates a SimpleApp. */ 
        public SimpleApp()
        {
            add(beanContainer);
            
            WTextField property1 = new WTextField();
            WTextField property2 = new WTextField();

            property1.setBeanProperty("property1");
            property2.setBeanProperty("property2");

            property1.setIdName("txt1");
            property2.setIdName("txt2");
            
            beanContainer.add(new WLabel("Property 1:", property1));
            beanContainer.add(property1);
            beanContainer.add(new WLabel("Property 2:", property2));
            beanContainer.add(property2);
            
            WButton submit = new WButton("Submit");
            submit.setIdName("btn");
            
            submit.setAction(new Action()
            {
                @Override
                public void execute(final ActionEvent event)
                {
                    WebUtilities.updateBeanValue(beanContainer);
                }
            });
            
            beanContainer.add(submit);
        }
        
        @Override
        protected void preparePaintComponent(final Request request)
        {
            super.preparePaintComponent(request);
            
            if (!isInitialised())
            {
                beanContainer.setBean(new SimpleFormBean());
                setInitialised(true);
            }
        }
    }
    
    /**
     * Simple struts-like request processing.
     * 
     * @param request the request to handle.
     * @param response ignored - rendering is not tested.
     */
    protected static void doSimpleRequest(final HttpServletRequest request, final HttpServletResponse response)
    {
        if (request.getParameter("submit") != null)
        {
            HttpSession session = request.getSession(true);
            SimpleFormBean formBean = (SimpleFormBean) session.getAttribute("formBean");

            if (formBean == null)
            {
                formBean = new SimpleFormBean();
                session.setAttribute("formBean", formBean);
            }
            
            Map<String, String[]> properties = new HashMap<String, String[]>();

            for (Enumeration<?> names = request.getParameterNames(); names.hasMoreElements();)
            {
                String key = (String) names.nextElement();

                if (key.startsWith("formBean"))
                {
                    properties.put(key.substring("formBean.".length()), request.getParameterValues(key));
                }
            }

            try
            {
                BeanUtils.populate(formBean, properties);
            }
            catch (Exception e)
            {
                log.error("Failed to execute test", e);
            }
        }
    }

    /**
     * A simple bean with arbitrary properties.
     */
    public static final class SimpleFormBean
    {
        /** An arbitrary property. */
        private String property1;

        /** An arbitrary property. */
        private String property2;

        /**
         * @return Returns the property1.
         */
        public String getProperty1()
        {
            return property1;
        }

        /**
         * @param property1 The property1 to set.
         */
        public void setProperty1(final String property1)
        {
            this.property1 = property1;
        }

        /**
         * @return Returns the property2.
         */
        public String getProperty2()
        {
            return property2;
        }

        /**
         * @param property2 The property2 to set.
         */
        public void setProperty2(final String property2)
        {
            this.property2 = property2;
        }
    }
}
