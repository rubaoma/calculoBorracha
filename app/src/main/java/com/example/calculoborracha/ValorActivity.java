package com.example.calculoborracha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ValorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valor);
    }

    public void chamarTelaInicial(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}