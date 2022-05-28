package tasks;

import java.time.Instant;
import java.util.Objects;

public class Task implements Comparable {
    private Long id;
    private String name;
    private String description;
    private Status status;

    private long duration = 0;  // продолжительность в секундах
    private Instant startTime = Instant.now();

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return startTime.plusSeconds(duration);
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

    public Type getTypeOfTask() {
        return Type.TASK;
    }

    @Override
    public int compareTo(Object o) {
        return getStartTime().compareTo(((Task)o).getStartTime());
    }
}