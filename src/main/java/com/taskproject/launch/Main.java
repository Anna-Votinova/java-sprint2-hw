package com.taskproject.launch;

import com.taskproject.manager.Managers;
import com.taskproject.manager.TaskManager;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefaultFileManager(new java.io.File("fileToSave.csv"), true);

        Task task1 = new Task("Задача 1", "Сделать уборку");
        Task task2 = new Task("Задача 2", "Помыть посуду");

        Epic epic1 = new Epic ("Большая задача 1", "Отдохнуть на море");
        Subtask subtask1 = new Subtask("Подзадача 1", "Забронировать гостиницу");
        Subtask subtask2 = new Subtask("Подзадача 2", "Купить билеты на самолет");
        Subtask subtask3 = new Subtask("Подзадача 3", "Собрать вещи");

        Epic epic2 = new Epic ("Большая задача 2", "Сделать ремонт");

        System.out.println(manager.getListOfAllTasks() + "\n");
        System.out.println(manager.getListOfAllEpics() + "\n");
        System.out.println(manager.getListOfAllSubtasks() + "\n");

        List<Task> story = manager.history();
        System.out.println("История просмотров:");
        for(Task task : story) {
            System.out.println(task.getId());
        }

        Subtask sub = manager.addNewSubtask(new Subtask("Подзадача 1", "Купить обои"));
        manager.getEpicById(4l).addSubtask(sub);

        manager.removeEpicById(3l);

        System.out.println(manager.getListOfAllTasks() + "\n");
        System.out.println(manager.getListOfAllEpics() + "\n");
        System.out.println(manager.getListOfAllSubtasks() + "\n");

        story = manager.history();
        System.out.println("История просмотров:");
        for(Task task : story) {
            System.out.println(task.getId());
        }
    }
}