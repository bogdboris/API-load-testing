package com.example.springBlockchain.model;

public class ApiResponse {
    private String body;
    private long duration;
    private int statusCode;

    // Конструктор для успешного ответа (3 параметра)
    public ApiResponse(String body, int statusCode, long duration) {
        this.body = body;
        this.statusCode = statusCode;
        this.duration = duration;
    }

    // Конструктор для ошибки (2 параметра)
    public ApiResponse(String errorMessage, int statusCode) {
        this.body = errorMessage;
        this.statusCode = statusCode;
        this.duration = 0; // Для ошибок duration = 0
    }

    public String getBody() {
        return body;
    }

    public long getDuration() {
        return duration;
    }


    public void setBody(String body) {

        this.body = body;
    }

    public void setDuration(long duration) {

        this.duration = duration;
    }
    public int getStatusCode() {

        return statusCode;
    }

    public void setStatusCode(int statusCode) {

        this.statusCode = statusCode;
    }
}

