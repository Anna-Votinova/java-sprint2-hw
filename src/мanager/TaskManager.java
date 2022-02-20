package мanager;

import сlasses.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Long idCounter = 1L;

    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map <Long, Subtask> subtasks = new HashMap<>();
    private final Map <Long, Epic> epics = new HashMap<>();

    private void generateId(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter++);
        } else {
            System.out.println("Ошибка! Передана задача с непустым id");
        }
    }

    public Task createNewTask(Task task) {
        generateId(task);
        tasks.put(task.getId(), task);
        return task;
    }

    public Subtask createNewSubtask (Subtask subtask) {
        generateId(subtask);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    public Epic createNewEpic (Epic epic) {
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

    public Task getTaskById(Long id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(Long id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(Long id) {
        return epics.get(id);
    }

    public void clearAllTasks () {
        tasks.clear();
    }

    public void clearAllSubtasks () {
        subtasks.clear();
    }

    public void clearAllEpics () {
        epics.clear();
    }

    public void removeTaskById(Long id) {
        if (!tasks.containsKey(id)) {
            return;
        }
        tasks.remove(id);
    }

    public void removeSubtaskById(Long id) {
        if (!subtasks.containsKey(id)) {
            return;
        }
        subtasks.remove(id);
    }

    public void removeEpicById(Long id) {
        epics.remove(id);

        List<Long> toRemove = new ArrayList<>();
        for(Subtask subtask : subtasks.values()) {
            if (subtask.getEpic().getId().equals(id)) {
                toRemove.add(subtask.getId());
            }
        }
        for(Long i : toRemove)
            subtasks.remove(i);
    }

    public List<Task> getListOfAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Subtask> getListOfAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Epic> getListOfAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getSubtaskListOfEpic (Epic epic) {
       List<Subtask> list = new ArrayList<>();
       for(Subtask subtask : subtasks.values()) {
           if(subtask.getEpic().equals(epic)) {
               list.add(subtask);
           }
       }
       return list;
    }
}