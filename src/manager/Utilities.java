package manager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Type;

public class Utilities {

    static String toString(Task task) {
        return task.getId() + "," +
                task.getTypeOfTask() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getStartTime() + "," +
                task.getDuration() + "," +
                (task.getTypeOfTask() == Type.SUBTASK &&
                        ((Subtask)task).getEpic() != null ? ((Subtask)task).getEpic().getId() : "");

    }

    static Task getTaskFromString(String value) {
        String[] parts = value.split(",");
        Task task;
        if (parts[1].equals("TASK")) {
            task = new Task(parts[2], parts[4]);
            task.setId(Long.parseLong(parts[0]));
            task.setStartTime(Instant.parse(parts[5]));
            task.setDuration(Long.parseLong(parts[6]));
        } else if (parts[1].equals("EPIC")) {
            task = new Epic(parts[2], parts[4]);
            task.setId(Long.parseLong(parts[0]));
        } else {
            task = new Subtask(parts[2], parts[4]);
            task.setId(Long.parseLong(parts[0]));
            task.setStartTime(Instant.parse(parts[5]));
            task.setDuration(Long.parseLong(parts[6]));
        }
        if (parts[3].equals("IN_PROGRESS")) {
            task.updateStatus();
        }
        if (parts[3].equals("DONE")) {
            for(int i = 1; i <= 2; ++i) {
                task.updateStatus();
            }
        }
        return task;
    }

    static Long getEpicId(String value) {
        String[] parts = value.split(",");
        if (parts.length < 8 || parts[7].trim().isEmpty()) {
            return null;
        } else {
            return Long.parseLong(parts[7]);
        }
    }

    static String toString(HistoryManager manager) {
        List<Task> list = manager.getHistory();
        String string = "";
        for(int i = 0; i < list.size(); ++i) {
            string += list.get(i).getId() + (i < list.size() - 1 ? "," : "");
        }
        return string;
    }

    static List<Long> fromString(String value) {
        String[] ids = value.split(",");
        List<Long> list = new ArrayList<>();
        for(String id : ids) {
            list.add(Long.parseLong(id));
        }
        return list;
    }
}
