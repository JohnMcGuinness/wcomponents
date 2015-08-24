package com.github.openborders.container;

import com.github.openborders.RenderContext;
import com.github.openborders.Request;
import com.github.openborders.UIContext;
import com.github.openborders.UIContextHolder;
import com.github.openborders.util.Config;

/**
 * This interceptor is used as the top-level component in both Portlet and 
 * Servlet environments. 
 * 
 * @author Martin Shevchenko
 * @since 1.0.0
 */
public class FormInterceptor extends InterceptorComponent
{
    /**
     * Override serviceRequest in order to perform processing specific to this interceptor.
     * 
     * @param request the request being responded to.
     */
    @Override
    public void serviceRequest(final Request request)
    {
        // Reset the focus for this new request.
        UIContext uic = UIContextHolder.getCurrent();
        uic.setFocussed(null, null);
        
        // We've hit the action phase, so we do want focus on this app.
        uic.setFocusRequired(true);
        
        super.serviceRequest(request);
    }
    
    /**
     * Override preparePaint in order to perform processing specific to this interceptor.
     * 
     * @param request the request being responded to.
     */
    @Override
    public void preparePaint(final Request request)
    {
        // Reset the form encoding type before painting the content.
        UIContext uic = UIContextHolder.getCurrent();
        uic.getEnvironment().setFormEncType(null);
        
        super.preparePaint(request);
    }

    /**
     * Override paint in order to perform processing specific to this interceptor.
     * 
     * @param renderContext the renderContext to send the output to.
     */
    @Override
    public void paint(final RenderContext renderContext)
    {
        getBackingComponent().paint(renderContext);
        
        // We don't want to remember the focus for the next render because on
        // a multi portlet page, we'd end up with multiple portlets trying to
        // set the focus.
        UIContext uic = UIContextHolder.getCurrent();
        
        if (uic.isFocusRequired())
        {
            boolean sticky = Config.getInstance().getBoolean("wcomponent.stickyFocus", false);
            
            if (!sticky)
            {
                uic.setFocussed(null, null);
                uic.setFocusRequired(false);
            }            
        }
    }
}
