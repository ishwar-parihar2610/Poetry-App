package com.example.poetryapp.response;

public class deleteResponse {
    String status;
    String message;

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

    public deleteResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
