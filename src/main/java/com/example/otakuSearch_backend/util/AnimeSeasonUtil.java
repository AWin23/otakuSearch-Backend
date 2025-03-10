package com.example.otakuSearch_backend.util;

import java.time.Month;
import java.time.LocalDate;

public class AnimeSeasonUtil {

    // Method to get the upcoming season based on the current month
    public static String getUpcomingSeason(int currentYear) {
        // Get the current month and year
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        // Logic to determine the upcoming anime season
        if (currentMonth == Month.DECEMBER || currentMonth == Month.JANUARY || currentMonth == Month.FEBRUARY) {
            return "SPRING";
        } else if (currentMonth == Month.MARCH || currentMonth == Month.APRIL || currentMonth == Month.MAY) {
            return "SUMMER";
        } else if (currentMonth == Month.JUNE || currentMonth == Month.JULY || currentMonth == Month.AUGUST) {
            return "FALL";
        } else {
            return "WINTER";
        }
    }

    // Method to get the current season as a String based on the current month
    public static String getCurrentSeason() {
        // Get the current month
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
    
        // Logic to determine the current anime season
        if (currentMonth == Month.JANUARY || currentMonth == Month.FEBRUARY || currentMonth == Month.MARCH) {
            return "WINTER";  // current season is Winter
        } else if (currentMonth == Month.APRIL || currentMonth == Month.MAY || currentMonth == Month.JUNE) {
            return "SPRING";  // current season will is Spring
        } else if (currentMonth == Month.JULY || currentMonth == Month.AUGUST || currentMonth == Month.SEPTEMBER) {
            return "SUMMER";  // current season is Summer
        } else {
            return "FALL";  // Next season will be Fal
        }
    }


    // Method to get the current season based on current month
    public static Month getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonth();  // Returns the current month (e.g., JANUARY)
    }

    // Get the current year for this season
    public static int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();  // Returns the current year (e.g., 2025)
    }


    // Get the next year for the season
    public static int getUpcomingYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue() >= 10 ? currentDate.getYear() + 1 : currentDate.getYear();
    }
}
