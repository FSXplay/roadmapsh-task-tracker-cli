package commands;

import service.TaskManager;

public class AddCommand implements Command {
    @Override
    public void execute(String[] args, TaskManager taskManager) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        taskManager.addTask(args[1]);
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: task-cli add \"description\"");
    }
}
