package com.example.youtubesearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private YoutubeApiService apiService;

    // ضفت المفتاح الي طلبته منا
    private final String API_KEY = "AIzaSyAEk7F_bbhTFUWxwJXDn5fzxviwCJYk7EY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //وهنا ربطت العناصر بكود الجافا
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //عشان أربط التطبيق بسيرفرات اليوتيوب، استخدمت مكتبة Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(YoutubeApiService.class);

        //لو المستخدم ضغط على الزر والصندوق فارغ، التطبيق مش هيشتغل  بل هتظهر رسالة تنبيهية تطلب منه كتابة كلمة
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                if (!query.isEmpty()) {
                    performSearch(query);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a search word", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

 //، ضفت ميزة. خليت البرنامج يمرر كلمة viewCount للـ API؛  وعيك السيرفر بيرتب الـ 10 فيديوهات تلقائياً من الأعلى مشاهدة للأقل مشاهدة قبل ما يعرضهم
    private void performSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        // نمرر viewCount للترتيب حسب المشاهدات الأعلى
        Call<YoutubeResponse> call = apiService.searchVideos("snippet", query, "video", 10, "viewCount", API_KEY);

        call.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null && response.body().items != null) {
                    videoAdapter = new VideoAdapter(response.body().items);
                    recyclerView.setAdapter(videoAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Search failed or API Key is invalid", Toast.LENGTH_SHORT).show();
                }
            }
//وعملت في دالة الـ onFailure، لو الإنترنت قطع تماماً والتطبيق شغال، الواجهة بتتصرف تلقائياً وبتظهر رسالة Network Error للمستخدم
            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}