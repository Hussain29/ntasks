package com.example.ntasks;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {

    private String taskID;
    private String taskName;
    private String taskDescription;
    private String priority;
    private String deadline;
    private String status;
    private String assignedUser;  // Add this line

    private String assignerdb;

    private String clientdb;


    public TaskModel() {
        // Default constructor required for calls to DataSnapshot.getValue(TaskModel.class)
    }

    public TaskModel(String taskID, String taskName, String taskDescription, String priority, String deadline, String status, String assignedUser, String assignerdb, String clientdb) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.assignedUser = assignedUser;
        this.assignerdb = assignerdb;
        this.clientdb = clientdb;

    }

    protected TaskModel(Parcel in) {
        taskID = in.readString();
        taskName = in.readString();
        taskDescription = in.readString();
        priority = in.readString();
        deadline = in.readString();
        status = in.readString();
        assignedUser = in.readString();  // Add this line
        assignerdb = in.readString();
        clientdb = in.readString();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

    public String getAssignerdb() {
        return assignerdb;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setAssignerdb(String assignerdb) {
        this.assignerdb = assignerdb;
    }

    public String getClientdb() {
        return clientdb;
    }

    public void setClientdb(String clientdb) {
        this.clientdb = clientdb;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getAssignedUser() {
        return assignedUser;
    }  // Add this method

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getStatus() {
        return status;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskID);
        dest.writeString(taskName);
        dest.writeString(taskDescription);
        dest.writeString(priority);
        dest.writeString(deadline);
        dest.writeString(status);
        dest.writeString(assignedUser);
        dest.writeString(assignerdb);
        dest.writeString(clientdb);// Add this line
    }
}

/*
package com.example.ntasks;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {

    private String taskID;
    private String taskName;
    private String taskDescription;
    private String priority;
    private String deadline;
    private String status;

    public TaskModel() {
        // Default constructor required for calls to DataSnapshot.getValue(TaskModel.class)
    }

    public TaskModel(String taskID, String taskName, String taskDescription, String priority, String deadline, String status) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    protected TaskModel(Parcel in) {
        taskID = in.readString();
        taskName = in.readString();
        taskDescription = in.readString();
        priority = in.readString();
        deadline = in.readString();
        status = in.readString();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };


    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public String getTaskID() {
        return taskID;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskID);
        dest.writeString(taskName);
        dest.writeString(taskDescription);
        dest.writeString(priority);
        dest.writeString(deadline);
        dest.writeString(status);
    }
}
*/
