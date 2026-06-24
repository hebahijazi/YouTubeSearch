package com.example.youtubesearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// هنا الـ Interface اللي ضفيت فيه الرابط والمتغيرات ونوع النتائج
public interface YoutubeApiService {
    @GET("youtube/v3/search")
    Call<YoutubeResponse> searchVideos(
            @Query("part") String part,
            @Query("q") String searchQuery,
            @Query("type") String type,
            @Query("maxResults") int maxResults,
            @Query("order") String order,// سطر لترتيب الفيديوهات من اعلى مشاهدة لاقل مشاهدة
            @Query("key") String apiKey
    );
}