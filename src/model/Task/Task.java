package model.Task;
import java.time.LocalDateTime;

public class Task {
    public int id;
    public String description;
    public Status status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
