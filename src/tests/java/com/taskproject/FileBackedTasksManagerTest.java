package com.taskproject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.taskproject.manager.Managers;
import com.taskproject.manager.TaskManager;

import java.io.File;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;
import com.taskproject.TaskManagerTest;

import java.util.List;

class FileBackedTasksManagerTest extends TaskManagerTest {

    Task task1, task2, task3;
    Epic epic1, epic2;
    Subtask subtask1, subtask2;

    String nameMain = "resources/fileToSave.csv";
    String name1 = "resources/file1.csv";
    String name2 = "resources/file2.csv";
    String name3 = "resources/file3.csv";
    String name4 = "resources/file4.csv";
    String name5 = "resources/file5.csv";

    FileBackedTasksManagerTest() {
        super(Managers.getDefaultFileManager(new File("resources/fileToSave.csv"), false));
    }

    // Здесь мы одновременно проверяем сохранение в файл и чтение из файла
    // Если в новый менеджер загрузилось ровно то, что записали перед этим старым менеджером, то оба процесса происходят правильно

    // Сохраняем что-то в файл
    public void saveToFile(String name) {

        taskManager = Managers.getDefaultFileManager(new File(name), true);

        task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.addNewTask(task1);
        task2 = new Task("Task 2", "Task 2 description");
        task2 = taskManager.addNewTask(task2);
        task3 = new Task("Task 3", "Task 3 description");
        task3 = taskManager.addNewTask(task3);

        // Большая задача без подзадач
        epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.addNewEpic(epic1);

        // Большая задача с подзадачами
        epic2 = new Epic("Epic 2", "Epic 2 description");
        epic2 = taskManager.addNewEpic(epic2);
        subtask1 = new Subtask("Subtask 1", "subtask 1 description");
        subtask2 = new Subtask("Subtask 2", "Subtask 2 description");
        epic2.addSubtask(subtask1);
        epic2.addSubtask(subtask2);
        subtask1 = taskManager.addNewSubtask(subtask1);
        subtask2 = taskManager.addNewSubtask(subtask2);

        // создание истории просмотров
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
    }

    @Test
    public void loadFromFile_CountOfTasksWhenEmpty() {
        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(nameMain), true);

        List<Task> listTasks = newFileManager.getListOfAllTasks();
        assertEquals(0, listTasks.size(), "Количество задач неверное");
    }

    @Test
    public void loadFromFile_CountOfTasks() {
        saveToFile(name1);

        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(name1), true);

        List<Task> listTasks = newFileManager.getListOfAllTasks();
        assertEquals(3, listTasks.size(), "Количество задач неверное");
    }

    @Test
    public void loadFromFile_CountOfEpicsWhenEmpty() {
        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(nameMain), true);

        List<Epic> listEpics = newFileManager.getListOfAllEpics();
        assertEquals(0, listEpics.size(), "Количество больших задач неверное");
    }

    @Test
    public void loadFromFile_CountOfEpics() {
        saveToFile(name2);

        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(name2), true);

        List<Epic> listEpics = newFileManager.getListOfAllEpics();
        assertEquals(2, listEpics.size(), "Количество больших задач неверное");
    }

    @Test
    public void loadFromFile_CountOfSubtasksWhenEmpty() {
        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(nameMain), true);

        List<Subtask> listSubTasks = newFileManager.getListOfAllSubtasks();
        assertEquals(0, listSubTasks.size(), "Количество Подзадач неверное");
    }

    @Test
    public void loadFromFile_CountOfSubtasks() {
        saveToFile(name3);

        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(name3), true);

        List<Subtask> listSubTasks = newFileManager.getListOfAllSubtasks();
        assertEquals(2, listSubTasks.size(), "Количество Подзадач неверное");
    }

    @Test
    public void loadFromFile_CountOfViewedTasksInHistoryWhenEmpty() {
        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(nameMain), true);

        List<Task> listHistory = newFileManager.history();
        assertEquals(0, listHistory.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void loadFromFile_CountOfViewedTasksInHistory() {
        saveToFile(name4);

        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(name4), true);

        List<Task> listHistory = newFileManager.history();
        assertEquals(7, listHistory.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    // выборочная проверка порядка задач в истории
    public void loadFromFile_CheckOrderInHistory() {
        saveToFile(name5);

        // Новый менеджер, который должен считать информацию из файла, куда мы предварительно что-то записали
        TaskManager newFileManager = Managers.getDefaultFileManager(new File(name5), true);

        List<Task> listHistory = newFileManager.history();
        assertEquals(task3, listHistory.get(1), "Задачи не совпадают");
    }

}