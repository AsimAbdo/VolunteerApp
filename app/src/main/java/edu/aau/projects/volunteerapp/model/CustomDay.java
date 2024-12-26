package edu.aau.projects.volunteerapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomDay implements DaysTitle {
    private String dayName;
    private boolean isChecked;

    public CustomDay(String dayName, boolean isChecked) {
        this.dayName = dayName;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getDayName() {
        return dayName;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private static List<CustomDay> days;

    private static void setDays(){
        days = new ArrayList<>();
        days.add(new CustomDay(SATURDAY, false));
        days.add(new CustomDay(SUNDAY, false));
        days.add(new CustomDay(MONDAY, false));
        days.add(new CustomDay(TUESDAY, false));
        days.add(new CustomDay(WEDNESDAY, false));
        days.add(new CustomDay(THURSDAY, false));
        days.add(new CustomDay(FRIDAY, false));
    }
    public static List<CustomDay> getDays(String availability){
        setDays();
        for (int i = 0; i < days.size(); i++) {
            days.get(i)
                    .setChecked(
                            availability.contains(
                                    days.get(i).getDayName()
                            )
                    );
        }
        return days;
    }

    public static List<CustomDay> getDays() {
        return days;
    }

    public static String getAvailability(List<CustomDay> days){
        String availability = "";

        for (CustomDay day : days)
            if (day.isChecked)
                availability += day.getDayName() + "|";

        availability = availability.substring(0, availability.length() - 1);
        return availability;
    }
}
