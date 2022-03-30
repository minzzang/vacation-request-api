package com.project.api.member.enums;

public enum VacationType {

    ANNUAL, HALF, HALF_HALF;

    public boolean isAnnual() {
        return this == ANNUAL;
    }
}
