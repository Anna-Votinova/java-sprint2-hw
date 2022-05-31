package com.taskproject.kv;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Постман: https://www.getpostman.com/collections/a83b61d9e1c81c10575c
 */


public class KVServer {
    public static final int PORT = 8078;
    private String API_KEY;
    private HttpServer server;
    private Map<String, String> data = new HashMap<>();

    public KVServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", (h) -> {
            try {
                //System.out.println("\n/register");
                switch (h.getRequestMethod()) {
                    case "GET":
                        API_KEY = generateApiKey();
                        System.out.println("API_KEY: " + API_KEY);
                        sendText(h, API_KEY);
                        break;
                    default:
                        System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/save", (h) -> {
            try {
                //System.out.println("\n/save");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_KEY со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "POST":
                        String key = h.getRequestURI().toString().substring("/save/".length());
                        if (key.startsWith("?API_KEY=")) {
                            key = key.substring("?API_KEY=".length());
                        }
                        if (key.isEmpty()) {
                            System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        String value = readText(h);
                        if (value.isEmpty()) {
                            System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        data.put(key, value);
                        //System.out.println("Значение для ключа " + key + " успешно обновлено!");
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/load", (h) -> {
            // Добавляем получение значения по ключу
            try {
                //System.out.println("\n/load");
                if (!hasAuth(h)) {
                    System.out.println("Запрос (load) неавторизован, нужен параметр в query API_KEY со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "GET":
                        String key = h.getRequestURI().toString().substring("/load/".length());
                        if (key.startsWith("?API_KEY=")) {
                            key = key.substring("?API_KEY=".length());
                        }
                        if (key.isEmpty()) {
                            System.out.println("Key для получения значения пустой. key указывается в пути: /load/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }

                        String value = data.get(key);
                        String response = (value == null ? "null" : value);
                        System.out.println("Возвращаем значение для ключа " + key);
                        sendText(h, response);

                        break;
                    default:
                        System.out.println("/load ждёт GET-запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
    }

    public void start() {
        System.out.println("Запускаем сервер KVServer на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        server.stop(1); // максимальное время задержки в секундах (время на то, чтобы завершить все обмены данными)
    }

    private String generateApiKey() {
        return "" + System.currentTimeMillis();
    }

    protected boolean hasAuth(HttpExchange h) {
        String rawQuery = h.getRequestURI().getRawQuery();
        return rawQuery != null && (rawQuery.contains("API_KEY=" + API_KEY) || rawQuery.contains("API_KEY=DEBUG"));
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
}