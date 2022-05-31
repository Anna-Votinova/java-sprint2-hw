package com.taskproject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.taskproject.manager.Managers;
import com.taskproject.manager.TaskManager;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Status;
import com.taskproject.tasks.Subtask;

class EpicTest {

    TaskManager manager;

    EpicTest() {
        manager = Managers.getDefault();
    }

    /*
    //////////////////  тесты на метод addSubtask
    */

    @Test
    public void subtaskAddedToEpic_shouldBeInEpic() {
        Epic epic = new Epic("Epic 1", "new Epic description");
        Subtask subtask = new Subtask("Subtask 1", "new Subtask description");

        epic.addSubtask(subtask);

        // добавляем эпик и сабтаск в менеджер
        manager.addNewEpic(epic);
        manager.addNewSubtask(subtask);

        assertTrue(manager.getSubtaskListOfEpic(epic).contains(subtask), "Подзадачу добавили в большую задачу, но она там не обнаруживается");
    }

    @Test
    public void epicOfSubtaskAddedToEpic_shouldBeTheSame() {
        Epic epic = new Epic("Epic 1", "new Epic description");
        Subtask subtask = new Subtask("Subtask 1", "new Subtask description");

        epic.addSubtask(subtask);

        // добавляем эпик и сабтаск в менеджер, чтобы у них проставились id (т.к. сравнение задач идёт по их идентификаторам)
        manager.addNewEpic(epic);
        manager.addNewSubtask(subtask);

        Epic expectedEpic = epic;
        Epic actualEpic = subtask.getEpic();
        assertEquals(expectedEpic, actualEpic, "Подзадачу добавили в большую задачу. Ожидаемая большая задача подзадачи: " + expectedEpic + ", реальная: " + actualEpic);
    }

    @Test
    public void subtaskAddedToEpicTwice_shouldNotBeDoubled() {
        Epic epic = new Epic("Epic 1", "new Epic description");
        Subtask subtask = new Subtask("Subtask 1", "new Subtask description");

        // добавляем эпик и сабтаск в менеджер
        manager.addNewEpic(epic);
        manager.addNewSubtask(subtask);

        epic.addSubtask(subtask);
        epic.addSubtask(subtask);

        // проверяем, что добавленный дважды сабтаск не задублировался
        Integer expectedCountOfSubtasks = 1;
        Integer actualCountOfSubTasks = manager.getSubtaskListOfEpic(epic).size();
        assertEquals(expectedCountOfSubtasks, actualCountOfSubTasks, "Подзадачу добавили дважды в большую задачу. Ожидаемое количество подзадача: " + expectedCountOfSubtasks + ", реальное: " + actualCountOfSubTasks);
    }

    /*
    //////////////////  тесты на метод updateStatus
    */

    @Test
    public void statusOfEpicWithoutSubtasks_shouldBeNew() {
        Epic epic = new Epic("Epic 1", "new Epic description");

        Status expectedStatus = Status.NEW;
        Status actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача без подзадач. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

    @Test
    public void statusOfEpicWithSubtasksAllNew_shouldBeNew() {
        Epic epic = new Epic("Epic 1", "new Epic description");

        Subtask subtask1 = new Subtask("Subtask 1", "new Subtask description");
        Subtask subtask2 = new Subtask("Subtask 2", "new Subtask description");
        Subtask subtask3 = new Subtask("Subtask 3", "new Subtask description");
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        Status expectedStatus = Status.NEW;
        Status actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача с подзадачами, статус которых только NEW. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

    @Test
    public void statusOfEpicWithSubtasksNewAndInProgress_shouldBeInProgress() {
        Epic epic = new Epic("Epic 1", "new Epic description");

        Subtask subtask1 = new Subtask("Subtask 1", "new Subtask description");
        Subtask subtask2 = new Subtask("Subtask 2", "new Subtask description");
        Subtask subtask3 = new Subtask("Subtask 3", "new Subtask description");
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        // 1-я поздазача становится IN_PROGRESS
        subtask1.setStatus(Status.IN_PROGRESS);

        Status expectedStatus = Status.IN_PROGRESS;
        Status actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача, у которой есть подзадачи NEW и подзадачи IN_PROGRESS. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

    @Test
    public void statusOfEpicWithSubtasksNewAndDone_shouldBeInProgress() {
        Epic epic = new Epic("Epic 1", "new Epic description");

        Subtask subtask1 = new Subtask("Subtask 1", "new Subtask description");
        Subtask subtask2 = new Subtask("Subtask 2", "new Subtask description");
        Subtask subtask3 = new Subtask("Subtask 3", "new Subtask description");
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        // 1-я подзадача становится DONE
        subtask1.setStatus(Status.DONE);

        Status expectedStatus = Status.IN_PROGRESS;
        Status actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача, у которой есть подзадачи NEW и DONE. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

    @Test
    public void statusOfEpicWithSubtasksAllDone_shouldBeDone() {
        Epic epic = new Epic("Epic 1", "new Epic description");

        Subtask subtask1 = new Subtask("Subtask 1", "new Subtask description");
        Subtask subtask2 = new Subtask("Subtask 2", "new Subtask description");
        Subtask subtask3 = new Subtask("Subtask 3", "new Subtask description");
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        // Все становятся DONE
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);

        Status expectedStatus = Status.DONE;
        Status actualStatus = epic.getStatus();
        assertEquals(expectedStatus, actualStatus, "Большая задача, у которой все подзадачи DONE. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

}