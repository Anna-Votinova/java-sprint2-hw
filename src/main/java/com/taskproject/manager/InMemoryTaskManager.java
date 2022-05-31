package com.taskproject.manager;

import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Long idCounter = 1L;

    private Map<Long, Task> tasks = new HashMap<>();
    private Map <Long, Subtask> subtasks = new HashMap<>();
    private Map <Long, Epic> epics = new HashMap<>();

    private TreeSet<Task> prioritizedTasks = new TreeSet<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    InMemoryTaskManager() {

    }

    protected void setIdCounter(Long idCounter) {
        this.idCounter = idCounter;
    }

    private void generateId(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter++);
        }
    }

    protected HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public Task addNewTask(Task task) {
        if (addAndMaybeDelete(task)) {
            generateId(task);
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        if (addAndMaybeDelete(subtask)) {
            generateId(subtask);
            subtasks.put(subtask.getId(), subtask);
        }
        return subtask;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        generateId(epic);
        epics.put(epic.getId(), epic);
        return epic;
    }

    private boolean addAndMaybeDelete(Task task) {
        prioritizedTasks.add(task);
        NavigableSet<Task> partFirst = prioritizedTasks.headSet(task, false);
        if (!partFirst.isEmpty()) {
            Task previous = partFirst.last();
            if (previous.getEndTime().isAfter(task.getStartTime())) {
                prioritizedTasks.remove(task);
                return false;
            }
        }

        NavigableSet<Task> partLast = prioritizedTasks.tailSet(task, false);
        if (!partLast.isEmpty()) {
            Task next = partLast.first();
            if (task.getEndTime().isAfter(next.getStartTime())) {
                prioritizedTasks.remove(task);
                return false;
            }

        }

        return true;
    }

    @Override
    public void updateTask(Task task) {
        if (task.getId() == null) {
            System.out.println("Ошибка! Передана большая задача с пустым id");
            return;
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic.getId() == null) {
            System.out.println("Ошибка! Передана задача с пустым id");
            return;
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask.getId() == null) {
            System.out.println("Ошибка! Передана подзадача с пустым id");
            return;
        }
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public Task getTaskById(Long id) {
        Task task = tasks.get(id);
        addNewViewToHistory(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(Long id) {
        Subtask subtask = subtasks.get(id);
        addNewViewToHistory(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(Long id) {
        Epic epic = epics.get(id);
        addNewViewToHistory(epic);
        return epic;
    }

    public Task getAnyTaskById(Long id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            return subtasks.get(id);
        }
    }

    protected void addNewViewToHistory(Task task) {
        historyManager.add(task);
    }

    @Override
    public void clearAllTasks() {
        List<Long> taskIds = new ArrayList<>();
        for(Task task : tasks.values()) {
            taskIds.add(task.getId());
        }
        for(Long id : taskIds) {
            removeTaskById(id);
        }
    }

    @Override
    public void clearAllSubtasks() {
        List<Long> taskIds = new ArrayList<>();
        for(Subtask task : subtasks.values()) {
            taskIds.add(task.getId());
        }
        for(Long id : taskIds) {
            removeSubtaskById(id);
        }
    }

    @Override
    public void clearAllEpics() {
        List<Long> taskIds = new ArrayList<>();
        for(Epic epic : epics.values()) {
            taskIds.add(epic.getId());
        }
        for(Long id : taskIds) {
            removeEpicById(id);
        }
    }

    @Override
    public void removeTaskById(Long id) {
        if (!tasks.containsKey(id)) {
            return;
        }
        Task t = tasks.get(id);
        prioritizedTasks.remove(t);
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtaskById(Long id) {
        if (!subtasks.containsKey(id)) {
            return;
        }
        Subtask subtask = subtasks.get(id);
        Epic epic = subtask.getEpic();
        if (epic != null && epic.getId() != null) {
            epic = epics.get(epic.getId());
            epic.removeSubtask(subtask);
        }
        prioritizedTasks.remove(subtask);
        subtasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(Long id) {
        List<Long> toRemove = new ArrayList<>();
        for(Subtask subtask : subtasks.values()) {
            if (subtask.getEpic().getId().equals(id)) {
                toRemove.add(subtask.getId());
            }
        }
        for(Long i : toRemove) {
            removeSubtaskById(i);
        }
        epics.remove(id);
        historyManager.remove(id);
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
        return historyManager.getHistory();
    }

    public Collection<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    protected Map<Long, Task> getTasks() {
        return tasks;
    }

    protected Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    protected Map<Long, Epic> getEpics() {
        return epics;
    }

    protected void setTasks(Map<Long, Task> tasks) {
        this.tasks = tasks;
    }

    protected void setSubtasks(Map<Long, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    protected void setEpics(Map<Long, Epic> epics) {
        this.epics = epics;
    }

    protected void setPrioritizedTasks(TreeSet<Task> prioritizedTasks) {
        this.prioritizedTasks = prioritizedTasks;
    }
}