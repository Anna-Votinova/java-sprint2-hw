package manager;

import сlasses.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private Long idCounter = 1L;

    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map <Long, Subtask> subtasks = new HashMap<>();
    private final Map <Long, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();


    InMemoryTaskManager() {

    }

    private void generateId(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter++);
        } else {
            System.out.println("Ошибка! Передана задача с непустым id");
        }
    }
    @Override
    public Task createNewTask(Task task) {
        generateId(task);
        tasks.put(task.getId(), task);
        return task;
    }
    @Override
    public Subtask createNewSubtask (Subtask subtask) {
        generateId(subtask);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }
    @Override
    public Epic createNewEpic (Epic epic) {
        generateId(epic);
        epics.put(epic.getId(), epic);
        return epic;
    }
    @Override
    public void updateTask(Task task) {
        if (task.getId() == null) {
            System.out.println("Ошибка! Передана задача с пустым id");
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
    @Override
    public Task getTaskById(Long id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;

    }

    @Override
    public Subtask getSubtaskById(Long id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(Long id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public void clearAllTasks () {
        tasks.clear();
    }
    @Override
    public void clearAllSubtasks () {
        subtasks.clear();
    }
    @Override
    public void clearAllEpics () {
        epics.clear();
    }
    @Override
    public void removeTaskById(Long id) {
        if (!tasks.containsKey(id)) {
            return;
        }
        tasks.remove(id);
    }
    @Override
    public void removeSubtaskById(Long id) {
        if (!subtasks.containsKey(id)) {
            return;
        }
        subtasks.remove(id);
    }
    @Override
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
    @Override
    public List<Task> getListOfAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    @Override
    public List<Subtask> getListOfAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    @Override
    public List<Epic> getListOfAllEpics() {
        return new ArrayList<>(epics.values());
    }
    @Override
    public List<Subtask> getSubtaskListOfEpic (Epic epic) {
        List<Subtask> list = new ArrayList<>();
        for(Subtask subtask : subtasks.values()) {
            if(subtask.getEpic().equals(epic)) {
                list.add(subtask);
            }
        }
        return list;
    }

    @Override
    public List<Task> history() {
        System.out.println("""

                История просмотров:
                """);
        return historyManager.getHistory();
    }

}