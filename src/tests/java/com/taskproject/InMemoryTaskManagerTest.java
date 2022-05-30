package com.taskproject;

import com.taskproject.manager.Managers;
import com.taskproject.TaskManagerTest;

class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManagerTest() {
        super(Managers.getDefault());
    }

}