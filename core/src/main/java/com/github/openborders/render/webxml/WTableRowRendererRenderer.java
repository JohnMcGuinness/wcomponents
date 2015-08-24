package com.github.openborders.render.webxml;

import java.util.List;

import com.github.openborders.Renderer;
import com.github.openborders.UIContext;
import com.github.openborders.UIContextHolder;
import com.github.openborders.WComponent;
import com.github.openborders.WRepeater;
import com.github.openborders.WTable;
import com.github.openborders.WTableColumn;
import com.github.openborders.WTableRowRenderer;
import com.github.openborders.XmlStringBuilder;
import com.github.openborders.WTable.ExpandMode;
import com.github.openborders.WTable.RowIdWrapper;
import com.github.openborders.WTable.SelectMode;
import com.github.openborders.WTable.TableModel;
import com.github.openborders.servlet.WebXmlRenderContext;
import com.github.openborders.util.TableUtil;

/**
 * {@link Renderer} for the {@link WTableRowRenderer} component.
 * 
 * @author Jonathan Austin
 * @since 1.0.0
 */
final class WTableRowRendererRenderer extends AbstractWebXmlRenderer
{
    /**
     * Paints the given WTableRowRenderer.
     * 
     * @param component the WTableRowRenderer to paint.
     * @param renderContext the RenderContext to paint to.
     */
    @Override
    public void doRender(final WComponent component, final WebXmlRenderContext renderContext)
    {
        WTableRowRenderer renderer = (WTableRowRenderer) component;
        XmlStringBuilder xml = renderContext.getWriter();
        WTable table = renderer.getTable();
        TableModel dataModel = table.getTableModel();

        int[] columnOrder = table.getColumnOrder();
        final int numCols = columnOrder == null ? table.getColumnCount() : columnOrder.length;
        
        // Get current row details
        RowIdWrapper wrapper = renderer.getCurrentRowIdWrapper();
        List<Integer> rowIndex = wrapper.getRowIndex();

        boolean tableSelectable = table.getSelectMode() != SelectMode.NONE;
        boolean rowSelectable = tableSelectable && dataModel.isSelectable(rowIndex);
        boolean rowSelected = rowSelectable && table.getSelectedRows().contains(wrapper.getRowKey());

        boolean tableExpandable = table.getExpandMode() != WTable.ExpandMode.NONE;
        boolean rowExpandable = tableExpandable && dataModel.isExpandable(rowIndex) && wrapper.hasChildren();
        boolean rowExpanded = rowExpandable && table.getExpandedRows().contains(wrapper.getRowKey());

        String rowIndexAsString = TableUtil.rowIndexListToString(rowIndex);

        xml.appendTagOpen("ui:tr");
        xml.appendAttribute("rowIndex", rowIndexAsString);
        xml.appendOptionalAttribute("unselectable", !rowSelectable, "true");
        xml.appendOptionalAttribute("selected", rowSelected, "true");
        xml.appendOptionalAttribute("disabled", dataModel.isDisabled(rowIndex), "true");
        xml.appendOptionalAttribute("expandable", rowExpandable && !rowExpanded, "true");
        xml.appendClose();

        for (int i = 0; i < numCols; i++)
        {
            int colIndex = columnOrder == null ? i : columnOrder[i];
            WTableColumn col = table.getColumn(colIndex);

            if (col.isVisible())
            {
                xml.appendTag("ui:td");
                renderer.getRenderer(colIndex).paint(renderContext);
                xml.appendEndTag("ui:td");
            }
        }

        if (rowExpandable)
        {
            xml.appendTagOpen("ui:subTr");
            xml.appendOptionalAttribute("open", rowExpanded, "true");
            xml.appendClose();

            if (rowExpanded || table.getExpandMode() == ExpandMode.CLIENT)
            {
                renderChildren(renderer, renderContext, wrapper.getChildren());
            }

            xml.appendEndTag("ui:subTr");
        }

        xml.appendEndTag("ui:tr");
    }

    /**
     * @param renderer the WTableRowRenderer to paint.
     * @param renderContext the RenderContext to paint to.
     * @param children the children ids
     */

    private void renderChildren(final WTableRowRenderer renderer, final WebXmlRenderContext renderContext,
                                final List<RowIdWrapper> children)
    {
        XmlStringBuilder xml = renderContext.getWriter();

        WTable table = renderer.getTable();
        WRepeater repeater = table.getRepeater();
        TableModel dataModel = table.getTableModel();

        // If there is a renderer specified by any child, we only paint content that has a specified renderer
        boolean rendererPresent = false;
        for (RowIdWrapper child : children)
        {
            if (dataModel.getRendererClass(child.getRowIndex()) != null)
            {
                rendererPresent = true;
                break;
            }
        }

        // Paint immediate children only.
        if (rendererPresent)
        {
            xml.appendTagOpen("ui:content");
            // Always span all columns
            xml.appendAttribute("spanAllCols", "true");
            xml.appendClose();

            for (RowIdWrapper child : children)
            {
                UIContext nodeContext = repeater.getRowContext(child, child.getPosition());

                WComponent expandedRenderer = renderer.getExpandedTreeNodeRenderer(dataModel.getRendererClass(child
                    .getRowIndex()));

                if (expandedRenderer != null)
                {
                    UIContextHolder.pushContext(nodeContext);

                    try
                    {
                        expandedRenderer.paint(renderContext);
                    }
                    finally
                    {
                        UIContextHolder.popContext();
                    }
                }
            }

            xml.appendEndTag("ui:content");
        }
        else
        {
            for (RowIdWrapper child : children)
            {
                UIContext nodeContext = repeater.getRowContext(child, child.getPosition());

                UIContextHolder.pushContext(nodeContext);

                try
                {
                    render(renderer, renderContext);
                }
                finally
                {
                    UIContextHolder.popContext();
                }
            }
        }

    }

}
