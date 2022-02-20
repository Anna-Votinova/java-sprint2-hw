package сlasses;

import java.util.Objects;

public class Task {
    private Long id;
    private String name;
    private String description;
    private Status status;

    public Task (String name, String description) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus() {
        if (status == Status.NEW) {
            status = Status.IN_PROGRESS;
        } else if (status == Status.IN_PROGRESS) {
            status = Status.DONE;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status;
    }

    public String getTypeOfTask (){
        return "task";
    }
}