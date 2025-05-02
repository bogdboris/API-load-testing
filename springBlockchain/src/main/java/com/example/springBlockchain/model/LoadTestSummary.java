package com.example.springBlockchain.model;

import java.util.List;

public class LoadTestSummary {
    private int totalSuccessCount;
    private int totalFailureCount;
    private double averageDuration;
    private List<LoadTestResult> allresults; // здесь каждый отдельный ответ

    // Геттеры и сеттеры

    public int getTotalSuccessCount() {
        return totalSuccessCount;
    }

    public void setTotalSuccessCount(int totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
    }

    public int getTotalFailureCount() {
        return totalFailureCount;
    }

    public void setTotalFailureCount(int totalFailureCount) {
        this.totalFailureCount = totalFailureCount;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }

    public List<LoadTestResult> getAllresults() {
        return allresults;
    }

    public void setAllresults(List<LoadTestResult> allresults) {
        this.allresults = allresults;
    }
}
