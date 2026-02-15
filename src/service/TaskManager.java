package service;

import model.Task.Task;
import model.Task.Status;
import java.util.Optional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for managing the lifecycle of tasks.
 * It handles CRUD operations (Create, Read, Update, Delete) and ensures
 * data persistence by synchronizing an in-memory Map with a JSON file.
 */
public class TaskManager {
    /** The default filename for task storage. */
    private static final String FILE_NAME = "tasks.json";
    
    /** Static counter to ensure unique, incremental task IDs across sessions. */
    private static int idCount = 1;
    
    /** In-memory storage of tasks, keyed by their unique ID. */
    private Map<Integer, Task> taskMap = loadTasksFromJSONFile(FILE_NAME);

    /**
     * Creates a new task and saves it to the persistent storage.
     * @param description The text description of the task.
     */
    public void addTask(String description) {
        taskMap.put(idCount, new Task(idCount, description, Optional.empty(), Optional.empty(), Optional.empty()));
        saveTasksToJSONFile(FILE_NAME);
        System.out.println("Task added successfully (ID: " + idCount++ + ")");
    }

    /**
     * Updates the description and timestamp of an existing task.
     * @param id             The ID of the task to update.
     * @param newDescription The new text description.
     */
    public void updateTask(int id, String newDescription) {
        Task selectedTask = taskMap.get(id);
        if (selectedTask != null) {
            selectedTask.description = newDescription;
            selectedTask.updateTime();
            saveTasksToJSONFile(FILE_NAME);
        }
    }

    /**
     * Removes a task from the map and updates the JSON file.
     * @param id The ID of the task to be deleted.
     */
    public void deleteTask(int id) {
        taskMap.remove(id);
        saveTasksToJSONFile(FILE_NAME);
    }

    /**
     * Changes the status of a specific task (e.g., TODO, IN_PROGRESS, DONE).
     * @param id     The ID of the task.
     * @param status The new Status enum value.
     */
    public void markTask(int id, Status status) {
        Task selectedTask = taskMap.get(id);
        if (selectedTask != null) {
            selectedTask.status = status;
            selectedTask.updateTime();
            saveTasksToJSONFile(FILE_NAME);
        }
    }

    /**
     * Prints tasks to the console. Can be filtered by status.
     * @param status The status to filter by; if null, all tasks are listed.
     */
    public void listTasks(Status status) {
        taskMap.values().stream()
                .filter(task -> status == null || task.status == status)
                .forEach(System.out::println);
    }

    /**
     * Parses the JSON file into the taskMap. 
     * It also calculates the next available ID to prevent collisions.
     * @param filePath Path to the JSON storage file.
     * @return A Map of tasks retrieved from the file.
     */
    private Map<Integer, Task> loadTasksFromJSONFile(String filePath) {
        Map<Integer, Task> taskMap = new HashMap<>();
        try {
            String json = JSONParser.readJSONAsString(filePath);
            json = json.substring(1, json.length() - 2).trim(); 

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

                if (id > maxId)
                    maxId = id;
            }

            idCount = maxId + 1; 
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
            System.out.println("Creating an empty tasks.json file...");
            JSONParser.writeJSONFile(filePath, "{}", false);
        }

        return taskMap;
    }

    /**
     * Serializes the current taskMap into a JSON string and writes it to disk.
     * @param filePath Path to the JSON storage file.
     */
    private void saveTasksToJSONFile(String filePath) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        int count = 0;
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            Task task = entry.getValue();
            json.append("\"").append(entry.getKey()).append("\": {");
            json.append("\"description\": \"").append(task.description).append("\", ");
            json.append("\"status\": \"")
                    .append(task.status.toString().toLowerCase().replace("_", "-"))
                    .append("\", ");
            json.append("\"createdAt\": \"").append(task.createdAt).append("\", ");
            json.append("\"updatedAt\": \"").append(task.updatedAt).append("\"");
            json.append("}");

            if (++count < taskMap.size())
                json.append(",");
        }

        json.append("}");
        JSONParser.writeJSONFile(filePath, json.toString(), true);
    }

    /**
     * Helper method to convert a raw JSON object string into a Task object.
     * @param id       The ID associated with the task.
     * @param taskData The raw string containing task fields (description, status, etc.).
     * @return A fully constructed Task object.
     */
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
                    String enumName = val.toUpperCase().replace("-", "_");
                    status = Status.valueOf(enumName);
                }
                case "createdAt" -> createdAt = java.time.LocalDateTime.parse(val);
                case "updatedAt" -> updatedAt = java.time.LocalDateTime.parse(val);
            }
        }

        return new Task(id, description, Optional.ofNullable(status), Optional.ofNullable(createdAt),
                Optional.ofNullable(updatedAt));
    }
}