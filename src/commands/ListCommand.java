package commands;

import model.Task.Status;
import service.TaskManager;

public class ListCommand implements Command {
    @Override
    public void execute(String[] args, TaskManager taskManager) {
        if (args.length == 1) {
            // List all tasks
            taskManager.listTasks(null);
        } else if (args.length == 2) {
            // List tasks by status
            String statusInput = args[1];
            switch (statusInput) {
                case "todo":
                    taskManager.listTasks(Status.TODO);
                    break;
                case "in-progress":
                    taskManager.listTasks(Status.IN_PROGRESS);
                    break;
                case "done":
                    taskManager.listTasks(Status.DONE);
                    break;
            
                default:
                    printUsage();
            }
        } else {
            printUsage();
        }
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: task-cli list [todo|done|in-progress]");
    }
}
