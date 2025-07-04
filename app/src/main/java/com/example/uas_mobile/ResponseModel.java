package com.example.uas_mobile;

public class ResponseModel {
    private boolean success;
    private String message;

    private int id;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    // ✅ Tambahkan getter untuk ID
    public int getId() {
        return id;
    }
}
