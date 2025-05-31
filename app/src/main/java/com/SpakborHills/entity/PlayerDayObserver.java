package com.SpakborHills.entity;

import com.SpakborHills.environment.DayObserver;
import com.SpakborHills.environment.Season;
import com.SpakborHills.environment.Weather;

/**
 * Observer that handles player-related day changes
 */
public class PlayerDayObserver implements DayObserver {
    private Player player;
    
    public PlayerDayObserver(Player player) {
        this.player = player;
    }
    
    @Override
    public void onDayChanged(int newDay, Season season, Weather weather) {
        System.out.println("PlayerDayObserver: New day " + newDay + " for player " + player.name);
        
        // Player-specific day change logic
        if (player != null) {
            // Check special days and milestones
            checkSpecialDays(newDay, season);
            
            // Reset daily limits
            resetDailyLimits();
            
            // Check achievements
            checkDayBasedAchievements(newDay);
            
            // Check relationship milestones
            checkRelationshipMilestones(newDay);
            
            // Seasonal greetings
            checkSeasonalEvents(newDay, season, weather);
        }
    }
    
    private void checkSpecialDays(int day, Season season) {
        // Weekly milestone
        if (day % 7 == 0) {
            player.gp.ui.addMessage("It's been " + (day/7) + " week(s) since you started farming!");
        }
        
        // Monthly milestone
        if (day == 30) {
            player.gp.ui.addMessage("You've been farming for a month! Keep it up!");
        } else if (day == 60) {
            player.gp.ui.addMessage("Two months of farming! You're getting experienced!");
        } else if (day == 90) {
            player.gp.ui.addMessage("Three months! You're becoming a veteran farmer!");
        }
        
        // Season start messages
        if (day % 40 == 1) { // Assuming 40 days per season
            switch (season) {
                case SPRING:
                    player.gp.ui.addMessage("Spring has arrived! Time to plant new crops!");
                    break;
                case SUMMER:
                    player.gp.ui.addMessage("Summer is here! Perfect weather for farming!");
                    break;
                case FALL:
                    player.gp.ui.addMessage("Fall has come! Time to harvest and prepare for winter!");
                    break;
                case WINTER:
                    player.gp.ui.addMessage("Winter is here! A time to rest and plan for next year!");
                    break;
            }
        }
    }
    
    private void resetDailyLimits() {
        // Reset any daily mechanics if you have them
        // Example: daily fishing bonus, daily gift limits, etc.
        
        // For now, just log that daily reset happened
        System.out.println("Player daily limits reset for day");
    }
    
    private void checkDayBasedAchievements(int day) {
        // Early achievements
        if (day == 1) {
            player.gp.ui.addMessage("Welcome to Spakbor Hills! Your farming journey begins!");
        } else if (day == 3) {
            player.gp.ui.addMessage("You've survived your first few days! Keep going!");
        } else if (day == 7) {
            player.gp.ui.addMessage("Achievement: First Week Complete!");
        } else if (day == 10) {
            player.gp.ui.addMessage("Achievement: Survived 10 days!");
        } else if (day == 25) {
            player.gp.ui.addMessage("Achievement: Experienced Farmer!");
        } else if (day == 50) {
            player.gp.ui.addMessage("Achievement: Veteran Farmer!");
        } else if (day == 100) {
            player.gp.ui.addMessage("Achievement: Master of Time!");
        } else if (day == 200) {
            player.gp.ui.addMessage("Achievement: Legendary Farmer!");
        }
        
        // Special number achievements
        if (day == 365) {
            player.gp.ui.addMessage("Achievement: One Full Year! You're a true farmer now!");
        }
    }
    
    private void checkRelationshipMilestones(int day) {
        // Check if player has achieved relationship milestones
        if (player.partner != null) {
            // Marriage anniversary messages
            int marriageDay = getMarriageDay(); // You'd need to track this
            if (marriageDay > 0 && (day - marriageDay) % 30 == 0) {
                int months = (day - marriageDay) / 30;
                player.gp.ui.addMessage("Happy " + months + " month marriage anniversary with " + player.partner.name + "!");
            }
        }
    }
    
    private void checkSeasonalEvents(int day, Season season, Weather weather) {
        // Weather-based messages
        if (weather == Weather.RAINY && day % 5 == 0) {
            player.gp.ui.addMessage("Rainy day! Perfect for indoor activities or fishing!");
        }
        
        // Seasonal tips
        int dayInSeason = day % 40; // Assuming 40 days per season
        if (dayInSeason == 10) {
            switch (season) {
                case SPRING:
                    player.gp.ui.addMessage("Spring tip: Don't forget to water your crops daily!");
                    break;
                case SUMMER:
                    player.gp.ui.addMessage("Summer tip: Some crops grow faster in this season!");
                    break;
                case FALL:
                    player.gp.ui.addMessage("Fall tip: Great time for preserving and preparing for winter!");
                    break;
                case WINTER:
                    player.gp.ui.addMessage("Winter tip: Focus on relationships and planning next year's crops!");
                    break;
            }
        }
    }
    
    private int getMarriageDay() {
        // This would need to be tracked in Player class
        // For now, return 0 (not implemented)
        return 0;
    }
}