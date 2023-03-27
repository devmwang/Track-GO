package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class PlayerListCellRenderer extends JList<String> implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if (value instanceof String[]) {
            setListData((String[]) value);
        }

        if (isSelected) {
            setBackground(UIManager.getColor("Table.selectionBackground"));
        } else {
            setBackground(UIManager.getColor("Table.background"));
        }

        return this;
    }
}
