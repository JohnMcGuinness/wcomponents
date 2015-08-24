package com.github.openborders.subordinate;

import junit.framework.Assert;

import org.junit.Test;

import com.github.openborders.AbstractWComponent;
import com.github.openborders.AbstractWComponentTestCase;
import com.github.openborders.SubordinateTarget;
import com.github.openborders.WLabel;
import com.github.openborders.subordinate.AbstractAction;
import com.github.openborders.subordinate.Hide;

/**
 * Test cases for {@link Hide}.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Hide_Test extends AbstractWComponentTestCase
{
    @Test
    public void testConstructor()
    {
        SubordinateTarget target = new MyTarget();
        Hide action = new Hide(target);

        Assert.assertEquals("Value for Hide should be false", Boolean.FALSE, action.getValue());
        Assert.assertEquals("Target for Hide should be the target", target, action.getTarget());
    }

    @Test
    public void testActionType()
    {
        Hide action = new Hide(new MyTarget());
        Assert.assertEquals("Incorrect Action Type", AbstractAction.ActionType.HIDE, action.getActionType());
    }

    @Test
    public void testToString()
    {
        MyTarget target = new MyTarget();

        Hide action = new Hide(target);
        Assert.assertEquals("Incorrect toString for action", "hide MyTarget", action.toString());

        WLabel label = new WLabel("test label", target);
        Assert.assertEquals("Incorrect toString for action with a label", "hide " + label.getText(), action.toString());
    }

    /**
     * Test component that implements the SubordinateTarget interface.
     */
    private static class MyTarget extends AbstractWComponent implements SubordinateTarget
    {
    }

}
