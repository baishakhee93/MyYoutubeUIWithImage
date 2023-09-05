package com.baishakhee.youtube.api;

import com.baishakhee.youtube.model.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import io.reactivex.rxjava3.core.Observable;

public interface APIInterface {
    @GET("search")
    Observable<MainModel> searchVideos(
            @Query("part") String part,
            @Query("maxResults") int maxResults,
            @Query("channelId") String channelId,
            @Query("key") String apiKey
    );
}

