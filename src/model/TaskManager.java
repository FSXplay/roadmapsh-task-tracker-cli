package model;

import model.Task.Task;
import model.Task.Status;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private static int idCount = 1;
    private Map<Integer, Task> taskMap = new HashMap<>();

    public void addTask(String description) {
        taskMap.put(idCount, new Task(idCount, description));
        idCount++;
    }

    public void updateTask(int id, String newDescription) {
        Task selectedTask = taskMap.get(id);
        selectedTask.description = newDescription;   
    }

    public void deleteTask(int id) {
        taskMap.remove(id);
    }

    public void markInProgress(int id) {
        Task selectedTask = taskMap.get(id);
        selectedTask.status = Status.IN_PROGRESS;
    }

    public void markDone(int id) {
        Task selectedTask = taskMap.get(id);
        selectedTask.status = Status.DONE;
    }

    public void listAll() {
        for (Task task : taskMap.values()) {
            System.out.println(task);
        }
    }

    public void listByStatus(Status status) {
        for (Task task : taskMap.values()) {
            if (task.status == status) {
                System.out.println(task);
            }
        }
    }
}
