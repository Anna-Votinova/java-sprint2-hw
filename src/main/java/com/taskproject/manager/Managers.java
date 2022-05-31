package com.taskproject.manager;

import java.net.URI;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefaultFileManager(java.io.File file, boolean useFile) {
        return FileBackedTasksManager.loadFromFile(file, useFile);
    }

    public static TaskManager getDefault(URI url, String API) {
        return HTTPTaskManager.loadFromServer(url, API);
    }

    public static TaskManager getDefault(URI url) {
        return HTTPTaskManager.loadFromServer(url, null);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}

