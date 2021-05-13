package com.example.simplechatapp;

import android.os.Parcel;
import android.os.Parcelable;

public class contact implements Parcelable {
    String ID;
    String lastText;
    String imgPath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    String imagePath;

    public contact()
    {
        ID = "";
        lastText = "";
    }
    public contact(String ID, String lastText, String imgPath)
    {
        this.ID = ID;
        this.lastText = lastText;
        this.imagePath = imgPath;
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
        return imagePath;
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
