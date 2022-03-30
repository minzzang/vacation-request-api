package com.project.api.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class VacationCalculator {

    public static int calculate(int year, LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> holidays = LunarHolidayManager.getHolidays();
        GregorianHolidayManager.registerHolidays(holidays, year);

        int count = 0;
        endDate = endDate.plusDays(1);
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (holidays.contains(date) ||
                    date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY) continue;

            count++;
        }
        return count;
    }
}
