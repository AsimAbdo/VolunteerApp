package edu.aau.projects.volunteerapp.model;

import java.util.HashMap;
import java.util.Map;

public class MTask {
    private int taskId;
    private int seekerId;
    private String assignedTo; //type
    private int assignedToId;
    private TaskDescription description;
    private String startDate;
    private String endDate;
    private String status;
    private int fundId;
    private int warehouseId;

    public MTask() {
        this.assignedTo = "";
        this.assignedToId = 0;
        this.fundId = 0;
        this.warehouseId = 0;
        this.description = new TaskDescription();
        this.status = "";
    }

    public MTask(int seekerId, TaskDescription description, String startDate, String endDate, String status) {
        this.seekerId = seekerId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public MTask(int seekerId) {
        this();
        this.seekerId = seekerId;
    }

    public void setDescription(TaskDescription description) {
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(int seekerId) {
        this.seekerId = seekerId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getStartDate() {
        return startDate;
    }

    public TaskDescription getDescription() {
        return description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("taskId", taskId);
        map.put("assignedTo", assignedTo);
        map.put("assignedToId", assignedToId);
        map.put("status", status);
        map.put("seekerId", seekerId);
        map.put("warehouseId", warehouseId);
        map.put("fundId", fundId);
        map.put("description", description);
        return map;
    }
}
