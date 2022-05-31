package com.taskproject.launch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taskproject.kv.HttpTaskServer;
import com.taskproject.kv.KVServer;

import com.taskproject.manager.Managers;
import com.taskproject.manager.TaskManager;
import com.taskproject.tasks.Epic;
import com.taskproject.tasks.Subtask;
import com.taskproject.tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

public class Main {

    private static HttpClient client = HttpClient.newHttpClient();
    private static String serverURL = "http://localhost:" + HttpTaskServer.PORT;
    private static Gson gson = new Gson();

    private static Task addNewTask(Task task) {
        try {
            URI url = URI.create(serverURL + "/tasks/task/");
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            return gson.fromJson(value, Task.class);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return null;
        }
    }

    private static Subtask addNewSubtask(Subtask subtask) {
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/");
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(subtask));
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            return gson.fromJson(value, Subtask.class);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return null;
        }
    }

    private static Epic addNewEpic(Epic epic) {
        try {
            URI url = URI.create(serverURL + "/tasks/epic/");
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            Epic epic1 = gson.fromJson(value, Epic.class);
            epic1.restoreSubtasks();
            return epic1;
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return null;
        }
    }

    private static void updateTask(Task task) {
        try {
            URI url = URI.create(serverURL + "/tasks/task/");
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void updateSubtask(Subtask subtask) {
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/");
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(subtask));
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void updateEpic(Epic epic) {
        try {
            URI url = URI.create(serverURL + "/tasks/epic/");
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static Task getTaskById(Long id) {
        Task task = null;
        try {
            URI url = URI.create(serverURL + "/tasks/task/?id=" + id);
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            task = gson.fromJson(value, Task.class);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return task;
    }

    private static Subtask getSubtaskById(Long id) {
        Subtask subtask = null;
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/?id=" + id);
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            subtask = gson.fromJson(value, Subtask.class);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return subtask;
    }

    private static Epic getEpicById(Long id) {
        Epic epic = null;
        try {
            URI url = URI.create(serverURL + "/tasks/epic/?id=" + id);
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            epic = gson.fromJson(value, Epic.class);
            epic.restoreSubtasks();
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return epic;
    }

    private static List<Task> getListOfAllTasks() {
        List<Task> allTasks = null;
        try {
            URI url = URI.create(serverURL + "/tasks/task/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            allTasks = gson.fromJson(value, new TypeToken<List<Task>>(){}.getType());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return allTasks;
    }

    private static List<Subtask> getListOfAllSubtasks() {
        List<Subtask> allSubtasks = null;
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            allSubtasks = gson.fromJson(value, new TypeToken<List<Subtask>>(){}.getType());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return allSubtasks;
    }

    private static List<Epic> getListOfAllEpics() {
        List<Epic> allEpics = null;
        try {
            URI url = URI.create(serverURL + "/tasks/epic/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            allEpics = gson.fromJson(value, new TypeToken<List<Epic>>(){}.getType());
            for(Epic epic : allEpics) {
                epic.restoreSubtasks();
            }
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return allEpics;
    }

    private static void removeTaskById(Long id) {
        try {
            URI url = URI.create(serverURL + "/tasks/task/?id=" + id);
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void removeSubtaskById(Long id) {
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/?id=" + id);
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void removeEpicById(Long id) {
        try {
            URI url = URI.create(serverURL + "/tasks/epic/?id=" + id);
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void clearAllTasks() {
        try {
            URI url = URI.create(serverURL + "/tasks/task/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void clearAllSubtasks() {
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static void clearAllEpics() {
        try {
            URI url = URI.create(serverURL + "/tasks/epic/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private static List<Subtask> getSubtaskListOfEpic(Epic epic) {
        List<Subtask> subtasksOfEpic = null;
        try {
            URI url = URI.create(serverURL + "/tasks/subtask/epic/?id=" + epic.getId());
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            subtasksOfEpic = gson.fromJson(value, new TypeToken<List<Subtask>>(){}.getType());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return subtasksOfEpic;
    }

    private static List<Task> history() {
        List<Task> history = null;
        try {
            URI url = URI.create(serverURL + "/tasks/history/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            history = gson.fromJson(value, new TypeToken<List<Task>>(){}.getType());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return history;
    }

    private static Collection<Task> getPrioritizedTasks() {
        Collection<Task> prioritizedTasks = null;
        try {
            URI url = URI.create(serverURL + "/tasks/");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String value = response.body();
            prioritizedTasks = gson.fromJson(value, new TypeToken<Collection<Task>>(){}.getType());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        return prioritizedTasks;
    }

    public static void main(String[] args) throws IOException {

        Task task1 = new Task("Задача 1", "Сделать уборку");
        Task task2 = new Task("Задача 2", "Помыть посуду");

        Epic epic1 = new Epic("Большая задача 1", "Отдохнуть на море");
        Subtask subtask1 = new Subtask("Подзадача 1", "Забронировать гостиницу");
        Subtask subtask2 = new Subtask("Подзадача 2", "Купить билеты на самолет");
        Subtask subtask3 = new Subtask("Подзадача 3", "Собрать вещи");

        Epic epic2 = new Epic("Большая задача 2", "Сделать ремонт");

        KVServer kvServer = null;
        HttpTaskServer httpTaskServer = null;
        try {
            kvServer = new KVServer();
            kvServer.start();
            httpTaskServer = new HttpTaskServer();
            httpTaskServer.start();

            System.out.println("Проверяем работу метода addNewTask");
            task1 = addNewTask(task1);
            task2 = addNewTask(task2);

            System.out.println("Проверяем работу метода getTaskById");
            Task task1_new = getTaskById(task1.getId());
            System.out.println(task1_new);
            Task task2_new = getTaskById(task2.getId());
            System.out.println(task2_new);

            System.out.println("Проверяем работу метода updateTask");
            task1.setName("Задача № 1");
            updateTask(task1);
            // будет дубль в истории
            System.out.println(getTaskById(task1.getId()));

            System.out.println("Проверяем работу метода addNewEpic");
            epic1 = addNewEpic(epic1);
            epic2 = addNewEpic(epic2);

            System.out.println("Проверяем работу метода getEpicById");
            Epic epic1_new = getEpicById(epic1.getId());
            System.out.println(epic1_new);
            Epic epic2_new = getEpicById(epic2.getId());
            System.out.println(epic2_new);

            System.out.println("Проверяем работу метода updateEpic");
            epic1.setName("Большая задача № 1");
            updateEpic(epic1);
            // будет дубль в истории
            System.out.println(getEpicById(epic1.getId()));

            System.out.println("Проверяем работу метода addNewSubtask");
            subtask1 = addNewSubtask(subtask1);
            subtask2 = addNewSubtask(subtask2);
            subtask3 = addNewSubtask(subtask3);

            System.out.println("Проверяем работу метода getSubtaskById");
            Subtask subtask1_new = getSubtaskById(subtask1.getId());
            System.out.println(subtask1_new);
            Subtask subtask2_new = getSubtaskById(subtask2.getId());
            System.out.println(subtask2_new);

            System.out.println("Проверяем работу метода updateSubtask");
            subtask1.setName("Подзадача № 1");
            updateSubtask(subtask1);
            // будет дубль в истории
            System.out.println(getSubtaskById(subtask1.getId()));

            epic1.addSubtask(subtask1);
            epic1.addSubtask(subtask2);
            epic1.addSubtask(subtask3);

            System.out.println("Ещё раз делаем updateEpic для epic1, т.к. в него добавляли подзадачи");
            updateEpic(epic1);
            System.out.println(getEpicById(epic1.getId()));

            System.out.println("Ещё раз делаем updateSubtask для подзадач эпика epic1, т.к. их в него добавляли");
            updateSubtask(subtask1);
            updateSubtask(subtask2);
            updateSubtask(subtask3);
            System.out.println(getSubtaskById(subtask1.getId()));
            System.out.println(getSubtaskById(subtask2.getId()));
            System.out.println(getSubtaskById(subtask3.getId()));

            System.out.println("Проверяем работу метода getListOfAllTasks");
            List<Task> allTasks = getListOfAllTasks();
            System.out.println(allTasks);

            System.out.println("Проверяем работу метода getListOfAllEpics");
            List<Epic> allEpics = getListOfAllEpics();
            System.out.println(allEpics);

            System.out.println("Проверяем работу метода getListOfAllSubtasks");
            List<Subtask> allSubtasks = getListOfAllSubtasks();
            System.out.println(allSubtasks);

            System.out.println("Проверяем работу метода getSubtaskListOfEpic");
            List<Subtask> subs1 = getSubtaskListOfEpic(epic1);
            System.out.println(epic1.getName() + ": " + subs1);
            List<Subtask> subs2 = getSubtaskListOfEpic(epic2);
            System.out.println(epic2.getName() + ": " + subs2);

            System.out.println("Проверяем работу метода history");
            List<Task> history = history();
            System.out.println("История: " + history);

            System.out.println("Проверяем работу метода removeTaskById");
            removeTaskById(task1.getId());
            System.out.println(getListOfAllTasks());

            System.out.println("Проверяем работу метода removeSubtaskById");
            removeSubtaskById(subtask1.getId());
            System.out.println(getListOfAllSubtasks());

            System.out.println("Проверяем работу метода removeEpicById");
            removeEpicById(epic1.getId());
            System.out.println(getListOfAllEpics());

            System.out.println("Проверяем работу метода clearAllTasks");
            clearAllTasks();
            System.out.println(getListOfAllTasks());

            System.out.println("Проверяем работу метода clearAllEpics");
            clearAllEpics();
            System.out.println(getListOfAllEpics());

            System.out.println("Проверяем работу метода clearAllSubtasks");
            clearAllSubtasks();
            System.out.println(getListOfAllSubtasks());

            history = history();
            System.out.println("История: " + history);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (httpTaskServer != null) {
                httpTaskServer.stop();
            }
            if (kvServer != null) {
                kvServer.stop();
            }
        }

    }

}