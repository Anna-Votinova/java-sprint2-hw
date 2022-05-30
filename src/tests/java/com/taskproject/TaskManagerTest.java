package com.taskproject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.taskproject.manager.TaskManager;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class TaskManagerTest <T extends TaskManager> {

    T taskManager;

    TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    /*
    //////////////////  тесты на метод addNewTask
    */

    @Test
    public void addNewTask_addedTaskShouldNotBeNull() {
        Task task = new Task("Task 1", "Task 1 description");
        task = taskManager.addNewTask(task);
        assertNotNull(task, "Добавить задачу не получилось");
    }

    @Test
    public void addNewTask_IdOfAddedTaskShouldNotBeNull() {
        Task task = new Task("Task 1", "Task 1 description");
        task = taskManager.addNewTask(task);
        assertNotNull(task.getId(), "id задачи не должен быть null после добавления в менеджер");
    }

    @Test
    public void addNewTask_listOfAllTasksShouldNotBeNull() {
        Task task = new Task("Task 1", "Task 1 description");
        task = taskManager.addNewTask(task);
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertNotNull(listTasks, "Список задач не должен быть null");
    }

    @Test
    public void addNewTask_listOfAllTasksShouldContainOneTask() {
        Task task = new Task("Task 1", "Task 1 description");
        task = taskManager.addNewTask(task);
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertEquals(1, listTasks.size(), "Реальное количество задач не совпадает с ожидаемым");
    }

    @Test
    public void addNewTask_listOfAllTasksShouldContainAddedTask() {
        Task task = new Task("Task 1", "Task 1 description");
        task = taskManager.addNewTask(task);
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertEquals(task, listTasks.get(0), "Произошла путаница. Задачи должны совпадать!");
    }

    /*
    //////////////////  тесты на метод addNewEpic
    */

    @Test
    public void addNewEpic_addedEpicShouldNotBeNull() {
        Epic epic = new Epic("Epic 1", "Epic 1 description");
        epic = taskManager.addNewEpic(epic);
        assertNotNull(epic, "Добавить большую задачу не получилось");
    }

    @Test
    public void addNewEpic_IdOfAddedEpicShouldNotBeNull() {
        Epic epic = new Epic("Epic 1", "Epic 1 description");
        epic = taskManager.addNewEpic(epic);
        assertNotNull(epic.getId(), "id большой задачи не должен быть null после добавления в менеджер");
    }

    @Test
    public void addNewEpic_listOfAllEpicsShouldNotBeNull() {
        Epic epic = new Epic("Epic 1", "Epic 1 description");
        epic = taskManager.addNewEpic(epic);
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertNotNull(listEpics, "Список больших задач не должен быть null");
    }

    @Test
    public void addNewEpic_listOfAllEpicsShouldContainOneEpic() {
        Epic epic = new Epic("Epic 1", "Epic 1 description");
        epic = taskManager.addNewEpic(epic);
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertEquals(1, listEpics.size(), "Реальное количество больших задач не совпадает с ожидаемым");
    }

    @Test
    public void addNewEpic_listOfAllEpicsShouldContainAddedEpic() {
        Epic epic = new Epic("Epic 1", "Epic 1 description");
        epic = taskManager.addNewEpic(epic);
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertEquals(epic, listEpics.get(0), "Произошла путаница. Большие задачи должны совпадать!");
    }

    /*
    //////////////////  тесты на метод addNewSubtask
    */

    @Test
    public void addNewSubtask_addedSubtaskShouldNotBeNull() {
        Subtask subtask = new Subtask("SubTask 1", "description");
        subtask = taskManager.addNewSubtask(subtask);
        assertNotNull(subtask, "Добавить подзадачу не получилось");
    }

    @Test
    public void addNewSubtask_IdOfAddedSubtaskShouldNotBeNull() {
        Subtask subtask = new Subtask("SubTask 1", "description");
        subtask = taskManager.addNewSubtask(subtask);
        assertNotNull(subtask.getId(), "id подзадачи не должен быть null после добавления в менеджер");
    }

    @Test
    public void addNewSubtask_listOfAllSubtasksShouldNotBeNull() {
        Subtask subtask = new Subtask("SubTask 1", "description");
        subtask = taskManager.addNewSubtask(subtask);
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertNotNull(listSubtasks, "Список подзадач не должен быть null");
    }

    @Test
    public void addNewSubtask_listOfAllSubtasksShouldContainOneSubtask() {
        Subtask subtask = new Subtask("SubTask 1", "description");
        subtask = taskManager.addNewSubtask(subtask);
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertEquals(1, listSubtasks.size(), "Реальное количество задач не совпадает с ожидаемым");
    }

    @Test
    public void addNewSubtask_listOfAllSubtasksShouldContainAddedSubtask() {
        Subtask subtask = new Subtask("SubTask 1", "description");
        subtask = taskManager.addNewSubtask(subtask);
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertEquals(subtask, listSubtasks.get(0), "Произошла путаница. Подзадачи должны совпадать!");
    }

    /*
    //////////////////  тесты на метод updateTask
    */

    @Test
    public void updateTask_TaskShouldBeFound() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.addNewTask(task1);
        task1.setName("Task 1 newName");
        taskManager.updateTask(task1);
        Task task = taskManager.getTaskById(task1.getId());
        assertNotNull(task, "После обновления задачу по id не удалось найти");
    }

    @Test
    public void updateTask_TaskShouldBeUpdated() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.addNewTask(task1);
        task1.setName("Task 1 newName");
        taskManager.updateTask(task1);
        Task task = taskManager.getTaskById(task1.getId());
        assertEquals(task.getName(), task1.getName(), "Обновление задач работает неправильно");
    }

    /*
    //////////////////  тесты на метод updateEpic
    */

    @Test
    public void updateEpic_EpicShouldBeFound() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);
        epic1.setName("Epic 1 newName");
        taskManager.updateEpic(epic1);
        Epic epic = taskManager.getEpicById(epic1.getId());
        assertNotNull(epic, "После обновления большую задачу по id не удалось найти");
    }

    @Test
    public void updateEpic_EpicShouldBeUpdated() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);
        epic1.setName("Epic 1 newName");
        taskManager.updateEpic(epic1);
        Epic epic = taskManager.getEpicById(epic1.getId());
        assertEquals(epic.getName(), epic1.getName(), "Обновление больших задач работает неправильно");
    }

    /*
    //////////////////  тесты на метод updateSubtask
    */

    @Test
    public void updateSubtask_SubtaskShouldBeFound() {
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        subtask1.setName("Subtask 1 newName");
        taskManager.updateSubtask(subtask1);
        Subtask subtask = taskManager.getSubtaskById(subtask1.getId());
        assertNotNull(subtask, "После обновления подзадачу по id не удалось найти");
    }

    @Test
    public void updateSubtask_SubtaskShouldBeUpdated() {
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        subtask1.setName("Subtask 1 newName");
        taskManager.updateSubtask(subtask1);
        Subtask subtask = taskManager.getSubtaskById(subtask1.getId());
        assertEquals(subtask.getName(), subtask1.getName(), "Обновление подзадач работает неправильно");
    }

    /*
    //////////////////  тесты на метод getTaskById
    */

    @Test
    public void getTaskById_TaskShouldNotBeNull() {
        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.addNewTask(task1);
        // просмотр
        Task task2 = taskManager.getTaskById(task1.getId());
        assertNotNull(task2, "Невозможно получить задачу по id");
    }

    @Test
    public void getTaskById_TaskShouldBeTheSame() {
        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.addNewTask(task1);
        // просмотр
        Task task2 = taskManager.getTaskById(task1.getId());
        assertEquals(task1, task2, "Задачи не равны");
    }

    @Test
    public void getTaskById_HistoryShouldNotBeNull() {
        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.addNewTask(task1);
        // просмотр
        taskManager.getTaskById(task1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
    }

    @Test
    public void getTaskById_HistoryShouldContainOneTask() {
        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.addNewTask(task1);
        // просмотр
        taskManager.getTaskById(task1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertEquals(1, listHistory.size(), "Неверное количество задач в истории просмотров");
    }

    @Test
    public void getTaskById_TaskShouldBeAddedToHistory() {
        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.addNewTask(task1);
        // просмотр
        Task task2 = taskManager.getTaskById(task1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertEquals(task2, listHistory.get(0), "Задачи не равны");
    }

    /*
    //////////////////  тесты на метод getSubtaskById
    */

    @Test
    public void getSubtaskById_SubtaskShouldNotBeNull() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        // просмотр
        Subtask subtask2 = taskManager.getSubtaskById(subtask1.getId());
        assertNotNull(subtask2, "Невозможно получить подзадачу по id");
    }

    @Test
    public void getSubtaskById_SubtaskShouldBeTheSame() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        // просмотр
        Subtask subtask2 = taskManager.getSubtaskById(subtask1.getId());
        assertEquals(subtask1, subtask2, "Подзадачи не равны");
    }

    @Test
    public void getSubtaskById_HistoryShouldNotBeNull() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        // просмотр
        taskManager.getSubtaskById(subtask1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
    }

    @Test
    public void getSubtaskById_HistoryShouldContainOneSubtask() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        // просмотр
        taskManager.getSubtaskById(subtask1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertEquals(1, listHistory.size(), "Неверное количество подзадач в истории просмотров");
    }

    @Test
    public void getSubtaskById_SubtaskShouldBeAddedToHistory() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        // просмотр
        Subtask subtask2 = taskManager.getSubtaskById(subtask1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertEquals(subtask2, listHistory.get(0), "Подзадачи не равны");
    }

    /*
    //////////////////  тесты на метод getEpicById
    */

    @Test
    public void getEpicById_EpicShouldNotBeNull() {
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.addNewEpic(epic1);
        // просмотр
        Epic epic2 = taskManager.getEpicById(epic1.getId());
        assertNotNull(epic2, "Невозможно получить большую задачу по id");
    }

    @Test
    public void getEpicById_EpicShouldBeTheSame() {
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.addNewEpic(epic1);
        // просмотр
        Epic epic2 = taskManager.getEpicById(epic1.getId());
        assertEquals(epic1, epic2, "Большие задачи не равны");
    }

    @Test
    public void getEpicById_HistoryShouldNotBeNull() {
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.addNewEpic(epic1);
        // просмотр
        taskManager.getEpicById(epic1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
    }

    @Test
    public void getEpicById_HistoryShouldContainOneEpic() {
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.addNewEpic(epic1);
        // просмотр
        taskManager.getEpicById(epic1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertEquals(1, listHistory.size(), "Неверное количество больших задач в истории просмотров");
    }

    @Test
    public void getEpicById_EpicShouldBeAddedToHistory() {
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.addNewEpic(epic1);
        // просмотр
        Epic epic2 = taskManager.getEpicById(epic1.getId());
        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertEquals(epic2, listHistory.get(0), "Большие задачи не равны");
    }

    /*
    ///////////////////  тесты на метод removeTaskById
    */

    @Test
    public void removeTaskById_TaskShouldBeRemoved() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.addNewTask(task1);
        taskManager.removeTaskById(task1.getId());
        assertEquals(0, taskManager.getListOfAllTasks().size(), "Задачу удалить не получилось");
    }

    @Test
    public void removeTaskById_TaskShouldBeRemovedFromHistory() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.addNewTask(task1);

// добавление в историю
        taskManager.getTaskById(task1.getId());

        taskManager.removeTaskById(task1.getId());

        List<Task> listHistory = taskManager.history();
        assertFalse(listHistory.contains(task1), "Из истории просмотров задачу удалить не получилось");
    }

    /*
    //////////////////  тесты на метод removeSubtaskById
    */

    @Test
    public void removeSubtaskById_SubtaskShouldBeRemoved() {
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        taskManager.removeSubtaskById(subtask1.getId());
        assertEquals(0, taskManager.getListOfAllSubtasks().size(), "Подзадачу удалить не получилось");
    }

    @Test
    public void removeSubtaskById_SubtaskShouldBeRemovedFromHistory() {
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.addNewSubtask(subtask1);

// добавление в историю
        taskManager.getSubtaskById(subtask1.getId());

        taskManager.removeSubtaskById(subtask1.getId());

        List<Task> listHistory = taskManager.history();
        assertFalse(listHistory.contains(subtask1), "Из истории просмотров подзадачу удалить не получилось");
    }

    /*
    //////////////////  тесты на метод removeEpicById
    */

    @Test
    public void removeEpicById_EpicShouldBeRemovedFrom() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);
        taskManager.removeEpicById(epic1.getId());
        assertEquals(0, taskManager.getListOfAllEpics().size(), "Большую задачу удалить не получилось");
    }

    @Test
    public void removeEpicById_EpicShouldBeRemovedFromHistory() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);

// добавление в историю
        taskManager.getEpicById(epic1.getId());

        taskManager.removeEpicById(epic1.getId());

        List<Task> listHistory = taskManager.history();
        assertFalse(listHistory.contains(epic1), "Из истории просмотров большую задачу удалить не получилось");
    }

    /*
    //////////////////  тесты на метод getListOfAllTasks
    */

    @Test
    public void getListOfAllTasks_ListShouldNotBeNull() {
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertNotNull(listTasks, "Список задач не должен быть null");
    }

    @Test
    public void getListOfAllTasks_ListShouldBeEmpty() {
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertEquals(0, listTasks.size(), "Неверное количество задач");
    }

    @Test
    public void getListOfAllTasks_ListShouldContainRightQuantityOfTasks() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.addNewTask(task1);
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertEquals(1, listTasks.size(), "Неверное количество задач");
    }

    /*
    //////////////////  тесты на метод getListOfAllSubtasks
    */

    @Test
    public void getListOfAllSubtasks_ListShouldNotBeNull() {
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertNotNull(listSubtasks, "Список подзадач не должен быть null");
    }

    @Test
    public void getListOfAllSubtasks_ListShouldBeEmpty() {
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertEquals(0, listSubtasks.size(), "Неверное количество подзадач");
    }

    @Test
    public void getListOfAllSubtasks_ListShouldContainRightQuantityOfSubtasks() {
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.addNewSubtask(subtask1);
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertEquals(1, listSubtasks.size(), "Неверное количество подзадач");
    }

    /*
    //////////////////  тесты на метод getListOfAllEpics
    */

    @Test
    public void getListOfAllEpics_ListShouldNotBeNull() {
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertNotNull(listEpics, "Список больших задач не должен быть null");
    }

    @Test
    public void getListOfAllEpics_ListShouldBeEmpty() {
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertEquals(0, listEpics.size(), "Неверное количество больших задач");
    }

    @Test
    public void getListOfAllEpics_ListShouldContainRightQuantityOfEpics() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertEquals(1, listEpics.size(), "Неверное количество больших задач");
    }

    /*
    //////////////////  тесты на метод clearAllTasks
    */

    @Test
    public void clearAllTasks_ListShouldNotBeNull() {
        taskManager.addNewTask(new Task("Task 1", "Task 1 description"));
        taskManager.addNewTask(new Task("Task 2", "Task 2 description"));
        taskManager.addNewTask(new Task("Task 3", "Task 3 description"));

        taskManager.clearAllTasks();
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertNotNull(listTasks, "Список задач не должен быть null");
    }

    @Test
    public void clearAllTasks_ListShouldBeEmpty() {
        taskManager.addNewTask(new Task("Task 1", "Task 1 description"));
        taskManager.addNewTask(new Task("Task 2", "Task 2 description"));
        taskManager.addNewTask(new Task("Task 3", "Task 3 description"));

        taskManager.clearAllTasks();
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertEquals(0, listTasks.size(), "Задачи не удалились");
    }

    /*
    //////////////////  тесты на метод clearAllSubtasks
    */

    @Test
    public void clearAllSubtasks_ListShouldNotBeNull() {
        taskManager.addNewSubtask(new Subtask("Subtask 1", "Subtask 1 description"));
        taskManager.addNewSubtask(new Subtask("Subtask 2", "Subtask 2 description"));
        taskManager.addNewSubtask(new Subtask("Subtask 3", "Subtask 3 description"));

        taskManager.clearAllSubtasks();
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertNotNull(listSubtasks, "Список задач не должен быть null");
    }

    @Test
    public void clearAllSubtasks_ListShouldBeEmpty() {
        taskManager.addNewSubtask(new Subtask("Subtask 1", "Subtask 1 description"));
        taskManager.addNewSubtask(new Subtask("Subtask 2", "Subtask 2 description"));
        taskManager.addNewSubtask(new Subtask("Subtask 3", "Subtask 3 description"));

        taskManager.clearAllSubtasks();
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertEquals(0, listSubtasks.size(), "Подзадачи не удалились");
    }

    /*
    //////////////////  тесты на метод clearAllEpics
    */

    @Test
    public void clearAllEpics_ListShouldNotBeNull() {
        taskManager.addNewEpic(new Epic("Epic 1", "Epic 1 description"));
        taskManager.addNewEpic(new Epic("Epic 2", "Epic 2 description"));
        taskManager.addNewEpic(new Epic("Epic 3", "Epic 3 description"));

        taskManager.clearAllEpics();
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertNotNull(listEpics, "Список задач не должен быть null");
    }

    @Test
    public void clearAllEpics_ListShouldBeEmpty() {
        taskManager.addNewEpic(new Epic("Epic 1", "Epic 1 description"));
        taskManager.addNewEpic(new Epic("Epic 2", "Epic 2 description"));
        taskManager.addNewEpic(new Epic("Epic 3", "Epic 3 description"));

        taskManager.clearAllEpics();
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertEquals(0, listEpics.size(), "Большие задачи не удалились");
    }

    /*
    //////////////////  тесты на метод getSubtaskListOfEpic
    */

    @Test
    public void getSubtaskListOfEpic_CountOfSubtasks() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        Subtask subtask2 = new Subtask("Subtask 2", "description");
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        List<Subtask> listSubtasks = taskManager.getSubtaskListOfEpic(epic1);
        assertEquals(2, listSubtasks.size(), "Количество подзадач в большой задаче неверное");
    }

    @Test
    public void getSubtaskListOfEpic_EpicShouldContainAddedSubtask() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        epic1.addSubtask(subtask1);
        subtask1 = taskManager.addNewSubtask(subtask1);

        List<Subtask> listSubtasks = taskManager.getSubtaskListOfEpic(epic1);
        assertTrue(listSubtasks.contains(subtask1), "Не найдена подзадача большой задачи");
    }

    /*
    //////////////////  тесты на метод history
    */

    @Test
    public void history_HistoryShouldNotBeNull() {
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        task1 = taskManager.addNewTask(task1);
        task2 = taskManager.addNewTask(task2);

        // добавление в историю
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());

        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
    }

    @Test
    public void history_CountOfTasksInHistory() {
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        task1 = taskManager.addNewTask(task1);
        task2 = taskManager.addNewTask(task2);

        // добавление в историю
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());

        List<Task> listHistory = taskManager.history();
        assertEquals(2, listHistory.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void history_HistoryShouldContainViewedTask() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.addNewTask(task1);

        // добавление в историю
        taskManager.getTaskById(task1.getId());

        List<Task> listHistory = taskManager.history();
        assertTrue(listHistory.contains(task1), "Задачи в истории нет");
    }

    @Test
    public void history_CountOfTasksInHistoryAfterDoubles() {
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        task1 = taskManager.addNewTask(task1);
        task2 = taskManager.addNewTask(task2);

// добавление в историю
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());

        List<Task> listHistory = taskManager.history();

        taskManager.getTaskById(task1.getId());

        //проверяем, добавились ли дубли в историю
        assertEquals(2, listHistory.size(), "Количество задач в истории просмотров неверное (случай дублей)");
    }

    @Test
    public void history_TasksShouldBeDeletedFromHistoryWhenDeletedFromManager() {
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        task1 = taskManager.addNewTask(task1);
        task2 = taskManager.addNewTask(task2);

        taskManager.getTaskById(task1.getId()); // добавление в историю
        taskManager.getTaskById(task2.getId());

        //задачи должны удаляться и из истории после удаления из менеджера
        taskManager.removeTaskById(task1.getId());
        taskManager.removeTaskById(task2.getId());
        List<Task> listHistory = taskManager.history();
        assertEquals(0, listHistory.size(), "Задачи не удалились из истории");
    }

    /*
    //////////////////  тесты на метод getPrioritizedTasks
    */

    @Test
    public void getPrioritizedTasks() {
        Instant moment = Instant.now();

        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        Task task3 = new Task("Task 3", "description");

        task2.setDuration(1800); // полчаса
        task1.setDuration(300);  // 5 минут
        task1.setStartTime(moment.plusSeconds(1801));

        task3.setStartTime(moment.minusSeconds(1800));
        task3.setDuration(3600);  // час

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task3);
        taskManager.addNewTask(task2);

        Collection<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        Iterator<Task> iter = prioritizedTasks.iterator();
        Task curr;
        Task next;
        if (iter.hasNext()) {
            curr = iter.next();

            while (iter.hasNext()) {
                next = iter.next();
                assertTrue(next.compareTo(curr) >= 0, "Задачи идут в неправильном порядке");
                curr = next;
            }
        }

    }

}