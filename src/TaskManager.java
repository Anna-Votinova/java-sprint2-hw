import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    private Integer idCounter = 1;

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map <Integer, Subtask> subtasks = new HashMap<>();
    private final Map <Integer, Epic> epics = new HashMap<>();

    private void generateId(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter++);
        } else {
            System.out.println("Ошибка! Передана задача с непустым id");
        }

    }

    Task createNewTask(Task task) {
        generateId(task);
        tasks.put(task.getId(), task);
        return task;
    }

    Subtask createNewSubtask (Subtask subtask) {
        generateId(subtask);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    Epic createNewEpic (Epic epic) {
        generateId(epic);
        epics.put(epic.getId(), epic);
        return epic;
    }

     void updateTask(Task task) {
        if (task.getId() == null) {
            System.out.println("Ошибка! Передана задача с пустым id");
            return;
        }
        if (!tasks.containsKey(task.getId())) {
            System.out.println("Ошибка! Передана несуществующая задача");
            return;
        }
        if (task.getTypeOfTask().equals("task")) {
            tasks.put(task.getId(), task);
        } else if (task.getTypeOfTask().equals("subtask")) {
            subtasks.put(task.getId(), (Subtask) task);
        } else if (task.getTypeOfTask().equals("epic")) {
            epics.put(task.getId(), (Epic) task);
        }
    }

    Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    void clearAllTasks () {
        tasks.clear();
    }

    void clearAllSubtasks () {
        subtasks.clear();
    }

    void clearAllEpics () {
        epics.clear();
    }

    void removeTaskById(Integer id) {
        if (!tasks.containsKey(id)) {
            return;
        }
        tasks.remove(id);
    }

    void removeSubtaskById(Integer id) {
        if (!subtasks.containsKey(id)) {
            return;
        }
        subtasks.remove(id);
    }

    void removeEpicById(Integer id) {
        epics.remove(id);

        List<Integer> toRemove = new ArrayList<>();
        for(Subtask subtask : subtasks.values()) {
            if (subtask.getEpic().getId().equals(id)) {
                toRemove.add(subtask.getId());
            }
        }
        for(Integer i : toRemove)
            subtasks.remove(i);
    }

    List<Task> getListOfAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    List<Subtask> getListOfAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    List<Epic> getListOfAllEpics() {
        return new ArrayList<>(epics.values());
    }

    List<Subtask> getSubtaskListOfEpic (Epic epic) {
       List<Subtask> list = new ArrayList<>();
       for(Subtask subtask : subtasks.values()) {
           if(subtask.getEpic().equals(epic)) {
               list.add(subtask);
           }
       }
       return list;
    }
}