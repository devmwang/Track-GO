package ui;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import model.*;

// Represents a custom cell renderer that renders a list of players vertically
public class PlayerListCellRenderer extends JList<String> implements TableCellRenderer {
    // REQUIRES: table, list, isSelected, hasFocus, row, column are not null
    // EFFECTS: Returns a cell renderer to render the list of players in a roster
    @Override
    public Component getTableCellRendererComponent(JTable table, Object list, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (list instanceof ArrayList<?>) {
            ArrayList<Player> playerList = (ArrayList<Player>) list;

            String[] playersStringList = new String[playerList.size()];

            for (int i = 0; i < playerList.size(); i++) {
                playersStringList[i] = playerList.get(i).getUsername();
            }

            setListData(playersStringList);
        }

        if (isSelected) {
            setBackground(UIManager.getColor("Table.selectionBackground"));
        } else {
            setBackground(UIManager.getColor("Table.background"));
        }

        return this;
    }
}
