package com.taskproject.kv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class KVTaskClient {

    private URI serverURL;
    private String API;
    private HttpClient client = HttpClient.newHttpClient();

    public KVTaskClient(URI serverURL) throws IOException, InterruptedException {
        this.serverURL = serverURL;
        URI url = URI.create(serverURL.toString() + "/register");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        API = response.body();
    }

    public KVTaskClient(URI serverURL, String API) throws IOException, InterruptedException {
        this.serverURL = serverURL;
        this.API = API;
    }

    // должен сохранять состояние менеджера задач через запрос POST /save/<ключ>?API_KEY=.
    public void put(String key, String json) throws IOException, InterruptedException {
        URI url = URI.create(serverURL.toString() + "/save/?API_KEY=" + key);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_KEY=.
    public String load(String key) throws IOException, InterruptedException {
        URI url = URI.create(serverURL.toString() + "/load/?API_KEY=" + key);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String value = response.body();
        return value;
    }

    public String getAPI() {
        return API;
    }

    public URI getServerURL() {
        return serverURL;
    }

    public void setServerURL(URI serverURL) {
        this.serverURL = serverURL;
    }

    public void setAPI(String API) {
        this.API = API;
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }
}