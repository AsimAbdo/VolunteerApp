package edu.aau.projects.volunteerapp.model;

public class VolunteerGroup {
    private int groupId;
    private String groupName;
    private String groupDescription;
    private String createdGroupDate;
    private int leaderId;

    public VolunteerGroup() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getCreatedGroupDate() {
        return createdGroupDate;
    }

    public void setCreatedGroupDate(String createdGroupDate) {
        this.createdGroupDate = createdGroupDate;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
}
