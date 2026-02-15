package commands;

import model.Task.Status;
import service.TaskManager;

public class MarkCommand extends IdCommand {
    @Override
    public void execute(String[] args, TaskManager taskManager) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        int id = parseId(args[1]);
        if (id == -1)
            return;

        String statusString = args[0].substring(5);
        switch (statusString) {
            case "todo":
                taskManager.markTask(id, Status.TODO);
                break;
            case "in-progress":
                taskManager.markTask(id, Status.IN_PROGRESS);
                break;
            case "done":
                taskManager.markTask(id, Status.DONE);
                break;
        
            default:
                printUsage();
        }
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: task-cli mark-[todo|in-progress|done] <id>");
    }
}
