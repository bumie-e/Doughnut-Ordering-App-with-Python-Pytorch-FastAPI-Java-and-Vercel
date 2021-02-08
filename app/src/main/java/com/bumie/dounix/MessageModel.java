package com.bumie.dounix;

import java.util.Date;

public class MessageModel {

    public String message;
    public int messageType;
    public Date messageTime = new Date();
    // Constructor
    public MessageModel(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public MessageModel(String message) {
        this.message = message;
    }
}