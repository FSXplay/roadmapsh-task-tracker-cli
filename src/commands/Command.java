package commands;

import service.TaskManager;

public interface Command {
    void execute(String[] args, TaskManager taskManager);
    void printUsage();
}
