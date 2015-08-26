package com.github.openborders.wcomponents.examples.table;

import java.util.List;

import com.github.openborders.wcomponents.Request;
import com.github.openborders.wcomponents.SimpleBeanBoundTableModel;
import com.github.openborders.wcomponents.WBeanContainer;
import com.github.openborders.wcomponents.WDateField;
import com.github.openborders.wcomponents.WDefinitionList;
import com.github.openborders.wcomponents.WHorizontalRule;
import com.github.openborders.wcomponents.WPanel;
import com.github.openborders.wcomponents.WTable;
import com.github.openborders.wcomponents.WTableColumn;
import com.github.openborders.wcomponents.WText;
import com.github.openborders.wcomponents.SimpleBeanBoundTableModel.LevelDetails;
import com.github.openborders.wcomponents.WTable.ExpandMode;
import com.github.openborders.wcomponents.util.TableUtil;

import com.github.openborders.wcomponents.examples.table.PersonBean.TravelDoc;

/**
 * This example demonstrates a simple {@link WTable} that is bean bound and has a custom renderer for expandable rows.
 * <p>
 * Uses {@link SimpleBeanBoundTableModel} to handle the bean binding and define the expandable levels.
 * </p>
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SimpleExpandableContentTableExample extends WPanel
{
    /** The table used in the example. */
    private final WTable table = new WTable();

    /**
     * Create example.
     */
    public SimpleExpandableContentTableExample()
    {
        add(table);

        // Columns
        table.addColumn(new WTableColumn("First name", new WText()));
        table.addColumn(new WTableColumn("Last name", new WText()));
        table.addColumn(new WTableColumn("DOB", new WDateField()));

        // Expand mode
        table.setExpandMode(ExpandMode.DYNAMIC);

        // Use expand all controls
        table.setExpandAll(true);

        // Set expandable level and the custom renderer.
        LevelDetails level = new LevelDetails("documents", TravelDocPanel.class);

        // Setup model - Define bean properties for the columns and the expandable level
        SimpleBeanBoundTableModel model = new SimpleBeanBoundTableModel(new String[] { "firstName", "lastName",
                                                                                      "dateOfBirth" }, level);

        table.setTableModel(model);
    }

    /**
     * Override preparePaintComponent in order to set up the example data the first time that the example is accessed by
     * each user.
     * 
     * @param request the request being responded to.
     */
    @Override
    protected void preparePaintComponent(final Request request)
    {
        super.preparePaintComponent(request);
        if (!isInitialised())
        {
            // Set the data as the bean on the table
            table.setBean(ExampleDataUtil.createExampleData());
            setInitialised(true);
        }
    }

    /**
     * An example component to display travel document details. Expects that the supplied bean is a {@link TravelDoc}.
     */
    public static final class TravelDocPanel extends WBeanContainer
    {
        /**
         * Creates a TravelDocPanel.
         */
        public TravelDocPanel()
        {
            WHorizontalRule rule = new WHorizontalRule()
            {
                @Override
                public boolean isVisible()
                {
                    List<Integer> index = TableUtil.getCurrentRowIndex(TravelDocPanel.this);
                    // On expanded row, so check the index of the expanded level
                    return index.get(1) > 0;
                }

            };
            add(rule);

            WText documentNumber = new WText();
            WText countryOfIssue = new WText();
            WText placeOfIssue = new WText();

            documentNumber.setBeanProperty("documentNumber");
            countryOfIssue.setBeanProperty("countryOfIssue");
            placeOfIssue.setBeanProperty("placeOfIssue");

            WDefinitionList list = new WDefinitionList(WDefinitionList.Type.COLUMN);
            add(list);

            list.addTerm("Document number", documentNumber);
            list.addTerm("Country of issue", countryOfIssue);
            list.addTerm("Place Of issue", placeOfIssue);
        }
    }

}