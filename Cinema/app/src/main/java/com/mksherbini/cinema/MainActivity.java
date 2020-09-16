package com.mksherbini.cinema;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mksherbini.cinema.MVCModel.TDBResult;
import com.mksherbini.cinema.MVCModel.TheMovieDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ITheMovieDBApi api = retrofit.create(ITheMovieDBApi.class);

        Call<TheMovieDB> caller = api.GetDefault();

        caller.enqueue(new Callback<TheMovieDB>() {
            @Override
            public void onResponse(Call<TheMovieDB> call, Response<TheMovieDB> response) {
                Log.d(TAG, "onResponse: " + response.code());

                if (response.isSuccessful()) {
//                    mTextView.setText(response.body().getTDBResults().get(0).getOriginalTitle());

                    InitRecyclerView(response.body().getTDBResults());

                }
            }

            @Override
            public void onFailure(Call<TheMovieDB> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }


    private void InitRecyclerView(List<TDBResult> list) {
        Log.d(TAG, "InitRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
