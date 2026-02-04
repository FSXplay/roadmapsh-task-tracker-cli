package model.Task;
import java.time.LocalDateTime;

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
}
