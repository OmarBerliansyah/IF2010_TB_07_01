package com.SpakborHills.environment;

public interface DayObserver {
    void onDayChanged(int newDay, Season season, Weather weather);
}