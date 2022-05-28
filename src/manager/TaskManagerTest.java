package manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tasks.*;

import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class TaskManagerTest <T extends TaskManager> {

    T taskManager;

    TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    @Test
    void createNewTask() {
        Task task = new Task("Task 1", "Task 1 description");
        task = taskManager.createNewTask(task);

        assertNotNull(task, "Добавить задачу не получилось");
        assertNotNull(task.getId(), "id задачи не должен быть null после добавления в менеджер");

        List<Task> listTasks = taskManager.getListOfAllTasks();

        assertNotNull(listTasks, "Список задач не должен быть null");
        assertEquals(1, listTasks.size(), "Реальное количество задач не совпадает с ожидаемым");
        assertEquals(task, listTasks.get(0), "Произошла путаница. Задачи должны совпадать!");
    }

    @Test
    void addEpic() {
        Epic epic = new Epic("Epic 1", "Epic 1 description");
        epic = taskManager.createNewEpic (epic);

        assertNotNull(epic, "Добавить задачу не получилось");
        assertNotNull(epic.getId(), "id задачи не должен быть null после добавления в менеджер");

        List<Epic> listEpics = taskManager.getListOfAllEpics();

        assertNotNull(listEpics, "Список задач не должен быть null");
        assertEquals(1, listEpics.size(), "Реальное количество задач не совпадает с ожидаемым");
        assertEquals(epic, listEpics.get(0), "Произошла путаница. Задачи должны совпадать!");

    }

    @Test
    void addSubTask() {
        Subtask subtask = new Subtask("SubTask 1", "description");
        subtask = taskManager.createNewSubtask(subtask);

        assertNotNull(subtask, "Добавить задачу не получилось");
        assertNotNull(subtask.getId(), "id задачи не должен быть null после добавления в менеджер");

        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();

        assertNotNull(listSubtasks, "Список задач не должен быть null");
        assertEquals(1, listSubtasks.size(), "Реальное количество задач не совпадает с ожидаемым");
        assertEquals(subtask, listSubtasks.get(0), "Произошла путаница. Задачи должны совпадать!");
    }

    @Test
    void updateTask() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.createNewTask(task1);
        Task task2 = taskManager.getTaskById(task1.getId());

        assertNotNull(task2, "Задачу по id не удалось найти");

        task2.setName("Task 1 newName");
        taskManager.updateTask(task2);

        Task task3 = taskManager.getTaskById(task2.getId());

        assertEquals(task2.getName(), task3.getName(), "Обновление работает неправильно");
    }

    @Test
    void getTaskById() {
        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = taskManager.createNewTask(task1);

        // просмотр
        Task task2 = taskManager.getTaskById(task1.getId());
        assertNotNull(task2, "Невозможно получить задачу по id");
        assertEquals(task1, task2, "Задачи не равны");

        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
        assertEquals(1, listHistory.size(), "Неверное количество задач в истории просмотров");
        assertEquals(task2, listHistory.get(0), "Задачи не равны");
    }

    @Test
    void getSubtaskById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask 1 description");
        subtask1 = taskManager.createNewSubtask(subtask1);

        // просмотр
        Subtask subtask2 = taskManager.getSubtaskById(subtask1.getId());
        assertNotNull(subtask2, "Невозможно получить задачу по id");
        assertEquals(subtask1, subtask2, "Задачи не равны");

        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
        assertEquals(1, listHistory.size(), "Неверное количество задач в истории просмотров");
        assertEquals(subtask2, listHistory.get(0), "Задачи не равны");
    }

    @Test
    void getEpicById() {
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = taskManager.createNewEpic(epic1);

        // просмотр
        Epic epic2 = taskManager.getEpicById(epic1.getId());
        assertNotNull(epic2, "Невозможно получить задачу по id");
        assertEquals(epic1, epic2, "Задачи не равны");

        // проверка добавления в историю просмотров
        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
        assertEquals(1, listHistory.size(), "Неверное количество задач в истории просмотров");
        assertEquals(epic2, listHistory.get(0), "Задачи не равны");
    }

    @Test
    void removeTaskById() {
        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.createNewTask(task1);
        taskManager.getTaskById(task1.getId()); // добавление в историю

        taskManager.removeTaskById(task1.getId());
        assertEquals(0, taskManager.getListOfAllTasks().size(), "Задача удалить не получилось");

        List<Task> listHistory = taskManager.history();
        assertFalse(listHistory.contains(task1), "Из истории просмотров задачу удалить не получилось");
    }

    @Test
    void removeSubtaskById() {
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.createNewSubtask(subtask1);
        taskManager.getSubtaskById(subtask1.getId()); // добавление в историю

        taskManager.removeSubtaskById(subtask1.getId());
        assertEquals(0, taskManager.getListOfAllSubtasks().size(), "Задача удалить не получилось");

        List<Task> listHistory = taskManager.history();
        assertFalse(listHistory.contains(subtask1), "Из истории просмотров задачу удалить не получилось");
    }

    @Test
    void removeEpic() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.createNewEpic(epic1);
        taskManager.getEpicById(epic1.getId()); // добавление в историю

        taskManager.removeEpicById(epic1.getId());
        assertEquals(0, taskManager.getListOfAllEpics().size(), "Задача удалить не получилось");

        List<Task> listHistory = taskManager.history();
        assertFalse(listHistory.contains(epic1), "Из истории просмотров задачу удалить не получилось");
    }

    @Test
    void getListOfAllTasks() {
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertNotNull(listTasks, "Список  задач не должен быть null");
        assertEquals(0, listTasks.size(), "Неверное количество задач");

        Task task1 = new Task("Task 1", "description");
        task1 = taskManager.createNewTask(task1);
        listTasks = taskManager.getListOfAllTasks();
        assertEquals(1, listTasks.size(), "Неверное количество задач");
    }

    @Test
    void getListOfAllSubtasks() {
        List<Subtask> listSubtask = taskManager.getListOfAllSubtasks();
        assertNotNull(listSubtask, "Список  задач не должен быть null");
        assertEquals(0, listSubtask.size(), "Неверное количество задач");

        Subtask subtask1 = new Subtask("Subtask 1", "description");
        subtask1 = taskManager.createNewSubtask(subtask1);
        listSubtask = taskManager.getListOfAllSubtasks();
        assertEquals(1, listSubtask.size(), "Неверное количество задач");
    }

    @Test
    void getListOfAllEpics() {
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertNotNull(listEpics, "Список  задач не должен быть null");
        assertEquals(0, listEpics.size(), "Неверное количество задач");

        Epic epic1 = new Epic("Task 1", "description");
        epic1 = taskManager.createNewEpic(epic1);
        listEpics = taskManager.getListOfAllEpics();
        assertEquals(1, listEpics.size(), "Неверное количество задач");
    }

    @Test
    void getPrioritizedTasks() {
        Instant moment = Instant.now();

        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        Task task3 = new Task("Task 3", "description");

        task2.setDuration(1800); // полчаса
        task1.setDuration(300);  // 5 минут
        task1.setStartTime(moment.plusSeconds(1801));

        task3.setStartTime(moment.minusSeconds(1800));
        task3.setDuration(3600);  // час

        taskManager.createNewTask(task1);
        taskManager.createNewTask(task3);
        taskManager.createNewTask(task2);

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

    @Test
    void clearAllTasks() {
        taskManager.createNewTask(new Task("Task 1", "Task 1 description"));
        taskManager.createNewTask(new Task("Task 2", "Task 2 description"));
        taskManager.createNewTask(new Task("Task 3", "Task 3 description"));
        assertEquals(3, taskManager.getListOfAllTasks().size(), "Неверное количество задач");

        taskManager.clearAllTasks();
        List<Task> listTasks = taskManager.getListOfAllTasks();
        assertNotNull(listTasks, "Список задач не должен быть null");
        assertEquals(0, listTasks.size(), "Задачи не удалились");
    }

    @Test
    void clearAllSubtasks() {
        taskManager.createNewSubtask(new Subtask("Subtask 1", "Subtask 1 description"));
        taskManager.createNewSubtask(new Subtask("Subtask 2", "Subtask 2 description"));
        taskManager.createNewSubtask(new Subtask("Subtask 3", "Subtask 3 description"));
        assertEquals(3, taskManager.getListOfAllSubtasks().size(), "Неверное количество задач");

        taskManager.clearAllSubtasks();
        List<Subtask> listSubtasks = taskManager.getListOfAllSubtasks();
        assertNotNull(listSubtasks, "Список задач не должен быть null");
        assertEquals(0, listSubtasks.size(), "Задачи не удалились");
    }

    @Test
    void clearAllEpics() {
        taskManager.createNewEpic(new Epic("Epic 1", "Epic 1 description"));
        taskManager.createNewEpic(new Epic("Epic 2", "Epic 2 description"));
        taskManager.createNewEpic(new Epic("Epic 3", "Epic 3 description"));
        assertEquals(3, taskManager.getListOfAllEpics().size(), "Неверное количество задач");

        taskManager.clearAllEpics();
        List<Epic> listEpics = taskManager.getListOfAllEpics();
        assertNotNull(listEpics, "Список задач не должен быть null");
        assertEquals(0, listEpics.size(), "Задачи не удалились");
    }

    @Test
    void getSubtaskListOfEpic() {
        Epic epic1 = new Epic("Epic 1", "description");
        epic1 = taskManager.createNewEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "description");
        Subtask subtask2 = new Subtask("Subtask 2", "description");
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        subtask1 = taskManager.createNewSubtask(subtask1);
        subtask2 = taskManager.createNewSubtask(subtask2);

        List<Subtask> listSubtasks = taskManager.getSubtaskListOfEpic(epic1);
        assertEquals(2, listSubtasks.size(), "Количество подзадач в большой задаче неверное");
        assertTrue(listSubtasks.contains(subtask1), "Не найдена поздазача большой задачи");
        assertTrue(listSubtasks.contains(subtask2), "Не найдена поздазача большой задачи");
    }

    @Test
    void history() {
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        task1 = taskManager.createNewTask(task1);
        task2 = taskManager.createNewTask(task2);

        taskManager.getTaskById(task1.getId()); // добавление в историю
        taskManager.getTaskById(task2.getId());

        List<Task> listHistory = taskManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
        assertEquals(2, listHistory.size(), "Количество задач в истории просмотров неверное");
        assertTrue(listHistory.contains(task1), "Задачи в истории нет");
        assertTrue(listHistory.contains(task2), "Задачи в истории нет");

        taskManager.getTaskById(task1.getId());

        //проверяем, добавилились ли дубли в историю
        assertEquals(2, listHistory.size(), "Количество задач в истории просмотров неверное");

        //задачи должны удаляться и из истории после удаления из менеджера
        taskManager.removeTaskById(task1.getId());
        taskManager.removeTaskById(task2.getId());
        listHistory = taskManager.history();
        assertEquals(0, listHistory.size(), "Количество задач в истории просмотров неверное");
    }

}