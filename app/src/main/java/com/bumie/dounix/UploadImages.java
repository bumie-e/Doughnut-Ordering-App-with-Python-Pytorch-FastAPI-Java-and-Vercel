package com.bumie.dounix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadImages extends AppCompatActivity {
    ImageView imageView;
    EditText name, price, desc, category;
    private String baseURL = "http://localhost:8000/";
    private static int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);
        imageView = findViewById(R.id.imageView3);
        name = findViewById(R.id.editTextTextPersonName);
        desc = findViewById(R.id.editTextTextPersonName2);
        category = findViewById(R.id.editTextTextPersonName3);
        price = findViewById(R.id.editTextTextPersonName5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    public void uploadImage(){
        try {
            Intent objectIntent = new Intent(Intent.ACTION_GET_CONTENT);
            objectIntent.setType("image/*");

            //objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(UploadImages.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK ) {
                // this is for uploading the image
                // get the image Uri from the intent
                Uri imageUri = data.getData();
                // set the image to the image view
                imageView.setImageURI(imageUri);
                sendData(imageUri.toString(),name.getText().toString(), desc.getText().toString(), category.getText().toString(), price.getText().toString());

        }

    }

    private void sendData(String uri, String name, String desc, String cat, String price){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholderApi placeholderApi = retrofit.create(JsonPlaceholderApi.class);
        Call<Model> call = placeholderApi.addDoughnut(name, uri, desc,price, cat);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UploadImages.this, "Post successfully sent! Code : " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.i("Upload Images","Unable to submit");
            }
        });
    }
}