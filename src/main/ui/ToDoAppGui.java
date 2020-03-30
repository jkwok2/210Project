package ui;

import model.TaskItem;
import model.ToDoList;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import persistence.Editor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
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
import java.util.ArrayList;

import static persistence.Editor.unpackData;

public class ToDoAppGui extends JFrame implements ActionListener, DocumentListener, TableModelListener,
        PropertyChangeListener {
    //Fields
    private static final String TODO_FILE = "./data/todo_data.json";
    private ToDoList toDoList;
    private TaskItem taskItem;
    private JTable listPanel;
    private DefaultTableModel tableModel;
    private String taskName;
    private JLabel counter;

    String[] menuItems = {"Options - Select Below", "Save Data", "Load Data"};
    String[] columnNames = {"Name", "Description", "Status"};

    public ToDoAppGui() {
        // Creates Window
        super("Jeff's ToDo List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 500);

        // Instantiates taskItem, and toDoList.
        taskItem = new TaskItem();
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
                ti.changeTaskName(text);
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
        ti.changeTaskStatus(text);
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
            Editor savedFile = new Editor(new File(TODO_FILE));
            savedFile.saveData(td);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void loadToDoList() {
        try {
            FileReader reader = new FileReader(new File(TODO_FILE));
            JSONObject jsonObject = unpackData(reader);
            ArrayList<TaskItem> listOfTaskItems = new ArrayList<>();
            ArrayList<String> taskItemNames = (ArrayList<String>) jsonObject.get("Task Item Names");
            ArrayList<String> taskItemDescription = (ArrayList<String>) jsonObject.get("Task Item Descriptions");
            ArrayList<String> taskItemStatus = (ArrayList<String>) jsonObject.get("Task Item Statuses");
            long numTasks = (Long) jsonObject.get("numTasks");
            long numCompleted = (Long) jsonObject.get("numCompleted");
            long numInProgress = (Long) jsonObject.get("numInProgress");
            long numNotStarted = (Long) jsonObject.get("numNotStarted");
            addParam(listOfTaskItems, taskItemNames, taskItemDescription, taskItemStatus);
            toDoList = new ToDoList(listOfTaskItems, numTasks, numNotStarted, numCompleted, numInProgress);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addParam(
            ArrayList<TaskItem> lti, ArrayList<String> tin, ArrayList<String> tid, ArrayList<String> tis) {
        for (int i = 0; i < tin.size(); i++) {
            String taskName = tin.get(i);
            String taskDescription = tid.get(i);
            String taskStatus = tis.get(i);
            lti.add(new TaskItem(taskName, taskDescription, taskStatus));
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

    private String topTextPane() {
        return "Welcome to Jeff's ToDoList!";
    }


    private JPanel getMenu() {
        // Adds panel with layout
        JPanel topPane = new JPanel();
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.Y_AXIS));

        JLabel instructions = new JLabel();
        instructions.setText(topTextPane());

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
        taskItem = new TaskItem();
        taskItem.changeTaskName(taskName.getText());
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
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
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
