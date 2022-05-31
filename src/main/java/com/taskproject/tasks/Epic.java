package com.taskproject.tasks;

import java.time.Instant;
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
    public void setStatus(Status status) {
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
                    super.setStatus(Status.IN_PROGRESS);
                }
                break;
            }
            if (subtask.getStatus() == Status.DONE) {
                areDoneSubtasks = true;
            }
        }
        if (areDoneSubtasks && !areNewSubtasks && !areSubtasksInProgress) {
            super.setStatus(Status.DONE);
        }
    }

    //для большой задачи нельзя установить желаемую продолжительность,
    //она должна рассчитываться, поэтому метод остается пустым
    @Override
    public void setDuration(long duration) {

    }

    @Override
    public long getDuration() {
        long d = 0;
        for (Subtask sub : subtasks) {
            d = d + sub.getDuration();
        }
        return d;
    }

    // ничего не надо делать
    // должно рассчитываться
    @Override
    public void setStartTime(Instant startTime) {

    }

    @Override
    public Instant getStartTime() {
        Instant time = null;
        for (Subtask sub : subtasks) {
            if (time == null || sub.getStartTime().isBefore(time)) {
                time = sub.getStartTime();
            }
        }
        // super.getStartTime() - время создания большой задачи
        return subtasks.size() > 0 ? time : super.getStartTime();

    }

    @Override
    public Instant getEndTime() {
        Instant time = null;
        for (Subtask sub : subtasks) {
            if (time == null || sub.getEndTime().isAfter(time)) {
                time = sub.getEndTime();
            }
        }
        return subtasks.size() > 0 ? time : super.getEndTime();
        // super.getEndTime() - время завершения большой задачи
    }

    @Override
    public String toString() {
        return super.toString() + ", subtasks=" + subtasks;
    }

    @Override
    public String getTypeOfTask() {
        return "EPIC";
    }
}