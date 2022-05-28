package manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tasks.Task;
import java.util.List;

class HistoryManagerTest {
    
    HistoryManager historyManager = Managers.getDefaultHistory();
    TaskManager taskManager = Managers.getDefault();
    
    @Test
    void add() {
        // история пока пустая
        Task task = new Task("Task 1", "description");
        List<Task> list = historyManager.getHistory();
        assertNotNull(list, "История просмотров не должна быть null");
        assertEquals(0, list.size(), "Количество задач в истории просмотров неверное");

        task = taskManager.createNewTask(task);
        historyManager.add(task);
        list = historyManager.getHistory();
        assertEquals(1, list.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task, list.get(0), "Задачи разные");

        Task task2 = new Task("Task 2", "description");
        taskManager.createNewTask(task2);
        historyManager.add(task2);
        list = historyManager.getHistory();
        assertEquals(2, list.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task2, list.get(1), "Задачи разные");
    }

    @Test
    void remove() {
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        Task task3 = new Task("Task 3", "description");
        Task task4 = new Task("Task 4", "description");
        Task task5 = new Task("Task 5", "description");
        task1 = taskManager.createNewTask(task1);
        historyManager.add(task1);
        task2 = taskManager.createNewTask(task2);
        historyManager.add(task2);
        task3 = taskManager.createNewTask(task3);
        historyManager.add(task3);
        task4 = taskManager.createNewTask(task4);
        historyManager.add(task4);
        task5 = taskManager.createNewTask(task5);
        historyManager.add(task5);

        List<Task> list = historyManager.getHistory();
        assertEquals(5, list.size(), "Количество задач в истории просмотров неверное");

        historyManager.remove(task3.getId());  // удаление из середины истории
        list = historyManager.getHistory();
        assertEquals(4, list.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task1, list.get(0), "Порядок задач после удаления неверный");
        assertEquals(task2, list.get(1), "Порядок задач после удаления неверный");
        assertEquals(task4, list.get(2), "Порядок задач после удаления неверный");
        assertEquals(task5, list.get(3), "Порядок задач после удаления неверный");

        historyManager.remove(task5.getId());  // удаление с конца истории
        list = historyManager.getHistory();
        assertEquals(3, list.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task1, list.get(0), "Порядок задач после удаления неверный");
        assertEquals(task2, list.get(1), "Порядок задач после удаления неверный");
        assertEquals(task4, list.get(2), "Порядок задач после удаления неверный");

        historyManager.remove(task1.getId());  // удаление с начала истории
        list = historyManager.getHistory();
        assertEquals(2, list.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task2, list.get(0), "Порядок задач после удаления неверный");
        assertEquals(task4, list.get(1), "Порядок задач после удаления неверныйя");
    }

    @Test
    void getHistory() {

        //в истории пока пусто
        List<Task> list = historyManager.getHistory();
        assertNotNull(list, "История просмотров не должна быть null");
        assertEquals(0, list.size(), "История просмотров должна быть пуста");

        //проверка поведение в случае дублей
        Task task1 = new Task("Task 1", "description");
        Task task2 = new Task("Task 2", "description");
        Task task3 = new Task("Task 3", "description");

        task1 = taskManager.createNewTask(task1);
        task2 = taskManager.createNewTask(task2);
        task3 = taskManager.createNewTask(task3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task1);

        list = historyManager.getHistory();
        assertEquals(3, list.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task2, list.get(0), "Порядок задач при дублировании неверный");
        assertEquals(task3, list.get(1), "Порядок задач при дублировании неверный");
        assertEquals(task1, list.get(2), "Порядок задач при дублировании неверный");
    }
}