package manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tasks.*;

import java.io.File;
import java.util.List;

class FileBackedTasksManagerTest extends TaskManagerTest {

    FileBackedTasksManagerTest() {
        super(Managers.getDefaultFileManager(new File("resources/fileToSave.csv"), false));
    }

    // Проверка, как всё сохраняется в файл и как потом загружается из файла
    @Test
    void saveToFile() {

        // список задач пуст, история пуста

        TaskManager manager = Managers.getDefaultFileManager(new File("resources/fileToSave.csv"), true);
        
        List<Task> listTasks = manager.getListOfAllTasks();
        assertNotNull(listTasks, "Список задач не должен быть null");
        assertEquals(0, listTasks.size(), "Количество задач неверное");

        List<Epic> listEpics = manager.getListOfAllEpics();
        assertNotNull(listEpics, "Список больших задач не должен быть null");
        assertEquals(0, listEpics.size(), "Количество больших задач неверное");

        List<Subtask> listSubTasks = manager.getListOfAllSubtasks();
        assertNotNull(listSubTasks, "Список подзадач не должен быть null");;
        assertEquals(0, listSubTasks.size(), "Количество подзадач неверно");

        List<Task> listHistory = manager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
        assertEquals(0, listHistory.size(), "В истории просмотров неверное количество задач");

        // список задач не пуст, история не пуста

        Task task1 = new Task("Task 1", "Task 1 description");
        task1 = manager.createNewTask(task1);
        Task task2 = new Task("Task 2", "Task 2 description");
        task2 = manager.createNewTask(task2);
        Task task3 = new Task("Task 3", "Task 3 description");
        task3 = manager.createNewTask(task3);

        // Большая задача без подзадач
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        epic1 = manager.createNewEpic(epic1);

        // Большая задача с подзадачами
        Epic epic2 = new Epic("Epic 2", "Epic 2 description");
        epic2 = manager.createNewEpic(epic2);
        Subtask subtask1 = new Subtask("Subtask 1", "subtask 1 description");
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask 2 description");
        epic2.addSubtask(subtask1);
        epic2.addSubtask(subtask2);
        subtask1 = manager.createNewSubtask(subtask1);
        subtask2 = manager.createNewSubtask(subtask2);

        // создание истории просмотров
        manager.getTaskById(task1.getId());
        manager.getTaskById(task3.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic2.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getSubtaskById(subtask2.getId());


        // Новый менеджер, который должен считать информацию из файла, куда должно было все записаться

        TaskManager newFileManager = Managers.getDefaultFileManager(new File("resources/fileToSave.csv"), true);

        listTasks = newFileManager.getListOfAllTasks();
        assertNotNull(listTasks, "Список задач не должен быть null");
        assertEquals(3, listTasks.size(), "Количество задач неверное");

        listEpics = newFileManager.getListOfAllEpics();
        assertNotNull(listEpics, "Список эпиков не должен быть null");
        assertEquals(2, listEpics.size(), "Количество больших задач неверное");

        listSubTasks = newFileManager.getListOfAllSubtasks();
        assertNotNull(listSubTasks, "Список подзадач не должен быть null");
        assertEquals(2, listSubTasks.size(), "Количество больших задач неверное");

        listHistory = newFileManager.history();
        assertNotNull(listHistory, "История просмотров не должна быть null");
        assertEquals(7, listHistory.size(), "Количество задач в истории просмотров неверное");
        assertEquals(task3, listHistory.get(1), "Задачи не совпадают");
    }

}