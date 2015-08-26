package com.github.openborders.wcomponents.examples.theme; 

import java.util.ArrayList;
import java.util.List;

import com.github.openborders.wcomponents.Request;
import com.github.openborders.wcomponents.WBeanContainer;
import com.github.openborders.wcomponents.WComponent;
import com.github.openborders.wcomponents.WFieldLayout;
import com.github.openborders.wcomponents.WHeading;
import com.github.openborders.wcomponents.WHorizontalRule;
import com.github.openborders.wcomponents.WList;
import com.github.openborders.wcomponents.WPanel;
import com.github.openborders.wcomponents.WText;

import com.github.openborders.wcomponents.examples.common.SimpleTableBean;

/** 
 * This component shows the usage of a {@link WList} component.
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
public class WListExample extends WPanel
{
    /** We need to keep track of the lists in this example, so that we can give them data. */
    private final List<WList> lists = new ArrayList<WList>();
    
    /**
     * Creates a WListExample.
     */
    public WListExample()
    {
        super(Type.BLOCK);

        add(new WHeading(WHeading.SECTION, "Simple horizontal list with a bar separator."));
        WText nameRenderer = new WText();
        nameRenderer.setBeanProperty("name");
        addList(WList.Type.FLAT, WList.Separator.BAR, false, nameRenderer);
        add(new WHorizontalRule());

        add(new WHeading(WHeading.SECTION, "Simple vertical list with a dot separator."));
        nameRenderer = new WText();
        nameRenderer.setBeanProperty("name");
        addList(WList.Type.STACKED, WList.Separator.DOT, false, nameRenderer);
        add(new WHorizontalRule());
        
        add(new WHeading(WHeading.SECTION, "Striped list with a border and complex content."));
        addList(WList.Type.STRIPED, null, true, new SimpleListRenderer());
    }
    
    /**
     * Adds a list to the example.
     * 
     * @param type the list type.
     * @param separator the list item separator.
     * @param renderBorder true to render a border around the list, false otherwise.
     * @param renderer the component to use to render items in the list.
     */
    private void addList(final WList.Type type, final WList.Separator separator, final boolean renderBorder, final WComponent renderer)
    {
        WList list = new WList(type);
        
        if (separator != null)
        {
            list.setSeparator(separator);
        }
        
        list.setRenderBorder(renderBorder);        
        list.setRepeatedComponent(renderer);
        add(list);
        
        lists.add(list);
    }

    /**
     * Simple WDataRenderer implementation for the list.
     * Expects the bean to be a {@link SimpleTableBean}.
     * 
     * @author Yiannis Paschalidis
     */
    public static class SimpleListRenderer extends WBeanContainer
    {
        /**
         * Creates a SimpleListRenderer.
         */
        public SimpleListRenderer()
        {
            WText name = new WText();
            WText type = new WText();
            WText thing = new WText();
            
            name.setBeanProperty("name");
            type.setBeanProperty("type");
            thing.setBeanProperty("thing");
            
            WFieldLayout fields = new WFieldLayout();
            fields.addField("Name", name);
            fields.addField("Type", type);
            fields.addField("Thing", thing);
            add(fields);
        }
    }

    /**
     * Override preparePaintComponent to perform initialisation the first time through.
     * 
     * @param request the request being responded to.
     */
    @Override
    public void preparePaintComponent(final Request request)
    {
        if (!isInitialised())
        {
            List<SimpleTableBean> items = new ArrayList<SimpleTableBean>();
            items.add(new SimpleTableBean("A", "none", "thing"));
            items.add(new SimpleTableBean("B", "some", "thing2"));
            items.add(new SimpleTableBean("C", "little", "thing3"));
            items.add(new SimpleTableBean("D", "lots", "thing4"));

            for (WList list : lists)
            {
                list.setData(items);
            }
            
            setInitialised(true);
        }
    }
}