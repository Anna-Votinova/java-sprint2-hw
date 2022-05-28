package manager;

import tasks.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Long idCounter = 1L;

    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map <Long, Subtask> subtasks = new HashMap<>();
    private final Map <Long, Epic> epics = new HashMap<>();

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
        }/* else {
            System.out.println("Ошибка! Передана задача с непустым id");
        }*/
    }

    protected HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public Task createNewTask(Task task) {
        if (addAndMaybeDelete(task)) {
            generateId(task);
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Subtask createNewSubtask(Subtask subtask) {
        if (addAndMaybeDelete(subtask)) {
            generateId(subtask);
            subtasks.put(subtask.getId(), subtask);
        }
        return subtask;
    }

    @Override
    public Epic createNewEpic(Epic epic) {
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
    public void clearAllTasks () {
        tasks.clear();
    }
    @Override
    public void clearAllSubtasks () {
        subtasks.clear();
    }
    @Override
    public void clearAllEpics () {
        subtasks.clear();
        epics.clear();
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
        prioritizedTasks.remove(subtask);
        subtasks.remove(id);
        historyManager.remove(id);
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
        for(Long i : toRemove) {
            removeSubtaskById(i);
        }
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

}