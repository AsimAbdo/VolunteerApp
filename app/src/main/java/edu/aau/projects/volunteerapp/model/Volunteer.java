package edu.aau.projects.volunteerapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Volunteer implements CashFundHolder {
    private int v_id;
    private User user;
    private List<String> skills;
    private String availability;
    private int completedTasks;
    private int teamLeader = 0;
    private CashFund cashFund;

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

    public List<String> getSkills() {
        return skills;
    }

    public String toFormattedForm(){
        String skill = "";
        for (int i = 0; i < skills.size(); i++) {
            skill += skills.get(i);
            if (i != skills.size() - 1)
                skill += "|";
        }
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

    public CashFund getCashFund() {
        return cashFund;
    }

    public void setTeamLeader(int teamLeader) {
        this.teamLeader = teamLeader;
    }

    public int getTeamLeader() {
        return teamLeader;
    }

    public void createCashFund() {
        this.cashFund = getCashFundData();
    }
    @Override
    public CashFund getCashFundData() {
        CashFund cashFund = new CashFund();
        cashFund.setBalance(0.0);
        cashFund.setFundName(this.getUser().getName().concat(" Cash Fund"));
        cashFund.setOwnerType("Individual");
        cashFund.setOwnerId(getV_id());
        return cashFund;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("v_id", v_id);
        map.put("availability", availability);
        map.put("skills", skills);
        map.put("user", user);
        map.put("completedTasks", completedTasks);
        map.put("teamLeader", teamLeader);
        map.put("cashFund", cashFund);
        return map;
    }
}
