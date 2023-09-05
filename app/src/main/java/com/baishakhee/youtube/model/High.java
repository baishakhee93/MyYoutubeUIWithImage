package com.baishakhee.youtube.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

   
public class High implements Parcelable {

   @SerializedName("url")
   String url;

   @SerializedName("width")
   int width;

   @SerializedName("height")
   int height;


    protected High(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<High> CREATOR = new Creator<High>() {
        @Override
        public High createFromParcel(Parcel in) {
            return new High(in);
        }

        @Override
        public High[] newArray(int size) {
            return new High[size];
        }
    };

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    public int getWidth() {
        return width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}