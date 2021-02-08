package com.bumie.dounix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Item extends AppCompatActivity {
    private String baseURL = "http://localhost:8000/";
    ImageView image;
    RecyclerView recyclerView;
    TextView title, description, add, subtract, number, price, category;
    Button buy;
    LinearLayoutManager layoutManager;
    ViewAdapter adapter;
    ArrayList<Model> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        image = findViewById(R.id.imageView9);
        recyclerView = findViewById(R.id.recyclerView);
        title = findViewById(R.id.textView4);
        description = findViewById(R.id.textView5);
        price = findViewById(R.id.textView15);
        number = findViewById(R.id.textView13);
        add = findViewById(R.id.textView6);
        subtract = findViewById(R.id.textView7);;
        category = findViewById(R.id.textView14);
        buy = findViewById(R.id.button1);

        layoutManager = new LinearLayoutManager(Item.this, LinearLayoutManager.HORIZONTAL,
                false);
        // setting layout manager for each recyler view
        recyclerView.setLayoutManager(layoutManager);
        // creating an empty array list
        arrayList = new ArrayList<Model>();
        getDount();
        adapter = new ViewAdapter(arrayList, Item.this);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduce();
            }
        });

    }

    public void increase(){
        int i = Integer.parseInt(number.getText().toString());
        i++;
        number.setText(String.valueOf(i));
    }

    public void reduce(){
        int i = Integer.parseInt(number.getText().toString());
        i--;
        number.setText(String.valueOf(i));
    }

    private void getDount(){
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
                    Toast.makeText(Item.this, "Code : " + response.code(), Toast.LENGTH_LONG).show();
                }
                Log.i("Call successful", "Code : " + response.code());
                for (int i = 0; i < response.body().size(); i++) {
                    arrayList.add(new Model(response.body().get(i).getImage()));
                }
                adapter.notifyDataSetChanged();

                // using the position of that particular image to get image info from the adapter class
                int position = getIntent().getIntExtra("Position", 0);

                title.setText(response.body().get(position).getName());
                description.setText(response.body().get(position).getDescription());
                price.setText(response.body().get(position).getPrice());
                category.setText(response.body().get(position).getCategory());
                String uri = response.body().get(position).getImage();
                Uri imgUri = Uri.parse(uri);
                image.setImageURI(imgUri);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }
}