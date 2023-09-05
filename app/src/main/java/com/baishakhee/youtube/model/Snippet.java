package com.baishakhee.youtube.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

   
public class Snippet implements Parcelable {

   @SerializedName("publishedAt")
   Date publishedAt;

   @SerializedName("channelId")
   String channelId;

   @SerializedName("title")
   String title;

   @SerializedName("description")
   String description;

   @SerializedName("thumbnails")
   Thumbnails thumbnails;

   @SerializedName("channelTitle")
   String channelTitle;

   @SerializedName("liveBroadcastContent")
   String liveBroadcastContent;

   @SerializedName("publishTime")
   String publishTime;


    protected Snippet(Parcel in) {
        channelId = in.readString();
        title = in.readString();
        description = in.readString();
        channelTitle = in.readString();
        liveBroadcastContent = in.readString();
        publishTime = in.readString();
        thumbnails = in.readParcelable(Thumbnails.class.getClassLoader());

    }

    public static final Creator<Snippet> CREATOR = new Creator<Snippet>() {
        @Override
        public Snippet createFromParcel(Parcel in) {
            return new Snippet(in);
        }

        @Override
        public Snippet[] newArray(int size) {
            return new Snippet[size];
        }
    };

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    public Date getPublishedAt() {
        return publishedAt;
    }
    
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getChannelId() {
        return channelId;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    
    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
    public Thumbnails getThumbnails() {
        return thumbnails;
    }
    
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
    public String getChannelTitle() {
        return channelTitle;
    }
    
    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }
    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(channelId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(channelTitle);
        dest.writeString(liveBroadcastContent);
        dest.writeString(publishTime);
        dest.writeParcelable((Parcelable) thumbnails, flags);

    }
}