package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        subtask.setEpic(this);
    }

    @Override
    public void updateStatus() {
        boolean areSubtasksInProgress = false;
        boolean areNewSubtasks = false;
        boolean areDoneSubtasks = false;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == Status.NEW) {
                areNewSubtasks = true;
            }
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                areSubtasksInProgress = true;
                if (getStatus() == Status.NEW) {
                    super.updateStatus();
                }
                break;
            }
            if (subtask.getStatus() == Status.DONE){
                areDoneSubtasks = true;
            }
        }
        if (areDoneSubtasks && !areNewSubtasks && !areSubtasksInProgress) {
            super.updateStatus();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", subtasks=" + subtasks;
    }

    @Override
    public Type getTypeOfTask() {
        return Type.EPIC;
    }
}