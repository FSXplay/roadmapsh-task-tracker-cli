package model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    public int id;
    public String description;
    public Status status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public Task(int _id, String _description) {
        this.id = _id;
        this.description = _description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        // Creating a formatter for a cleaner look (e.g., 2026-02-04 23:15)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return String.format(
                "Task #%d [%s]\n" +
                        "   Description: %s\n" +
                        "   Created:     %s\n" +
                        "   Last Updated: %s",
                id,
                status,
                description,
                createdAt.format(fmt),
                updatedAt.format(fmt));
    }
}
