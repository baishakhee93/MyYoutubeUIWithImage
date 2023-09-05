package com.baishakhee.youtube.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

   
public class Items  implements Parcelable {

   @SerializedName("kind")
   String kind;

   @SerializedName("etag")
   String etag;

   @SerializedName("id")
   Id id;

   @SerializedName("snippet")
   Snippet snippet;




    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getKind() {
        return kind;
    }
    
    public void setEtag(String etag) {
        this.etag = etag;
    }
    public String getEtag() {
        return etag;
    }
    
    public void setId(Id id) {
        this.id = id;
    }
    public Id getId() {
        return id;
    }
    
    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
    public Snippet getSnippet() {
        return snippet;
    }
    protected Items(Parcel in) {
        // Read data from the parcel and assign to fields
        kind = in.readString();
        etag = in.readString();
        id = in.readParcelable(Id.class.getClassLoader());
        snippet = in.readParcelable(Snippet.class.getClassLoader());
    }



    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(etag);
        dest.writeParcelable((Parcelable) id, flags);
        dest.writeParcelable((Parcelable) snippet, flags);
    }
}