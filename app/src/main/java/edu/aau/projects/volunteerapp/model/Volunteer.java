package edu.aau.projects.volunteerapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Volunteer {
    private int v_id;
    private int u_id;
    private User user;
    private List<String> skills;
    private String availability;
    private int completedTasks;

    public Volunteer() {
        this.skills = new ArrayList<>();
        this.availability = "";
    }

    public Volunteer(User user) {
        this();
        this.user = user;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public User getUser() {
        return user;
    }

//    public void setU_id(int u_id) {
//        this.u_id = u_id;
//    }

    public List<String> getSkills() {
        return skills;
    }

    public String toFormattedForm(){
        String skill = "";
        for (String sk :
                skills) {
            skill += sk + "|";
        }
        skill = skill.substring(0, skill.length() - 1);
        return skill;
    }

    public static List<String> getFromFormatted(String skills){
        List<String> list = new ArrayList<>();
        String[] res = skills.split("\\|");
        list.addAll(Arrays.asList(res));
        return list;
    }
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "v_id=" + v_id +
                ", user=" + user.getName() +
                ", skills='" + skills + '\'' +
                ", availability='" + availability + '\'' +
                ", completedTasks=" + completedTasks +
                '}';
    }
}
