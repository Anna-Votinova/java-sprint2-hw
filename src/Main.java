public class Main {
    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        Task task1 = new Task("Задача 1", "Сделать уборку");
        Task task2 = new Task("Задача 2", "Помыть посуду");

        Epic epic1 = new Epic ("Большая задача 1", "Отдохнуть на море");
        Subtask subtask1 = new Subtask("Подзадача 1", "Забронировать гостиницу");
        Subtask subtask2 = new Subtask("Подзадача 2", "Купить билеты на самолет");

        Epic epic2 = new Epic ("Большая задача 2", "Сделать ремонт");
        Subtask subtask3 = new Subtask("Подзадача 3", "Поклеить новые обои");

        task1 = manager.createNewTask(task1);
        task2 = manager.createNewTask(task2);
        epic1 = manager.createNewEpic(epic1);
        epic2 = manager.createNewEpic(epic2);
        subtask1 = manager.createNewSubtask(subtask1);
        subtask2 = manager.createNewSubtask(subtask2);
        subtask3 = manager.createNewSubtask(subtask3);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic2.addSubtask(subtask3);

        System.out.println(manager.getListOfAllTasks() + "\n");
        System.out.println(manager.getListOfAllEpics() + "\n");
        System.out.println(manager.getListOfAllSubtasks() + "\n");

        task1.updateStatus();
        System.out.println(manager.getTaskById(task1.getId()) + "\n");
        task2.updateStatus();
        System.out.println(manager.getTaskById(task2.getId()) + "\n");
        epic1.updateStatus();
        System.out.println(manager.getEpicById(epic1.getId()) + "\n");
        epic2.updateStatus();
        System.out.println(manager.getEpicById(epic2.getId()) + "\n");
        subtask1.updateStatus();
        System.out.println(manager.getEpicById(subtask1.getEpic().getId()) + "\n");
        subtask2.updateStatus();
        System.out.println(manager.getEpicById(subtask2.getEpic().getId()) + "\n");
        subtask3.updateStatus();
        System.out.println(manager.getEpicById(subtask3.getEpic().getId()) + "\n");
        subtask3.updateStatus();
        System.out.println(manager.getEpicById(subtask3.getEpic().getId()) + "\n");

        manager.removeTaskById(task1.getId());
        System.out.println(manager.getTaskById(task1.getId()) + "\n");
        manager.removeEpicById(epic1.getId());
        System.out.println(manager.getEpicById(epic1.getId()) + "\n");
    }
}