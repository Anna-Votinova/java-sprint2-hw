package com.taskproject.manager;

import com.google.gson.Gson;
import com.taskproject.kv.KVTaskClient;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.TreeSet;

public class HTTPTaskManager extends FileBackedTasksManager {

    // URL сервера (KVServer), на котором хранится состояние
    protected URI url;
    private KVTaskClient kvTaskClient;

    public HTTPTaskManager(File file) {
        super(file);
    }

    public HTTPTaskManager(URI url) {
        super(null);
        this.url = url;
        try {
            kvTaskClient = new KVTaskClient(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static HTTPTaskManager loadFromServer(URI url, String API) {
        if (API == null) {
            return new HTTPTaskManager(url);
        } else {
            Gson gson = new Gson();
            try {
                KVTaskClient taskClient = new KVTaskClient(url, API);
                String conditionString = taskClient.load(API);
                HTTPTaskManager manager = new HTTPTaskManager(url);
                manager.kvTaskClient = taskClient;

                ManagerCondition condition = gson.fromJson(conditionString, ManagerCondition.class);
                manager.restoreFromManagerCondition(condition);

                return manager;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return new HTTPTaskManager(url);
        }
    }

    @Override
    protected void save() {
        Gson gson = new Gson();
        ManagerCondition condition = formManagerCondition();
        String conditionString = gson.toJson(condition, ManagerCondition.class);
        //System.out.println("conditionString: " + conditionString);
        try {
            kvTaskClient.put(kvTaskClient.getAPI(), conditionString);
        } catch (IOException | InterruptedException ex) {
            throw new ManagerSaveException("Произошла ошибка: " + ex.getLocalizedMessage());
        }
    }

    // формирование объекта ManagerCondition с состоянием сервера на текущий момент
    private ManagerCondition formManagerCondition() {
        ManagerCondition managerCondition = new ManagerCondition();
        managerCondition.setTasks(getTasks());
        managerCondition.setEpics(getEpics());
        managerCondition.setSubtasks(getSubtasks());
        managerCondition.setPrioritizedTasks(getPrioritizedTasks());
        managerCondition.setListHistory(history());
        return managerCondition;
    }

    // восстановление состояния менеджера по объекту ManagerCondition
    private void restoreFromManagerCondition(ManagerCondition managerCondition) {
        this.setTasks(managerCondition.getTasks());
        this.setEpics(managerCondition.getEpics());
        this.setSubtasks(managerCondition.getSubtasks());
        this.setPrioritizedTasks((TreeSet<Task>) managerCondition.getPrioritizedTasks());
        List<Task> listHistory = managerCondition.getListHistory();
        for (Task task : listHistory) {
            this.getHistoryManager().add(task);
        }
    }

}