package project.backend.objects;

public class Tasks {

    private int taskID;
    private String doDate;
    private String taskTitle;
    private String taskDiscription;
    private String assinedTo;

    public Tasks() {}

    public String toString() {
        return this.taskID + " " + this.doDate + " " + this.taskTitle + " " + this.taskDiscription + " " + this.assinedTo;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDiscription() {
        return taskDiscription;
    }

    public void setTaskDiscription(String taskDiscription) {
        this.taskDiscription = taskDiscription;
    }

    public String getDoDate() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate;
    }

    public String getAssinedTo() {
        return assinedTo;
    }

    public void setAssinedTo(String assinedTo) {
        this.assinedTo = assinedTo;
    }
    
}
