package manager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefaultFileManager(java.io.File file, boolean useFile) {
        FileBackedTasksManager manager = FileBackedTasksManager.loadFromFile(file, useFile);
        return manager;
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}
