package com.taskproject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.taskproject.tasks.Task;
import java.util.List;
import com.taskproject.manager.HistoryManager;
import com.taskproject.manager.Managers;
import com.taskproject.manager.TaskManager;

class HistoryManagerTest {

    HistoryManager historyManager;
    TaskManager taskManager;

    Task task1;
    Task task2;
    Task task3;
    Task task4;
    Task task5;

    HistoryManagerTest() {
        historyManager = Managers.getDefaultHistory();
        taskManager = Managers.getDefault();

        task1 = new Task("Task 1", "description");
        task2 = new Task("Task 2", "description");
        task3 = new Task("Task 3", "description");
        task4 = new Task("Task 4", "description");
        task5 = new Task("Task 5", "description");
    }

    public void add(Task task) {
        Task t = taskManager.addNewTask(task);
        historyManager.add(t);
        task.setId(t.getId());
    }

    public void addAllTasks() {
        add(task1);
        add(task2);
        add(task3);
        add(task4);
        add(task5);
    }

    /*
     тесты на метод add
    */

    @Test
    public void historyManagerWithOneTaskAdded_shouldNotBeNull() {
        // Добавляем задачу
        add(task1);
        List<Task> list = historyManager.getHistory();
        assertNotNull(list, "История просмотров не должна быть null");
    }

    @Test
    public void historyManagerWithOneTaskAdded_shouldHaveOneTaskInHistory() {
        // Добавляем задачу
        add(task1);
        List<Task> list = historyManager.getHistory();
        assertEquals(1, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void historyManagerWithOneTaskAdded_shouldHaveTheSameTaskInHistory() {
        // Добавляем задачу
        task1 = taskManager.addNewTask(task1);
        historyManager.add(task1);
        List<Task> list = historyManager.getHistory();
        assertEquals(task1, list.get(0), "Задачи разные");
    }

    @Test
    public void historyManagerWithTwoTasksAdded_shouldHaveTwoTasksInHistory() {
        // Добавляем 2 задачи
        add(task1);
        add(task2);
        List<Task> list = historyManager.getHistory();
        assertEquals(2, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void historyManagerWithTwoTasksAdded_shouldHaveTheSameSecondTaskInHistory() {
        // Добавляем 2 задачи
        add(task1);
        add(task2);
        List<Task> list = historyManager.getHistory();
        assertEquals(task2, list.get(1), "Задачи разные");
    }

    /*
     тесты на метод remove
    */

    @Test
    public void historyManagerWithFiveTasksAdded_shouldHaveFiveTasksInHistory() {
        addAllTasks();
        List<Task> list = historyManager.getHistory();
        assertEquals(5, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void removeFromMiddle_Count() {
        addAllTasks();
        historyManager.remove(task3.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(4, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void removeFromMiddle_Task1ShouldBeFirst() {
        addAllTasks();
        historyManager.remove(task3.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task1, list.get(0), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromMiddle_Task2ShouldBeSecond() {
        addAllTasks();
        historyManager.remove(task3.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task2, list.get(1), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromMiddle_Task4ShouldBeThird() {
        addAllTasks();
        historyManager.remove(task3.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task4, list.get(2), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromMiddle_Task5ShouldBeFourth() {
        addAllTasks();
        historyManager.remove(task3.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task5, list.get(3), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromEnd_Count() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void removeFromEnd_Task1ShouldBeFirst() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task1, list.get(0), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromEnd_Task2ShouldBeSecond() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task2, list.get(1), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromEnd_Task4ShouldBeThird() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task4, list.get(2), "Порядок задач после удаления неверный");
    }

    public void removeFromBeginning_Count() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        historyManager.remove(task1.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(2, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void removeFromBeginning_Task2ShouldBeFirst() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        historyManager.remove(task1.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task2, list.get(0), "Порядок задач после удаления неверный");
    }

    @Test
    public void removeFromEnd_Task4ShouldBeSecond() {
        addAllTasks();
        historyManager.remove(task3.getId());
        historyManager.remove(task5.getId());
        historyManager.remove(task1.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(task4, list.get(1), "Порядок задач после удаления неверный");
    }

    /*
     тесты на метод getHistory
    */

    @Test
    public void history_shouldNotBeNull() {
        //в истории пока пусто
        List<Task> list = historyManager.getHistory();
        assertNotNull(list, "История просмотров не должна быть null");
    }

    @Test
    public void history_shouldBeEmpty() {
        //в истории пока пусто
        List<Task> list = historyManager.getHistory();
        assertEquals(0, list.size(), "История просмотров должна быть пуста");
    }

    // проверяем, чтобы не было дублей в истории при повторном просмотре
    @Test
    public void history_shouldNotBeDoubles() {
        add(task1);
        add(task2);
        add(task3);
        historyManager.add(task1);   // повтор

        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "Количество задач в истории просмотров неверное");
    }

    @Test
    public void history_Task2ShouldBeFirst() {
        add(task1);
        add(task2);
        add(task3);
        historyManager.add(task1);   // повтор

        List<Task> list = historyManager.getHistory();
        assertEquals(task2, list.get(0), "Порядок задач при дублировании неверный");
    }

    @Test
    public void history_Task2ShouldBeSecond() {
        add(task1);
        add(task2);
        add(task3);
        historyManager.add(task1);   // повтор

        List<Task> list = historyManager.getHistory();
        assertEquals(task3, list.get(1), "Порядок задач при дублировании неверный");
    }

    @Test
    public void history_Task1ShouldBeThird() {
        add(task1);
        add(task2);
        add(task3);
        historyManager.add(task1);   // повтор

        List<Task> list = historyManager.getHistory();
        assertEquals(task1, list.get(2), "Порядок задач при дублировании неверный");
    }

}