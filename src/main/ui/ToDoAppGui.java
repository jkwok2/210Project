package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoAppGui extends JFrame implements ActionListener {

    String[] menuItems = {"Options - Select Below", "Save Data",
            "Change Task Status", "Change Name", "Change Description", "Load Data"};

    public ToDoAppGui() {
        // Creates Window
        super("Jeff's ToDo List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creates Dropdown menu on top with options
        JComboBox mainMenu = new JComboBox(menuItems);
        mainMenu.setSelectedIndex(0);
        mainMenu.addActionListener(this);

        // Creates List in Center
        DefaultListModel listItems = new DefaultListModel<>();
        JList list = new JList(listItems);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        // Adds dropdown menu, list, and panel
        this.add(mainMenu, BorderLayout.NORTH);
        this.add(listScrollPane, BorderLayout.CENTER);
        this.add(getTaskList(), BorderLayout.SOUTH);

        // Display Window
        this.pack();
        this.setVisible(true);
    }

    private JPanel getTaskList() {
        // Adds Panel with Add/Remove
        JPanel buttonPane = new JPanel();
        JButton addTask = new JButton("Add Task");
        JButton removeTask = new JButton("Remove Task");
        JTextField taskName = new JTextField();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
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
