package com.example.poetryapp.response;

public class poetryInsertResponse {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String status;
    String message;

    public poetryInsertResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
