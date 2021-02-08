package com.bumie.dounix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.pytorch.Module;
import org.pytorch.Tensor;

import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import ca.rmen.porterstemmer.PorterStemmer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chat extends AppCompatActivity {
    private String baseURL = "http://192.168.8.111:8000/";
    Button send;
    EditText edtxt;
    RecyclerView rv;
    ArrayList<MessageModel> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        send = findViewById(R.id.button_chatbox_send);
        edtxt = findViewById(R.id.edittext_chatbox);
        rv = findViewById(R.id.rv);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(edtxt.getText().toString());
            }
        });
    }

    private void sendMessage(String message){
        messagesList.add(new MessageModel(message, CustomAdapter.MESSAGE_TYPE_OUT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholderApi placeholderApi = retrofit.create(JsonPlaceholderApi.class);
        Call<MessageModel> call = placeholderApi.addText(message);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Chat.this, "Post successfully sent! Code : " + response.code(), Toast.LENGTH_LONG).show();
                    messagesList.add(new MessageModel(response.body().getMessage(), CustomAdapter.MESSAGE_TYPE_IN));
                    CustomAdapter adapter = new CustomAdapter(Chat.this, messagesList);
                    rv.setLayoutManager(new LinearLayoutManager(Chat.this));
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.i("Chat","Unable to submit"+t.getMessage());
            }
        });
    }


}