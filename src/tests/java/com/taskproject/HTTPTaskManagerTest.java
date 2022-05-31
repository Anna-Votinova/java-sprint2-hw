package com.taskproject;

import com.taskproject.kv.KVServer;
import com.taskproject.manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.net.URI;

class HTTPTaskManagerTest extends TaskManagerTest {

    KVServer kvServer = new KVServer();

    HTTPTaskManagerTest() throws IOException {
        super(Managers.getDefault(URI.create("http://localhost:" + KVServer.PORT)));
    }

    @BeforeEach
    public void startServerKV() {
        kvServer.start();
    }

    @AfterEach
    public void stopServerKV() {
        kvServer.stop();
    }

}