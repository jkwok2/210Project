# Productivity All-In-One

#### To-Do List to help you be more productive!


  What will the application do?


The application is a ToDo List. *The To-Do list features the name, description, and status of the task.* A 
user can add and remove tasks, as well as change the name, description, and status.
 
  Who will use it? /  Why is this project of interest to you?

Anyone who wants to be able to track their tasks. I was interested in creating an application 
that would improve productivity, especially one that combined many all the functions of a todo list, timer, and tracker.
There are often moments when one feels that they are productive but aren't actually accomplishing anything, and vice 
versa. I hope to be able to produce that data and assist users that way.

## User Stories
1. As a user, I want to add a task to the ToDoList.
2. As a user, I want to remove a task from the ToDoList.
3. As a user, I want to display the Name, Description, and Status of a TaskItem.
4. As a user, I want to display the number of tasks Not Started, In Progress, and Completed.
5. As a user, I want an option to save all entered tasks to a file.
6. As a user, I want to retrieve all saved tasks from a file. 

## Instructions for Grader
Every time a task is modified on the GUI, it is also modified in the ToDoList and TaskItem classes. This also how the 
save and load functions of the ToDoList are implemented. 

Add X to Y #1: Click 'Add Task' button to add new TaskItem (taken from the JTextArea) to add a task to the GUI and 
create a new TaskItem and add it to the ToDoList Class in model.

Add X to Y #2: Click 'Remove Task' button to remove selected TaskItem (taken from the JTable) to remove a task to the 
GUI and create a new TaskItem and add it to the ToDoList Class in model.

Other User Stories: The name, description, and status of a TaskItem is displayed on the GUI interface. The number
of tasks not started, in progress, and completed are displayed in a JLabel at the top. This is updated every time a task
is added or removed.

Audiovisual Component: 'beep.wav' sound played when 'Add Task' button is clicked and task is added.

Save state of application: Menu dropdown 'Save Data' under 'Options - Select Below'

Load state of application: Menu dropdown 'Load Data' under 'Options - Select Below'

## Phase 4
Task 2: I have chosen to make my TaskItem class Robust.
First, I added an exception to change Task Status to highlight any change that would make the status not "Completed",
"In Progress", or "Not Started". Previously, I had three separate methods for these, so this would also allow me to 
refactor my code and make the code more simple. Instead of the three methods changeTaskStatusToInProgress(), 
changeTaskStatusToCompleted(), and changeTaskStatusToNotStarted(), there is now only one changeTaskStatus(string newStatus),
with an exception added for anything that is not one of those three above statuses.
Second, I would add an exception to change TaskName, when the taskname was being 
changed to an empty field from an already populated field. This is an exception because it is unlikely that a user
would want to 

Task 3 - Cohesion Issues:

A: My Editor Class included both a reader and writer for saving and loading data. This is not a single responsibility, 
so I decided to separate that into two separate classes under persistence to improve cohesion. I also revised the 
associated persistence class tests.

B: I noticed within the GUI class that the loadToDoList() method contained code that did not involve the GUI, but rather
included reading the JSON File, and converting it into a filled ToDoList. I moved this code into the JsonReader class to
again improve cohesion. In particular, see the removal of lines 45-56 in my GUI class on my first commit March 31.

Task 3 - Coupling Issues: 
 
A: Note that I have two classes in Model, ToDoList and TaskItem. ToDoList has an arraylist of TaskItems and in the field
counters to notate the number of tasks of a certain status. TaskItem a field for the status of the task.  
Originally, to change the status of a task, there were three methods used in the ToDoList class which were 
task...ToDo(), where ... was either Completed, InProgress, or NotStarted. These methods called another method within 
the TaskItem Class, which actually changed the status (by modifying the TaskItem field). It would the increment the 
counter. I realized this was coupling because changing the method in the ToDoList resulted in the method within the other
class breaking also. I thus separated these and rewrote the method to change status as changeTaskStatus(String newStatus).
I wrote a new method just to add and subtract from the counter. Finally, I modified the tests which called the old methods.
