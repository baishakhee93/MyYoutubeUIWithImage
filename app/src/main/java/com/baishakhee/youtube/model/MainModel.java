package com.baishakhee.youtube.model;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class MainModel{

   @SerializedName("kind")
   String kind;

   @SerializedName("etag")
   String etag;

   @SerializedName("nextPageToken")
   String nextPageToken;

   @SerializedName("regionCode")
   String regionCode;

   @SerializedName("pageInfo")
   PageInfo pageInfo;

   @SerializedName("items")
   List<Items> items;


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
    
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
    public String getNextPageToken() {
        return nextPageToken;
    }
    
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    public String getRegionCode() {
        return regionCode;
    }
    
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    
    public void setItems(List<Items> items) {
        this.items = items;
    }
    public List<Items> getItems() {
        return items;
    }
    
}