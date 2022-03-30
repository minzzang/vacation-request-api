package com.project.api.utils;

import java.time.LocalDate;
import java.util.Set;

public class GregorianHolidayManager {

    public static void registerHolidays(Set<LocalDate> holidays, int year) {
        holidays.add(LocalDate.of(year, 1, 1));
        holidays.add(LocalDate.of(year, 3, 1));
        holidays.add(LocalDate.of(year, 3, 9));
        holidays.add(LocalDate.of(year, 5, 5));
        holidays.add(LocalDate.of(year, 6, 6));
        holidays.add(LocalDate.of(year, 8, 15));
        holidays.add(LocalDate.of(year, 10, 3));
        holidays.add(LocalDate.of(year, 10, 9));
        holidays.add(LocalDate.of(year, 12, 25));
    }

}
