package com.example.simplechatapp.ui.chat;

public class message {
    String sender;
    String reciver;
    String content;
    String timeStamp;
    boolean read;
    boolean deleted;
    boolean OutBox;
    boolean dateText;

    public message(String sender, String reciver, String content, String timeStamp, boolean OutBox)
    {
        this.sender = sender;
        this.reciver = reciver;
        this.content = content;
        this.timeStamp = timeStamp;
        read = false;
        deleted = false;
        this.OutBox = OutBox;

    }
    public message(boolean dateText)
    {
        this.dateText = dateText;
    }
    public void setContent(String content)
    {
        if(dateText)
        {
            this.content = content;
        }
    }
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public String getContent()
    {
        return content;
    }
}
