package com.github.openborders.subordinate;

import junit.framework.Assert;

import org.junit.Test;

import com.github.openborders.AbstractWComponent;
import com.github.openborders.AbstractWComponentTestCase;
import com.github.openborders.ComponentModel;
import com.github.openborders.Disableable;
import com.github.openborders.SubordinateTarget;
import com.github.openborders.WComponentGroup;
import com.github.openborders.WLabel;
import com.github.openborders.WTextField;
import com.github.openborders.subordinate.AbstractAction;
import com.github.openborders.subordinate.DisableInGroup;

/**
 * Test cases for {@link DisableInGroup}.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DisableInGroup_Test extends AbstractWComponentTestCase
{
    @Test
    public void testConstructor()
    {
        SubordinateTarget target = new MyTarget();
        WComponentGroup<SubordinateTarget> group = new WComponentGroup<SubordinateTarget>();
        DisableInGroup action = new DisableInGroup(target, group);

        Assert.assertEquals("Value for DisableIn should be true", Boolean.TRUE, action.getValue());
        Assert.assertEquals("Target for DisableIn should be the group", group, action.getTarget());
        Assert.assertEquals("TargetInGroup for DisableIn should be the target", target, action.getTargetInGroup());
    }

    @Test
    public void testDisableInWComponentGroup()
    {
        MyTarget target1 = new MyTarget();
        MyTarget target2 = new MyTarget();
        MyTarget target3 = new MyTarget();
        WComponentGroup<SubordinateTarget> group = new WComponentGroup<SubordinateTarget>();
        group.addToGroup(target1);
        group.addToGroup(target2);
        group.addToGroup(target3);
        DisableInGroup action = new DisableInGroup(target2, group);

        // Check all enabled
        AssertTargetUtil.assertTargetsEnabled(target1, target2, target3);

        // Execute Action
        setActiveContext(createUIContext());
        action.execute();

        // Check only target2 is disabled
        Assert.assertFalse("Target1 should be enabled", target1.isDisabled());
        Assert.assertTrue("Target2 should be disabled", target2.isDisabled());
        Assert.assertFalse("Target3 should be enabled", target3.isDisabled());
    }

    @Test
    public void testActionType()
    {
        SubordinateTarget target = new WTextField();
        WComponentGroup<SubordinateTarget> group = new WComponentGroup<SubordinateTarget>();
        group.addToGroup(target);
        DisableInGroup action = new DisableInGroup(target, group);
        Assert.assertEquals("Incorrect Action Type", action.getActionType(), AbstractAction.ActionType.DISABLEIN);
    }

    @Test
    public void testToString()
    {
        SubordinateTarget target1 = new MyTarget();
        SubordinateTarget target2 = new MyTarget();
        SubordinateTarget target3 = new MyTarget();

        WComponentGroup<SubordinateTarget> group = new WComponentGroup<SubordinateTarget>();
        group.addToGroup(target1);
        group.addToGroup(target2);
        group.addToGroup(target3);

        DisableInGroup action = new DisableInGroup(target2, group);
        Assert.assertEquals("Incorrect toString for action",
                            "disable MyTarget in WComponentGroup([MyTarget, MyTarget, MyTarget])", action.toString());

        new WLabel("test label", target2);
        Assert.assertEquals("Incorrect toString for action with a label",
                            "disable test label in WComponentGroup([MyTarget, MyTarget, MyTarget])", action.toString());
    }

    /**
     * Test component that implements the SubordinateTarget interface.
     */
    private static class MyTarget extends AbstractWComponent implements SubordinateTarget, Disableable
    {
        /**
         * {@inheritDoc}
         */
        public boolean isDisabled()
        {
            return isFlagSet(ComponentModel.DISABLED_FLAG);
        }

        /**
         * {@inheritDoc}
         */
        public void setDisabled(final boolean disabled)
        {
            setFlag(ComponentModel.DISABLED_FLAG, disabled);
        }
    }
}
