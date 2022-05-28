package tasks;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EpicTest {

    @Test
    public void updateStatus() {
        Epic epic = new Epic("Epic 1", "new Epic description");

        Status expectedStatus = Status.NEW;
        Status actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача без подзадач. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);

        Subtask subtask1 = new Subtask("Subtask 1", "new Subtask description");
        Subtask subtask2 = new Subtask("Subtask 2", "new Subtask description");
        Subtask subtask3 = new Subtask("Subtask 3", "new Subtask description");
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        // NEW
        actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача с подзадачами, статус которых только NEW. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);

        // IN_PROGRESS
        subtask1.updateStatus(); // 1-я поздазача теперь IN_PROGRESS
        expectedStatus = Status.IN_PROGRESS;
        actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача, у которой есть подзадачи NEW и подзадачи IN_PROGRESS. Ожидаемый статус: " + expectedStatus + ", реальный:: " + actualStatus);

        // IN_PROGRESS
        subtask1.updateStatus(); // 1-я подзадача стала DONE
        actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача, у которой есть подзадачи NEW и DONE. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);

        // DONE
        subtask2.updateStatus();
        subtask2.updateStatus();
        subtask3.updateStatus();
        subtask3.updateStatus();  // Все стали DONE
        expectedStatus = Status.DONE;
        actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача, у которой все подзадачи DONE. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

}