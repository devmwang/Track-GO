package ui;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import model.*;

public class PlayerListCellRenderer extends JList<String> implements TableCellRenderer {
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
