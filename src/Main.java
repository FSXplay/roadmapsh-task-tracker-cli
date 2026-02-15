import java.util.Map;
import java.util.HashMap;

import commands.Command;
import commands.AddCommand;
import commands.DeleteCommand;
import commands.ListCommand;
import commands.MarkCommand;
import commands.UpdateCommand;

import service.TaskManager;

public class Main {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("add", new AddCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("update", new UpdateCommand());
        commands.put("mark-todo", new MarkCommand());
        commands.put("mark-in-progress", new MarkCommand());
        commands.put("mark-done", new MarkCommand());
        commands.put("list", new ListCommand());
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        TaskManager taskManager = new TaskManager();
        String commandName = args[0];

        Command command = commands.get(commandName);

        if (command == null) {
            System.out.println("Unknown command: " + commandName);
            printUsage();
            return;
        }

        command.execute(args, taskManager);
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
