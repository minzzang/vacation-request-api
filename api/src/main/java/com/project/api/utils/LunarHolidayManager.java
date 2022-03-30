package com.project.api.utils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LunarHolidayManager {

    public static Set<LocalDate> getHolidays() {
        Set<LocalDate> holidays = new HashSet<>();

        registerHolidays(holidays);
        return holidays;
    }

    private static void registerHolidays(Set<LocalDate> holidays) {
        // 설날
        holidays.add(LocalDate.of(2022, 1, 31));
        holidays.add(LocalDate.of(2022, 2, 1));
        holidays.add(LocalDate.of(2022, 2, 2));

        // 석가탄신일
        holidays.add(LocalDate.of(2022, 5, 8));

        // 추석
        holidays.add(LocalDate.of(2022, 9, 9));
        holidays.add(LocalDate.of(2022, 9, 10));
        holidays.add(LocalDate.of(2022, 9, 11));
        holidays.add(LocalDate.of(2022, 9, 12));
    }
}
