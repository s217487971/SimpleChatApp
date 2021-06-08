package com.example.simplechatapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class contact implements Parcelable, Serializable {
    String ID; //Name
    String lastText; //Number
    String imgPath; //Profile Picture Saved During Contact Saving
    String Number;

    public String getImagePath() {
        return imgPath;
    }

    public void setImagePath(String imagePath) {
        this.imgPath = imagePath;
    }

    public contact()
    {
        ID = "";
        lastText = "";
    }
    public contact(String ID, String lastText, String imgPath)
    {
        this.ID = ID;
        this.lastText = lastText;
        this.imgPath = imgPath;

        if(ID.equals(""))
        {
            ID = lastText;
        }
        char[] chars = ID.toCharArray();
        StringBuilder sb = new StringBuilder();
            // checks whether the character is not a letter
            // if it is not a letter ,it will return false
            if ((Character.isDigit(chars[0]))) {
                Number = lastText;
            }

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getImgResource() {
        return imgPath;
    }

    public void setImgResource(String imgResource) {
        this.imgPath = imgPath;
    }

    protected contact(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);

        this.ID = data[0];
        this.lastText = data[1];
        this.imgPath = data[2];
    }

    public static final Creator<contact> CREATOR = new Creator<contact>() {
        @Override
        public contact createFromParcel(Parcel in) {
            return new contact(in);
        }

        @Override
        public contact[] newArray(int size) {
            return new contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.ID,
        this.lastText,this.imgPath});
    }

}
