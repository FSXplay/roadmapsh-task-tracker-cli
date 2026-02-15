package commands;

import service.TaskManager;

public class UpdateCommand extends IdCommand {
    @Override
    public void execute(String[] args, TaskManager taskManager) {
        if (args.length != 3) {
            printUsage();
            return;
        }

        int id = parseId(args[1]);
        if (id == -1)
            return;

        taskManager.updateTask(id, args[2]);
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: task-cli update <id> \"new description\"");
    }
}
