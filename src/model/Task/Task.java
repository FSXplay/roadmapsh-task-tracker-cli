package model.Task;
import java.time.LocalDateTime;

public class Task {
    public int id;
    public String description;
    public Status status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public Task(int _id, String _description, Status _status, LocalDateTime _createdAt, LocalDateTime _updateAt) {
        this.id = _id;
        this.description = _description;
        this.status = _status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
