package com.baishakhee.youtube.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

   
public class Thumbnails implements Parcelable {

    @SerializedName("default")
     Default aDefault;

   @SerializedName("medium")
   Medium medium;

   @SerializedName("high")
   High high;


    protected Thumbnails(Parcel in) {
        aDefault = in.readParcelable(Default.class.getClassLoader());
        high = in.readParcelable(High.class.getClassLoader());
        medium = in.readParcelable(Medium.class.getClassLoader());

    }

    public static final Creator<Thumbnails> CREATOR = new Creator<Thumbnails>() {
        @Override
        public Thumbnails createFromParcel(Parcel in) {
            return new Thumbnails(in);
        }

        @Override
        public Thumbnails[] newArray(int size) {
            return new Thumbnails[size];
        }
    };

    public Default getaDefault() {
        return aDefault;
    }

    public void setaDefault(Default aDefault) {
        this.aDefault = aDefault;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
    public Medium getMedium() {
        return medium;
    }
    
    public void setHigh(High high) {
        this.high = high;
    }
    public High getHigh() {
        return high;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) aDefault, flags);
        dest.writeParcelable((Parcelable) high, flags);
        dest.writeParcelable((Parcelable) medium, flags);

    }
}