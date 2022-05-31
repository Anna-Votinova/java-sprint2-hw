package com.taskproject.tasks;

public class Subtask extends Task {

    private Epic epic;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Epic getEpic() {
        return epic;
    }

    protected void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        if (epic != null) {
            epic.setStatus(status);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", epic=" + getEpic().getId();
    }

    @Override
    public String getTypeOfTask() {
        return "SUBTASK";
    }
}
