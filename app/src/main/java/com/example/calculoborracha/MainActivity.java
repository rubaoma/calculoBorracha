package com.example.calculoborracha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chamarTelaCalculoPeso(View view){
        Intent intent = new Intent(this, PesoActivity.class);
        startActivity(intent);
    }

}