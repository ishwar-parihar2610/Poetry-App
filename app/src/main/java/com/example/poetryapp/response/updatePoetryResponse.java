package com.example.poetryapp.response;

public class updatePoetryResponse {
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

    public updatePoetryResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
