package com.example.ntasks;

public class taskdb {
    private String taskNamedb;
    private String taskDescriptiondb;
    private String prioritydb;
    private String deadlinedb;
    private String statusdb;  // New field for status
    private String assignedUserdb;  // New field for assigned user
    private String assignerdb;

    private String clientdb;
    public taskdb() {
        // Default constructor required for calls to DataSnapshot.getValue(taskdb.class)
    }

    public taskdb(String taskNamedb, String taskDescriptiondb, String prioritydb, String deadlinedb, String statusdb, String assignedUserdb, String assignerdb, String clientdb) {
        this.taskNamedb = taskNamedb;
        this.taskDescriptiondb = taskDescriptiondb;
        this.prioritydb = prioritydb;
        this.deadlinedb = deadlinedb;
        this.statusdb = statusdb;
        this.assignedUserdb = assignedUserdb;
        this.assignerdb = assignerdb;
        this.clientdb = clientdb;
    }

    public String getClientdb() {
        return clientdb;
    }

    public void setClientdb(String clientdb) {
        this.clientdb = clientdb;
    }

    public String getAssignerdb() {
        return assignerdb;
    }

    public void setAssignerdb(String assignerdb) {
        this.assignerdb = assignerdb;
    }

    public String getTaskNamedb() {
        return taskNamedb;
    }

    public void setTaskNamedb(String taskNamedb) {
        this.taskNamedb = taskNamedb;
    }

    public String getTaskDescriptiondb() {
        return taskDescriptiondb;
    }

    public void setTaskDescriptiondb(String taskDescriptiondb) {
        this.taskDescriptiondb = taskDescriptiondb;
    }

    public String getPrioritydb() {
        return prioritydb;
    }

    public void setPrioritydb(String prioritydb) {
        this.prioritydb = prioritydb;
    }

    public String getDeadlinedb() {
        return deadlinedb;
    }

    public void setDeadlinedb(String deadlinedb) {
        this.deadlinedb = deadlinedb;
    }

    public String getStatusdb() {
        return statusdb;
    }

    public void setStatusdb(String statusdb) {
        this.statusdb = statusdb;
    }

    public String getAssignedUserdb() {
        return assignedUserdb;
    }

    public void setAssignedUserdb(String assignedUser) {
        this.assignedUserdb = assignedUser;
    }
}

/*package com.example.ntasks;

public class taskdb {
    public String taskNamedb;
    public String taskDescriptiondb;
    public String prioritydb;
    public String deadlinedb;
    public String statusdb;  // New field for status

    public taskdb() {
        // Default constructor required for calls to DataSnapshot.getValue(taskdb.class)
    }

    public taskdb(String taskNamedb, String taskDescriptiondb, String prioritydb, String deadlinedb, String statusdb) {
        this.taskNamedb = taskNamedb;
        this.taskDescriptiondb = taskDescriptiondb;
        this.prioritydb = prioritydb;
        this.deadlinedb = deadlinedb;
        this.statusdb = statusdb;
    }

    public void setTaskNamedb(String taskNamedb) {
        this.taskNamedb = taskNamedb;
    }

    public void setTaskDescriptiondb(String taskDescriptiondb) {
        this.taskDescriptiondb = taskDescriptiondb;
    }

    public void setPrioritydb(String prioritydb) {
        this.prioritydb = prioritydb;
    }

    public void setDeadlinedb(String deadlinedb) {
        this.deadlinedb = deadlinedb;
    }

    public String getStatusdb() {
        return statusdb;
    }

    public void setStatusdb(String statusdb) {
        this.statusdb = statusdb;
    }
}*/
