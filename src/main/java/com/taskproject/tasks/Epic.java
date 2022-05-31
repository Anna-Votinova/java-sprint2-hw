package com.taskproject.tasks;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.taskproject.manager.Utilities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private transient List<Subtask> subtasks;
    private String jsonSubtasks;

    private static Gson gson = new Gson();

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
        jsonSubtasks = makeJsonOfListSubtasks(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        subtask.setEpic(this);
        jsonSubtasks = makeJsonOfListSubtasks(subtasks);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        jsonSubtasks = makeJsonOfListSubtasks(subtasks);
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

    static String makeJsonOfListSubtasks(List<Subtask> subtasks) {
        String s = "[";
        for(int i = 0; i < subtasks.size(); ++i) {
            Subtask sub = subtasks.get(i);
            s += makeJsonOfSubtask(sub);
            if (i < subtasks.size() - 1) {
                s += ",";
            }
        }
        s += "]";
        return s;
        /*
        [{"id":1,"name":"Задача № 1","description":"Сделать уборку","status":"NEW","duration":0,"startTime":{"seconds":1653995595,"nanos":166583100}},
        {"id":2,"name":"Задача 2","description":"Помыть посуду","status":"NEW","duration":0,"startTime":{"seconds":1653995595,"nanos":166583100}}]*/
    }

    private static String makeJsonOfSubtask(Subtask subtask) {
        return "{\"id\":" + subtask.getId() + ",\"name\":\"" + subtask.getName() + "\",\"description\":\"" + subtask.getDescription() +
                "\",\"status\":\"" + subtask.getStatus() + "\",\"duration\":" + subtask.getDuration() + ",\"startTime\":{\"seconds\":" +
                subtask.getStartTime().getEpochSecond() + ",\"nanos\":" + subtask.getStartTime().getNano() + "}}";
    }

    public void restoreSubtasks() {
        subtasks = gson.fromJson(jsonSubtasks, new TypeToken<List<Subtask>>(){}.getType());
        for(Subtask sub : subtasks) {
            sub.setEpic(this);
        }
    }

    public String getJsonSubtasks() {
        return jsonSubtasks;
    }

    public void setJsonSubtasks(String jsonSubtasks) {
        this.jsonSubtasks = jsonSubtasks;
    }
}