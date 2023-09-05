package com.baishakhee.youtube.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

   
public class Id implements Parcelable {

   @SerializedName("kind")
   String kind;

   @SerializedName("videoId")
   String videoId;


    protected Id(Parcel in) {
        kind = in.readString();
        videoId = in.readString();
    }

    public static final Creator<Id> CREATOR = new Creator<Id>() {
        @Override
        public Id createFromParcel(Parcel in) {
            return new Id(in);
        }

        @Override
        public Id[] newArray(int size) {
            return new Id[size];
        }
    };

    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getKind() {
        return kind;
    }
    
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public String getVideoId() {
        return videoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(videoId);
    }
}