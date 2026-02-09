package service;

import model.Task.Task;
import model.Task.Status;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private static final String FILE_NAME = "tasks.json";
    private static int idCount = 1;
    private Map<Integer, Task> taskMap = loadTaskFromJSONFile(FILE_NAME);

    // Below are primary commands used by users
    public void addTask(String description) {
        taskMap.put(idCount, new Task(idCount, description, Optional.empty(), Optional.empty(), Optional.empty()));
        System.out.println("Task added successfully (ID: " + idCount++ + ")");
    }

    public void updateTask(int id, String newDescription) {
        Task selectedTask = taskMap.get(id);
        selectedTask.description = newDescription;
        selectedTask.updateTime();
    }

    public void deleteTask(int id) {
        taskMap.remove(id);
    }

    public void markInProgress(int id) {
        Task selectedTask = taskMap.get(id);
        selectedTask.status = Status.IN_PROGRESS;
        selectedTask.updateTime();
    }

    public void markDone(int id) {
        Task selectedTask = taskMap.get(id);
        selectedTask.status = Status.DONE;
        selectedTask.updateTime();
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

    // Internally handle saving and loading the tasks
    private static Map<Integer, Task> loadTaskFromJSONFile(String filePath) {
        String json = JSONParser.readJSONAsString(filePath);
        json = json.substring(1, json.length() - 2).trim(); // "Unbox" the JSON by removing the outmost brackets
        Map<Integer, Task> taskMap = new HashMap<>();

        if (json.isEmpty())
            return taskMap;

        String[] taskEntries = JSONParser.splitJsonEntries(json);
        int maxId = 0;
        
        for (String entry : taskEntries) {
            int colonIndex = entry.indexOf(":");
            String idStr = entry.substring(0, colonIndex).replace("\"", "").trim();
            int id = Integer.parseInt(idStr);

            String taskData = entry.substring(colonIndex + 1).trim();
            taskData = taskData.substring(1, taskData.length() - 1).trim();

            Task task = parseTaskFields(id, taskData);
            taskMap.put(id, task);

            if (id > maxId) maxId = id;
        }

        idCount = maxId + 1; // Ensure that there will be no duplicate IDs

        return taskMap;
    }

    private static Task parseTaskFields(int id, String taskData) {
        String[] fields = JSONParser.splitJsonEntries(taskData);

        String description = "";
        Status status = Status.TODO;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String field : fields) {
            String[] kv = field.split(":", 2);
            String key = kv[0].replace("\"", "").trim();
            String val = kv[1].replace("\"", "").trim();

            switch (key) {
                case "description" -> description = val;
                case "status" -> {
                    // Convert "in-progress" -> IN_PROGRESS
                    String enumName = val.toUpperCase().replace("-", "_");
                    status = Status.valueOf(enumName);
                }
                case "createdAt" -> createdAt = java.time.LocalDateTime.parse(val);
                case "updatedAt" -> updatedAt = java.time.LocalDateTime.parse(val);
            }
        }

        return new Task(id, description, Optional.ofNullable(status), Optional.ofNullable(createdAt), Optional.ofNullable(updatedAt));
    }
}
