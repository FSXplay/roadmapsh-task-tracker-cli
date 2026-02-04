import model.TaskManager;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        TaskManager taskManager = new TaskManager();
        String command = args[0];

        switch (command) {
            case "add":
                taskManager.addTask(args[1]);
                break;
            case "update":

                break;
            case "delete":
                break;
            case "mark-in-progress":
                break;
            case "mark-done":
                break;
            case "list":
                break;
            case "list done":
                break;
            case "list todo":
                break;
            case "list in-progress":
                break;

            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private static void printUsage() {
        System.out.println("""
                Usage:
                  task-cli add "description"
                  task-cli update <id> "new description"
                  task-cli delete <id>
                  task-cli mark-in-progress <id>
                  task-cli mark-done <id>
                  task-cli list
                  task-cli list todo|done|in-progress
                """);
    }
}
