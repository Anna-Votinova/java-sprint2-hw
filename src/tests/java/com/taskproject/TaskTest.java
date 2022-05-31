package com.taskproject;

import com.taskproject.tasks.Status;
import com.taskproject.tasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskTest {

    /*
    //////////////////  тесты на метод updateStatus()
    */

    @Test
    public void taskStatus_shouldBeNew() {
        Task task = new Task("Task 1", "new Task description");

        Status expectedStatus = Status.NEW;
        Status actualStatus = task.getStatus();
        assertEquals(expectedStatus, actualStatus, "Новая задача. Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

    @Test
    public void taskStatus_shouldBeInProgress() {
        Task task = new Task("Task 1", "new Task description");

        task.setStatus(Status.IN_PROGRESS);

        Status expectedStatus = Status.IN_PROGRESS;
        Status actualStatus = task.getStatus();
        assertEquals(expectedStatus, actualStatus, "Задача 1 раз сделала updateStatus(). Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

    @Test
    public void taskStatus_shouldBeDone() {
        Task task = new Task("Task 1", "new Task description");

        task.setStatus(Status.DONE);

        Status expectedStatus = Status.DONE;
        Status actualStatus = task.getStatus();
        assertEquals(expectedStatus, actualStatus, "Задача 2 раза сделала updateStatus(). Ожидаемый статус: " + expectedStatus + ", реальный: " + actualStatus);
    }

}