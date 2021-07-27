package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class gif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity( new Intent(gif.this,MainActivity.class));
            }
        },3350);
    }
}