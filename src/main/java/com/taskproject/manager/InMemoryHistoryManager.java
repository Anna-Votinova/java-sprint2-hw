package com.taskproject.manager;

import com.taskproject.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    class LinkedList {
        private Node tail;

        void linkLast(Task task) {
            Node node = new Node(task, tail, null);
            if(tail != null) {
                tail.setNext(node);
            }
            tail = node;
        }

        List<Task> getTasks() {
            List<Task> list = new ArrayList<>();
            Node current = tail;
            while(current != null) {
                list.add(0, current.getTask());
                current = current.getPrev();
            }
            return list;
        }

        void removeNode(Node node) {
            Node prev = node.getPrev();
            Node next = node.getNext();
            if(prev != null) {
                prev.setNext(next);
            }
            if(next != null) {
                next.setPrev(prev);
            }
            if(tail == node) {
                tail = prev;
            }
        }
    }

    private final LinkedList listHistory = new LinkedList();
    private final Map<Long, Node> nodes = new HashMap<>();

    @Override
    public void add(Task task) {
        if(task != null) {
        Node node = nodes.get(task.getId());
            if(node != null) {
                listHistory.removeNode(node);
            }
            listHistory.linkLast(task);
            nodes.put(task.getId(), listHistory.tail);
        }
    }

    @Override
    public void remove(long id) {
        Node node = nodes.get(id);
        if(node != null) {
            listHistory.removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        return listHistory.getTasks();
    }
}
