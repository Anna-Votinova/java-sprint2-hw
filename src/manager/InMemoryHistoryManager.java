package manager;

import tasks.Task;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int SIZE = 10;
    private final List<Task> listHistory = new LinkedList<>();


    InMemoryHistoryManager() {

    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (listHistory.size() == SIZE) {
                listHistory.remove(0);
            }
            listHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return listHistory;
    }
}
