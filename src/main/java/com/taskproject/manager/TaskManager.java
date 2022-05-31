package com.taskproject.manager;

import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.util.Collection;
import java.util.List;

public interface TaskManager {

    Task addNewTask(Task task);

    Subtask addNewSubtask(Subtask subtask);

    Epic addNewEpic(Epic epic);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    Task getTaskById(Long id);

    Subtask getSubtaskById(Long id);

    Epic getEpicById(Long id);

    void clearAllTasks();

    void clearAllSubtasks();

    void clearAllEpics();

    void removeTaskById(Long id);

    void removeSubtaskById(Long id);

    void removeEpicById(Long id);

    List<Task> getListOfAllTasks();

    List<Subtask> getListOfAllSubtasks();

    List<Epic> getListOfAllEpics();

    List<Subtask> getSubtaskListOfEpic(Epic epic);

    List<Task> history();

    Collection<Task> getPrioritizedTasks();

}