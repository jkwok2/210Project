package ui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoAppGui extends JFrame implements ActionListener {

    String[] menuItems = {"Options - Select Below", "Save Data", "Load Data"};


    public ToDoAppGui() {
        // Creates Window
        super("Jeff's ToDo List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500,500);

        // Make Table
        String[] columnNames = {"Name", "Description", "Status"};
        Object[][] toDoListStartData = {{"Sample Task", "Sample Description", "Sample Status"}};
        JTable toDoListPanel = new JTable(toDoListStartData, columnNames);
        toDoListPanel.setPreferredScrollableViewportSize(new Dimension(500, 70));
        toDoListPanel.setFillsViewportHeight(true);

        // Adds scroll bar on the right
        JScrollPane scrollPane = new JScrollPane(toDoListPanel);


        // Adds dropdown menu, list, and panel
        this.add(getMenu(), BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(getTaskList(), BorderLayout.SOUTH);

        // Display Window
//        this.pack();
        this.setVisible(true);
    }

//    class MyTableModel extends AbstractTableModel {
//        private String[] columnNames = {"Name", "Description", "Status"};
//        private Object[][] toDoListStartData = {{"Sample Task", "Sample Description", "Sample Status"}};
//
//        @Override
//        public int getRowCount() {
//            return columnNames.length;
//        }
//
//        @Override
//        public int getColumnCount() {
//            return toDoListStartData.length;
//        }
//
//        public String getColumnName(int col) {
//            return columnNames[col];
//        }
//
//        @Override
//        public Object getValueAt(int rowIndex, int columnIndex) {
//            return toDoListStartData[rowIndex][columnIndex];
//        }

//        public boolean isCellEditable(int row, int col) {
//            //Note that the data/cell address is constant,
//            //no matter where the cell appears onscreen.
//            if (col < 1) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//
//        /*
//         * Don't need to implement this method unless your table's
//         * data can change.
//         */
//        public void setValueAt(Object value, int row, int col) {
//            toDoListStartData[row][col] = value;
//            fireTableCellUpdated(row, col);
//        }
//
//        private void printDebugData() {
//            int numRows = getRowCount();
//            int numCols = getColumnCount();
//
//            for (int i=0; i < numRows; i++) {
//                System.out.print("    row " + i + ":");
//                for (int j=0; j < numCols; j++) {
//                    System.out.print("  " + toDoListStartData[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("--------------------------");
//        }
//    }


    private JPanel getToDoList() {
        JPanel toDoListPanel = new JPanel();

        // Creates List in Center
        DefaultListModel listItems = new DefaultListModel<>();
        listItems.addElement("Sample Task");
        JList list = new JList(listItems);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        toDoListPanel.add(scrollPane, BorderLayout.LINE_START);
        return toDoListPanel;
    }

    private JComboBox getMenu() {
        // Creates Dropdown menu on top with options
        JComboBox mainMenu = new JComboBox(menuItems);
        mainMenu.setSelectedIndex(0);
        mainMenu.addActionListener(this);
        return mainMenu;
    }

    private JPanel getTaskList() {

        // Adds panel with layout
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        // Creates button to put on panel
        JButton addTask = new JButton("Add Task");
        JButton removeTask = new JButton("Remove Task");
        JTextField taskName = new JTextField();
        // Sets out buttons in order from left to right
        buttonPane.add(removeTask);
        buttonPane.add(taskName);
        buttonPane.add(addTask);
        return buttonPane;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
