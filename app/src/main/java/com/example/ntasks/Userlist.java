// Userlist.java
package com.example.ntasks;

import com.google.firebase.database.PropertyName;

public class Userlist {
    @PropertyName("taskID")
    private String taskID;

    @PropertyName("deadlinedb")
    private String deadlinedb;

    @PropertyName("prioritydb")
    private String prioritydb;

    @PropertyName("statusdb")
    private String statusdb;

    @PropertyName("taskDescriptiondb")
    private String taskDescriptiondb;

    @PropertyName("taskNamedb")
    private String taskNamedb;

    @PropertyName("assignedUserdb")  // Replace with the actual field name for assigned user
    private String assignedUserdb;  // Add this line

    @PropertyName("assignerdb")  // Replace with the actual field name for assigned user
    private String assignerdb;  // Add this line

    @PropertyName("clientdb")  // Replace with the actual field name for assigned user
    private String clientdb;

    @PropertyName("lastchangeddb")  // Replace with the actual field name for assigned user
    private String lastchangeddb;// Add this line

    public Userlist() {
        // Default constructor required for calls to DataSnapshot.getValue(Userlist.class)
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

    public String getTaskID() {
        return taskID;
    }

    public String getTaskdeadl() {
        return deadlinedb;
    }

    public String getTaskprio() {
        return prioritydb;
    }

    public String getStatusdb() {
        return statusdb;
    }

    public String getTaskDesc() {
        return taskDescriptiondb;
    }

    public String getTaskName() {
        return taskNamedb;
    }

    public String getAssignedUserdb() {
        return assignedUserdb;  // Add this method
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setTaskdeadl(String deadlinedb) {
        this.deadlinedb = deadlinedb;
    }

    public void setTaskprio(String prioritydb) {
        this.prioritydb = prioritydb;
    }

    public void setStatusdb(String statusdb) {
        this.statusdb = statusdb;
    }

    public void setTaskDesc(String taskDescriptiondb) {
        this.taskDescriptiondb = taskDescriptiondb;
    }

    public void setTaskName(String taskNamedb) {
        this.taskNamedb = taskNamedb;
    }

    public void setAssignedUserdb(String assignedUserdb) {
        this.assignedUserdb = assignedUserdb;  // Add this method
    }

    public String getLastchangeddb() {
        return lastchangeddb;
    }

    public void setLastchangeddb(String lastchangeddb) {
        this.lastchangeddb = lastchangeddb;
    }
}

