package com.taskproject.manager;

import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ManagerCondition {

    private Map<Long, Task> tasks;
    private Map<Long, Epic> epics;
    private Map<Long, Subtask> subtasks;
    private Collection<Task> prioritizedTasks;
    private List<Task> listHistory;

    public ManagerCondition() {

    }

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Map<Long, Task> tasks) {
        this.tasks = tasks;
    }

    public Map<Long, Epic> getEpics() {
        return epics;
    }

    public void setEpics(Map<Long, Epic> epics) {
        this.epics = epics;
    }

    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Map<Long, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public Collection<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void setPrioritizedTasks(Collection<Task> prioritizedTasks) {
        this.prioritizedTasks = prioritizedTasks;
    }

    public List<Task> getListHistory() {
        return listHistory;
    }

    public void setListHistory(List<Task> listHistory) {
        this.listHistory = listHistory;
    }
}
