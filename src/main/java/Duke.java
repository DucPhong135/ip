import java.util.Scanner;

public class Duke {

    //taskList variable for level-2 onwards
    private static Task[] taskList = new Task[100];
    //task count
    private static int taskCount = 0;


    //echo function for level-1
    public static void echo(String message) {
        System.out.println(message);
    }


    //add function for level-2
    public static void add(String task) {
        Task newTask = new Task(task);
        taskList[taskCount] = newTask;
        taskCount++;
        System.out.println("added: " + task);
    }


    //list function for level-2
    public static void list() {
        for (int i = 0; i < taskCount; i++) {
            System.out.print((i + 1) + ". ");
            System.out.println(taskList[i].toString());
        }
    }


    //mark function for level-3
    public void mark(int index) throws DukeException {
        try {
            checkMarkUnmarkInput(index);
            System.out.println("Nice! I've marked this task as done:");
            taskList[index - 1].markAsDone();
        } catch (DukeException e) {
            e.displayMessage();
        }

    }


    //unmark function for level-3
    public void unmark(int index) throws DukeException {
        try {
            checkMarkUnmarkInput(index);
            System.out.println("Ok, I've marked this task as not done yet:");
            taskList[index - 1].markAsNotDone();
        } catch (DukeException e) {
            e.displayMessage();
        }
    }


    //add toDo task for level-4
    public static void addToDo(String[] inputComponent) throws DukeException {
        String description = "";

        for (int i = 1; i < inputComponent.length; i++) {
            description += inputComponent[i];
            description += " ";
        }

        try {
            checkTodoInput(description);
            taskList[taskCount] = new ToDo(description.trim());
            System.out.println("Got it. I've added this task:");
            System.out.println(taskList[taskCount].toString());
            taskCount++;
            System.out.println("Now you have " + taskCount + " tasks in the list");
        } catch (DukeException e) {
            e.displayMessage();
        }

    }


    //add deadline task for level-4
    public static void addDeadline(String[] inputComponent) throws DukeException {
        String description = "";
        String by = "";
        int state = 0;//transition from "description" to "by" string


        for (int i = 1; i < inputComponent.length; i++) {
            if (inputComponent[i].equals("/by")) {
                state += 1;
            } else {
                if (state == 1) {
                    by += inputComponent[i];
                    by += " ";
                } else {
                    description += inputComponent[i];
                    description += " ";
                }
            }
        }

        try {
            checkDeadlineInput(description, state);
            taskList[taskCount] = new Deadline(description.trim(), by.trim());
            System.out.println("Got it. I've added this task:");
            System.out.println(taskList[taskCount].toString());
            taskCount++;
            System.out.println("Now you have " + taskCount + " tasks in the list");
        } catch (DukeException e) {
            e.displayMessage();
        }

    }


    //add event task for level-4
    public static void addEvent(String[] inputComponent) {
        String description = "";
        String from = "";
        String to = "";
        int state = 0;//transition from "description" to "from" to "to" string


        for (int i = 1; i < inputComponent.length; i++) {
            if (inputComponent[i].equals("/from")) {
                state = 1;
            } else if (inputComponent[i].equals("/to")) {
                state = 2;
            } else {
                if (state == 1) {
                    from += inputComponent[i];
                    from += " ";
                } else if (state == 2) {
                    to += inputComponent[i];
                    to += " ";
                } else {
                    description += inputComponent[i];
                    description += " ";
                }
            }
        }

        try {
            checkEventInput(description, state);
            taskList[taskCount] = new Event(description.trim(), from.trim(), to.trim());
            System.out.println("Got it. I've added this task:");
            System.out.println(taskList[taskCount].toString());
            taskCount++;
            System.out.println("Now you have " + taskCount + " tasks in the list");
        } catch (DukeException e) {
            e.displayMessage();
        }
    }

    //exception handler for todo level-5
    public static void checkTodoInput(String input) throws DukeException {
        if (input == null || input.isEmpty()) {
            // Throw a custom exception for empty input
            throw new DukeException("Description for a todo cannot be empty");
        }
    }

    //exception handler for deadline level-5
    public static void checkDeadlineInput(String input, int state) throws DukeException {
        if (input == null || input.isEmpty()) {
            // Throw a custom exception for empty input
            throw new DukeException("Description for a deadline cannot be empty");
        } else if (state == 0) {
            throw new DukeException("There is no date for a deadline");
        } else if (state > 1) {
            throw new DukeException("Too many /by statement");
        }
    }


    //exception handler for event level-5
    public static void checkEventInput(String input, int state) throws DukeException {
        if (input == null || input.isEmpty()) {
            // Throw a custom exception for empty input
            throw new DukeException("Description for an event cannot be empty");
        } else if (state == 0) {
            throw new DukeException("There is no start and end for this event");
        } else if (state == 1) {
            throw new DukeException("There is no end for this event");
        }
    }


    //exception handler for mark and unmark level-5
    public static void checkMarkUnmarkInput(int index) throws DukeException {
        if (index < 0 || index > taskCount) {
            throw new DukeException("You have input an invalid index");
        }
    }

    //exception handler for general input level-5
    public static void checkGeneralInput() throws DukeException {
        throw new DukeException("Sorry I cannot understand that");
    }

    //main function to execute the chatbot
    public void execute() throws DukeException {
        System.out.println("Hello I'm Lambo");
        System.out.println("What can I do for you?");
        Scanner inputReader = new Scanner(System.in);//scanner for receiving input


        //Super loop used for receiving inputs continuously and response back to user
        SuperLoop:
        while (true) {
            String input = inputReader.nextLine();
            String[] inputComponent = input.split(" ");

            //switch case based on the first word of input line
            switch (inputComponent[0]) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    break SuperLoop;
                case "list":
                    list();
                    break;
                case "mark":
                    try {
                        mark(Integer.parseInt(inputComponent[1]));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Warning: You haven't input any number");
                    }
                    break;
                case "unmark":
                    try {
                        unmark(Integer.parseInt(inputComponent[1]));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Warning: You haven't input any number");
                    }
                    break;
                case "todo":
                    addToDo(inputComponent);
                    break;
                case "deadline":
                    addDeadline(inputComponent);
                    break;
                case "event":
                    addEvent(inputComponent);
                    break;
                default:
                    try {
                        checkGeneralInput(); //check for invalid command
                    } catch (DukeException e) {
                        e.displayMessage();
                    }
            }
        }
    }
}
