package ui;

import model.TaskItem;
import model.ToDoList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoAppGui extends JFrame implements ActionListener {
    //Fields
    private static final String TODO_FILE = "./data/todo_data.json";
    private ToDoList toDoList;
    private TaskItem taskItem;
    private JTable listPanel;

    String[] menuItems = {"Options - Select Below", "Save Data", "Load Data"};

    public ToDoAppGui() {
        // Creates Window
        super("Jeff's ToDo List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 500);

        // Instantiates taskItem, and toDoList.
        taskItem = new TaskItem();
        toDoList = new ToDoList();

        // Make Table
        String[] columnNames = {"Name", "Description", "Status"};
        Object[][] toDoListStartData = {{"Sample Task", "Sample Description", "Not Started"}};
        listPanel = new JTable(toDoListStartData, columnNames);

        // Displays instructions to change task name or description.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Double-click to change task name/description");
        listPanel.getColumnModel().getColumn(0).setCellRenderer(renderer);
        listPanel.getColumnModel().getColumn(1).setCellRenderer(renderer);

        // Sets up the status column to display a dropdown box.
        setUpStatusDropdown(listPanel.getColumnModel().getColumn(2));

        // Adds scroll bar on the right
        JScrollPane scrollPane = new JScrollPane(listPanel);

        // Adds dropdown menu, list, and panel
        this.add(getMenu(), BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(getTaskList(), BorderLayout.SOUTH);

        // Display Window
        this.setVisible(true);
    }

    // Reference: TableRenderDemoProject from Oracle
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#TableRenderDemo
    public void setUpStatusDropdown(TableColumn statusColumn) {
        //Set up the dropdown for task status
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Not Started");
        comboBox.addItem("In Progress");
        comboBox.addItem("Completed");
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Displays instructions for changing status.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click to change status");
        statusColumn.setCellRenderer(renderer);
    }

    private String topTextPane() {
        String header = "Welcome! Hover over elements to view instructions.";
        return header;
    }


    private JPanel getMenu() {
        // Adds panel with layout
        JPanel topPane = new JPanel();
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.Y_AXIS));
        // Doesn't work
//        topPane.setAlignmentX(LEFT_ALIGNMENT);

        //
        JLabel instructions = new JLabel();
        // TODO: Change this to be interactive
        instructions.setText(topTextPane());

        JLabel counter = new JLabel();
        counter.setText("Tasks Not Started: " + toDoList.getNumberOfTasksNotStarted() + " / "
                + "Tasks In Progress: " + toDoList.getNumberOfTasksInProgress() + " / "
                + "Tasks Completed: " + toDoList.getNumberOfTasksCompleted());
        // Doesn't Work
//        counter.setHorizontalAlignment(JLabel.LEFT);

        // Creates Dropdown menu on top with options
        JComboBox mainMenu = new JComboBox(menuItems);
        mainMenu.setSelectedIndex(0);
        mainMenu.addActionListener(this);

        topPane.add(mainMenu);
        topPane.add(instructions);
        topPane.add(counter);
        return topPane;
    }

    private JPanel getTaskList() {
        // Adds panel with layout
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        // Creates button to put on panel
        JButton addTask = new JButton("Add Task");
        JButton removeTask = new JButton("Remove Task");
        JTextField taskName = new JTextField();

        // When Add Task is clicked, input in text area is used to generate a new task.
        addTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        addTask.addActionListener(this);
        // Sets out buttons in order from left to right
        buttonPane.add(removeTask);
        buttonPane.add(taskName);
        buttonPane.add(addTask);
        return buttonPane;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().beep();
    }
}
