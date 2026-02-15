package commands;

import service.TaskManager;

public class DeleteCommand extends IdCommand {
    @Override
    public void execute(String[] args, TaskManager taskManager) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        int id = parseId(args[1]);
        if (id == -1)
            return;

        taskManager.deleteTask(id);
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: task-cli delete <id>");
    }
}
