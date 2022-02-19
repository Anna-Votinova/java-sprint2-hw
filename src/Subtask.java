public class Subtask extends Task {

    private Epic epic;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Epic getEpic() {
        return epic;
    }

    protected void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public void updateStatus() {
        super.updateStatus();
        if (epic != null) {
            epic.updateStatus();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", epic=" + getEpic().getId();
    }

    @Override
    public String getTypeOfTask() {
        return "subtask";
    }
}
