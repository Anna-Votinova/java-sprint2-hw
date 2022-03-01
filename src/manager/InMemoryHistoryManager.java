package manager;

import сlasses.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> listHistory = new ArrayList<>();

    InMemoryHistoryManager() {

    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (listHistory.size() == 10) {
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
