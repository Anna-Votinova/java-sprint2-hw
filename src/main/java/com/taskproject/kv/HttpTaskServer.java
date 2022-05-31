package com.taskproject.kv;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;
import com.taskproject.manager.Managers;
import com.taskproject.manager.TaskManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;


public class HttpTaskServer {

    public static final int PORT = 8080;
    private HttpServer server;
    private TaskManager manager = Managers.getDefault(URI.create("http://localhost:" + KVServer.PORT));

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        initServer();
    }

    private void initServer() {
        server.createContext("/tasks/task", getHandlerTask("/tasks/task/"));
        server.createContext("/tasks/subtask", getHandlerSubtask("/tasks/subtask/"));
        server.createContext("/tasks/epic", getHandlerEpic("/tasks/epic/"));
        server.createContext("/tasks/subtask/epic", getHandlerSubtasksOfEpic("/tasks/subtask/epic/"));
        server.createContext("/tasks/history", getHandlerHistory("/tasks/history/"));
        server.createContext("/tasks", getHandlerPrioritizedTasks("/tasks/"));
    }

    private HttpHandler getHandlerTask(String request) {
        Gson gson = new Gson();
        return (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (id.isEmpty()) { // запрос всех тасков
                            String response = gson.toJson(manager.getListOfAllTasks());
                            sendText(h, response);
                        } else {  // запрос конкретного таска по id
                            Task task = manager.getTaskById(Long.parseLong(id));
                            String response = gson.toJson(task, Task.class);
                            sendText(h, response);
                        }
                        break;
                    case "POST":
                        String value = readText(h);
                        if (!value.isEmpty()) {
                            Task task = gson.fromJson(value, Task.class);
                            if (task.getId() == null) {
                                task = manager.addNewTask(task);
                            } else {
                                manager.updateTask(task);
                            }
                            String response = gson.toJson(task);
                            sendText(h, response);
                        }
                        break;
                    case "DELETE":
                        id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (id.isEmpty()) {  // удаление всех тасков
                            manager.clearAllTasks();
                            sendText(h, "");
                        } else {  // удаление конкретного таска по id
                            manager.removeTaskById(Long.parseLong(id));
                            sendText(h, "");
                        }
                        break;
                    default:
                        System.out.println("??? Полученный тип запроса: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                h.close();
            }
        };
    }

    private HttpHandler getHandlerSubtask(String request) {
        Gson gson = new Gson();
        return (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (id.isEmpty()) {  // запрос всех сабтасков
                            String response = gson.toJson(manager.getListOfAllSubtasks());
                            sendText(h, response);
                        } else {  // запрос конкретного по id
                            Subtask subtask = manager.getSubtaskById(Long.parseLong(id));
                            String response = gson.toJson(subtask, Subtask.class);
                            sendText(h, response);
                        }
                        break;
                    case "POST":
                        String value = readText(h);
                        if (!value.isEmpty()) {
                            Subtask subtask = gson.fromJson(value, Subtask.class);
                            if (subtask.getId() == null) {
                                subtask = manager.addNewSubtask(subtask);
                            } else {
                                manager.updateSubtask(subtask);
                            }
                            String response = gson.toJson(subtask);
                            sendText(h, response);
                        }
                        break;
                    case "DELETE":
                        id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (id.isEmpty()) {  // удаление всех сабтасков
                            manager.clearAllSubtasks();
                            sendText(h, "");
                        } else {  // удаление конкретного сабтаска по id
                            manager.removeSubtaskById(Long.parseLong(id));
                            sendText(h, "");
                        }
                        break;
                    default:
                        System.out.println("??? Полученный тип запроса: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                h.close();
            }
        };
    }

    private HttpHandler getHandlerEpic(String request) {
        Gson gson = new Gson();
        return (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (id.isEmpty()) {  // запрос всех эпиков
                            String response = gson.toJson(manager.getListOfAllEpics());
                            sendText(h, response);
                        } else {  // запрос конкретного эпика по id
                            Epic epic = manager.getEpicById(Long.parseLong(id));
                            String response = gson.toJson(epic, Epic.class);
                            sendText(h, response);
                        }
                        break;
                    case "POST":
                        String value = readText(h);
                        if (!value.isEmpty()) {
                            Epic epic = gson.fromJson(value, Epic.class);
                            epic.restoreSubtasks();
                            if (epic.getId() == null) {
                                epic = manager.addNewEpic(epic);
                            } else {
                                manager.updateEpic(epic);
                            }
                            String response = gson.toJson(epic);
                            sendText(h, response);
                        }
                        break;
                    case "DELETE":
                        id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (id.isEmpty()) {  // удаление всех эпиков
                            manager.clearAllEpics();
                            sendText(h, "");
                        } else {  // удаление конкретного эпика по id
                            manager.removeEpicById(Long.parseLong(id));
                            sendText(h, "");
                        }
                        break;
                    default:
                        System.out.println("??? Полученный тип запроса: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                h.close();
            }
        };
    }

    private HttpHandler getHandlerSubtasksOfEpic(String request) {
        Gson gson = new Gson();
        return (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String id = h.getRequestURI().toString().substring(request.length());
                        if (id.startsWith("?id=")) {
                            id = id.substring("?id=".length());
                        }
                        if (!id.isEmpty()) {
                            Epic epic = manager.getEpicById(Long.parseLong(id));
                            String response = gson.toJson(manager.getSubtaskListOfEpic(epic));
                            sendText(h, response);
                        }
                        break;
                    default:
                        System.out.println("??? Полученный тип запроса: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                h.close();
            }
        };
    }

    private HttpHandler getHandlerHistory(String request) {
        Gson gson = new Gson();
        return (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String response = gson.toJson(manager.history());
                        sendText(h, response);
                        break;
                    default:
                        System.out.println("??? Полученный тип запроса: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                h.close();
            }
        };
    }

    private HttpHandler getHandlerPrioritizedTasks(String request) {
        Gson gson = new Gson();
        return (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String response = gson.toJson(manager.getPrioritizedTasks());
                        System.out.println("*** " + manager.getPrioritizedTasks());
                        sendText(h, response);
                        break;
                    default:
                        System.out.println("??? Полученный тип запроса: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                h.close();
            }
        };
    }

    protected String readText(HttpExchange h) throws IOException {
        //byte[] bytes = new byte[h.getRequestBody().available()];
        List<Byte> listBytes = new ArrayList<Byte>();
        int byt;
        while ((byt = h.getRequestBody().read()) != -1) {
            listBytes.add((byte)byt);
        }
        byte[] bytes = new byte[listBytes.size()];
        Iterator<Byte> iterator = listBytes.iterator();
        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = iterator.next();
        }
        return new String(bytes, "UTF-8");
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes("UTF-8");
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    public void start() {
        System.out.println("Запускаем сервер HTTPTaskServer на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        // максимальное время задержки в секундах (время на то, чтобы завершить все обмены данными)
        server.stop(1);
    }

}
