package ui;

import exceptions.EmptyException;
import exceptions.NonStatusException;
import model.TaskItem;
import model.ToDoList;
import org.json.simple.parser.ParseException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;


public class ToDoAppGui extends JFrame implements ActionListener, DocumentListener, TableModelListener,
        PropertyChangeListener {
    //Fields
    private static final String TODO_FILE = "./data/todo_data.json";
    private ToDoList toDoList;
    private JTable listPanel;
    private DefaultTableModel tableModel;
    private String taskName;
    private JLabel counter;
    private JLabel instructions;

    String[] menuItems = {"Options - Select Below", "Save Data", "Load Data"};
    String[] columnNames = {"Name", "Description", "Status"};

    public ToDoAppGui() {
        // Creates Window
        super("Jeff's ToDo List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 500);

        // Instantiates taskItem, and toDoList.
        toDoList = new ToDoList();

        // Makes Table
        tableModel = new DefaultTableModel(columnNames, 0);
        listPanel = new JTable(tableModel);

        // Displays instructions to change task name or description.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Double-click to change task name/description");
        listPanel.getColumnModel().getColumn(0).setCellRenderer(renderer);
        listPanel.getColumnModel().getColumn(1).setCellRenderer(renderer);

        tableModelListener();

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

    private void tableModelListener() {
        tableModel.addTableModelListener(e -> {
            int col = e.getColumn();
            int row = e.getFirstRow();
            if (col == 0) {
                String text = (String) listPanel.getModel().getValueAt(row, col);
                TaskItem ti = toDoList.getTaskItem(e.getFirstRow());
                try {
                    ti.changeTaskName(text);
                } catch (EmptyException ex) {
                    instructions.setText("Welcome to Jeff's ToDoList - no empty task names allowed!");
                    listPanel.setValueAt("Sample Task Name", row, col);
                }
            } else if (col == 1) {
                String text = (String) listPanel.getModel().getValueAt(row, col);
                String taskName = (String) listPanel.getModel().getValueAt(row, 0);
                int toDoListRow = toDoList.taskPosition(taskName);
                TaskItem ti = toDoList.getTaskItem(toDoListRow);
                ti.changeDescription(text);
            } else if (col == 2) {
                methodForTableModelListenerIfStatusChanged(col, row);
                resetMenuText();
            }
        });
    }

    private void methodForTableModelListenerIfStatusChanged(int col, int row) {
        String text = (String) listPanel.getModel().getValueAt(row, col);
        String taskName = (String) listPanel.getModel().getValueAt(row, 0);
        int toDoListRow = toDoList.taskPosition(taskName);
        TaskItem ti = toDoList.getTaskItem(toDoListRow);
        String previousStatus = ti.getStatus();
        try {
            ti.changeTaskStatus(text);
        } catch (NonStatusException e) {
            System.out.println("This is not a valid status.");
            e.printStackTrace();
        }
        String newStatus = ti.getStatus();
        tableModelListenerStatusChangedSwitch(previousStatus, newStatus);
    }

    private void tableModelListenerStatusChangedSwitch(String previousStatus, String newStatus) {
        switch (previousStatus) {
            case "Not Started":
                toDoList.subNumNotStarted();
                break;
            case "In Progress":
                toDoList.subNumInProgress();
                break;
            case "Completed":
                toDoList.subNumCompleted();
                break;
        }
        switch (newStatus) {
            case "Not Started":
                toDoList.addNumNotStarted();
                break;
            case "In Progress":
                toDoList.addNumInProgress();
                break;
            case "Completed":
                toDoList.addNumCompleted();
                break;
        }
    }

    public void saveData(ToDoList td) {
        try {
            JsonWriter savedFile = new JsonWriter(new File(TODO_FILE));
            savedFile.saveData(td);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void loadToDoList() {
        try {
            JsonReader reader = new JsonReader(new File(TODO_FILE));
            toDoList = reader.unpackData();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }


    // Reference: TableRenderDemoProject from Oracle
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#TableRenderDemo
    // Sets up task status as a dropdown menu.
    public void setUpStatusDropdown(TableColumn statusColumn) {
        //Set up the dropdown for task status
        JComboBox<String> comboBox = new JComboBox<>();
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


    private JPanel getMenu() {
        // Adds panel with layout
        JPanel topPane = new JPanel();
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.Y_AXIS));

        instructions = new JLabel();
        instructions.setText("Welcome to Jeff's ToDoList!");

        counter = new JLabel();
        resetMenuText();
        JComboBox<String> mainMenu = new JComboBox<>(menuItems);
        mainMenu.setSelectedIndex(0);
        mainMenuActionListener(mainMenu);

        topPane.add(mainMenu);
        topPane.add(instructions);
        topPane.add(counter);
        return topPane;
    }

    private void mainMenuActionListener(JComboBox<String> mainMenu) {
        mainMenu.addActionListener(e -> {
            if (mainMenu.getSelectedIndex() == 1) {
                saveData(toDoList);
            } else if (mainMenu.getSelectedIndex() == 2) {
                loadToDoList();
                tableModel.setRowCount(0);
                int c = 0;
                while (c < toDoList.getToDoListSize()) {
                    tableModel.addRow(
                            new String[]{
                                    toDoList.getTaskItem(c).getTaskName(),
                                    toDoList.getTaskItem(c).getDescription(),
                                    toDoList.getTaskItem(c).getStatus()});
                    c++;
                }
                resetMenuText();
            }
        });
    }

    private void resetMenuText() {
        counter.setText("Tasks Not Started: " + toDoList.getNumberOfTasksNotStarted() + " / "
                + "Tasks In Progress: " + toDoList.getNumberOfTasksInProgress() + " / "
                + "Tasks Completed: " + toDoList.getNumberOfTasksCompleted());
    }

    private JPanel getTaskList() {
        // Adds panel with layout
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        // Creates button to put on panel
        JButton addTask = new JButton("Add Task");
        JButton removeTask = new JButton("Remove Task");
        JTextField taskName = new JTextField();
        addTask.setEnabled(false);
        addTaskListener(addTask, taskName);

        removeTask.addActionListener(e -> {
            if (listPanel.getSelectedRow() != -1) {
                removeTask();
                tableModel.removeRow(listPanel.getSelectedRow());
            }
            resetMenuText();
        });

        addTask.addActionListener(this);
        // Sets out buttons in order from left to right
        buttonPane.add(removeTask);
        buttonPane.add(taskName);
        buttonPane.add(addTask);
        return buttonPane;
    }

    private void addTaskListener(JButton addTask, JTextField taskName) {
        // When Add Task is clicked, input in text area is used to generate a new task.
        addTask.addActionListener(e -> {
            tableModel.addRow(new String[]{taskName.getText(), "", "Not Started"});
            addTask(taskName);
            taskName.setText("");
            addTask.setEnabled(false);
            resetMenuText();
        });

        taskName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                addTask.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    // EFFECTS: Removes a Task
    private void removeTask() {
        if (displayTextExactMatch()) {
            int pos = toDoList.taskPosition(taskName);
            if (pos == -1) {
                System.out.print("No Matching Task.\n");
            } else {
                toDoList.removeTask(pos);
                System.out.print("Task Removed: " + taskName + "\n");
            }
        }
    }

    private boolean displayTextExactMatch() {
        if (toDoList.getToDoListSize() == 0) {
            return false;
        } else {
            int i = listPanel.getSelectedRow();
            taskName = (String) listPanel.getValueAt(i, 0);
            return true;
        }
    }

    private void addTask(JTextField taskName) {
        TaskItem taskItem = new TaskItem();
        try {
            taskItem.changeTaskName(taskName.getText());
        } catch (EmptyException e) {
            e.printStackTrace();
        }
        toDoList.addTask(taskItem);
        System.out.print("Task Added: " + taskName.getText() + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // https://www.soundjay.com/beep-sounds-1.html
        String soundName = "./data/beep-01a.wav";
        playSound(soundName);
    }

    private void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Note - no sounds in this jar");
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void tableChanged(TableModelEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
