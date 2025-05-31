package com.SpakborHills.environment;

public interface DaySubject {
    void addDayObserver(DayObserver observer);
    void removeDayObserver(DayObserver observer);
    void notifyDayObservers(int newDay, Season season, Weather weather);
}
