package com.baishakhee.youtube.model;

import com.google.gson.annotations.SerializedName;

   
public class PageInfo {

   @SerializedName("totalResults")
   int totalResults;

   @SerializedName("resultsPerPage")
   int resultsPerPage;


    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    public int getTotalResults() {
        return totalResults;
    }
    
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
    public int getResultsPerPage() {
        return resultsPerPage;
    }
    
}