package com.taskproject.manager;

import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.io.*;
import java.util.List;
import java.util.Scanner;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private File fileToSave;
    private boolean saveToFile = false;

    public FileBackedTasksManager(File fileToSave) {
        this.fileToSave = fileToSave;
    }

    public static FileBackedTasksManager loadFromFile(File file, boolean useFile) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        if (useFile) {
        try {
            Long maximId = 0L;
            Scanner scanner = new Scanner(new FileInputStream(file), "UTF-8");
            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                if (string.trim().isEmpty()) {
                    string = scanner.nextLine();
                    List<Long> list = Utilities.fromString(string);
                    for (Long id : list) {
                        Task task = manager.getAnyTaskById(id);
                        manager.addNewViewToHistoryWithoutSavingToFiles(task);
                    }
                } else {
                    Task task = Utilities.getTaskFromString(string);
                    switch (task.getTypeOfTask()) {
                        case "TASK":
                            manager.addNewTask(task);
                            break;
                        case "EPIC":
                            manager.addNewEpic((Epic) task);
                            break;
                        case "SUBTASK":
                            manager.addNewSubtask((Subtask) task);
                            Long id = Utilities.getEpicId(string);
                            if (id != null) {
                                Epic epic = manager.getEpicById(id);
                                if (epic != null) {
                                    epic.addSubtask((Subtask) task);
                                }
                            }
                    }
                    if (task.getId() > maximId) {
                        maximId = task.getId();
                    }
                }
            }
            manager.setIdCounter(maximId + 1);
            scanner.close();
        } catch (FileNotFoundException ex) {
            throw new ManagerSaveException("Ошибка! Не удалось прочитать файл с историей.");
        }

        manager.saveToFile = true;
    }
        return manager;
    }

    protected void save() {
        if (saveToFile) {
            try {
                PrintWriter printWriter = new PrintWriter(fileToSave);
                try {
                    printWriter = new PrintWriter(fileToSave, "UTF-8");
                } catch (UnsupportedEncodingException exx) {
                    printWriter = new PrintWriter(fileToSave);
                }
                List<Task> allTasks = getListOfAllTasks();
                for (Task task : allTasks) {
                    printWriter.println(Utilities.toString(task));
                }
                List<Epic> allEpics = getListOfAllEpics();
                for (Epic epic : allEpics) {
                    printWriter.println(Utilities.toString(epic));
                }
                List<Subtask> allSubtasks = getListOfAllSubtasks();
                for (Subtask subtask : allSubtasks) {
                    printWriter.println(Utilities.toString(subtask));
                }

                List<Task> history = history();
                if (!history.isEmpty()) {
                    printWriter.println();
                    printWriter.println(Utilities.toString(getHistoryManager()));
                }
                printWriter.close();
            } catch (FileNotFoundException ex) {
                throw new ManagerSaveException("Ошибка! Не удалось сохранить состояние в файл.");
            }
        }
    }

    @Override
    public Task addNewTask(Task task) {
        task = super.addNewTask(task);
        save();
        return task;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        subtask = super.addNewSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        epic = super.addNewEpic(epic);
        save();
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeTaskById(Long id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeSubtaskById(Long id) {
        super.removeSubtaskById(id);
    }

    @Override
    public void removeEpicById(Long id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    protected void addNewViewToHistory(Task task) {
        super.addNewViewToHistory(task);
        save();
    }

    protected void addNewViewToHistoryWithoutSavingToFiles(Task task) {
        super.addNewViewToHistory(task);
    }

    public void setSaveToFile(boolean saveToFile) {
        this.saveToFile = saveToFile;
    }

}
