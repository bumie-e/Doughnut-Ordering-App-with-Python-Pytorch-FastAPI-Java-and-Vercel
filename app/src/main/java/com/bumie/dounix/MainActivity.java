package com.bumie.dounix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String baseURL = "http://192.168.137.1:8886/";
    RecyclerView rv, rvspeciality;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    RVAdapter adapter;
    ArrayList<Model> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv1);
        rvspeciality = findViewById(R.id.recyclerView);

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,
                false);
        // setting layout manager for each recyler view
        rvspeciality.setLayoutManager(linearLayoutManager);
        rv.setLayoutManager(gridLayoutManager);
        // creating an empty array list
        arrayList = new ArrayList<Model>();
        getDounts();
        adapter = new RVAdapter(arrayList, MainActivity.this);
        rv.setAdapter(adapter);
        // creating another empty array list
        arrayList = new ArrayList<Model>();
        getSpecialityDoughnut();
        adapter = new RVAdapter(arrayList, MainActivity.this);
        rvspeciality.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Chat.class);
                startActivity(intent);
            }
        });
    }


    public void getDounts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholderApi placeholderApi = retrofit.create(JsonPlaceholderApi.class);
        Call<List<Model>> call = placeholderApi.getDoughnuts();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (!response.isSuccessful()) {
                    Log.i("Call unsuccessful", "Code : " + response.code());
                    Toast.makeText(MainActivity.this, "Code : " + response.code(), Toast.LENGTH_LONG).show();
                }
                Log.i("Call successful", "Code : " + response.code());
                for (int i = 0; i < response.body().size(); i++) {
                    arrayList.add(new Model(response.body().get(i).getName(), response.body().get(i).getImage()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }

    public void getSpecialityDoughnut(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholderApi placeholderApi = retrofit.create(JsonPlaceholderApi.class);
        Call<List<Model>> call = placeholderApi.getSpecialityDoughnut();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (!response.isSuccessful()) {
                    Log.i("Call unsuccessful", "Code : " + response.code());
                    Toast.makeText(MainActivity.this, "Code : " + response.code(), Toast.LENGTH_LONG).show();
                }
                Log.i("Call successful", "Code : " + response.code());
                for (int i = 0; i < response.body().size(); i++) {
                    arrayList.add(new Model(response.body().get(i).getName(), response.body().get(i).getImage()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }
}