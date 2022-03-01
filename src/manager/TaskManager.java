package manager;

import tasks.*;
import java.util.List;

public interface TaskManager {

    Task createNewTask(Task task);

    Subtask createNewSubtask (Subtask subtask);

    Epic createNewEpic (Epic epic);

    void updateTask(Task task);

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

    List<Subtask> getSubtaskListOfEpic (Epic epic);

    List<Task> history();
}