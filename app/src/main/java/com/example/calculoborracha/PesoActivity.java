package com.example.calculoborracha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class PesoActivity extends AppCompatActivity {

    private TextInputEditText textDiametroFerro;
    private TextInputEditText textDiametroBorracha;
    private TextInputEditText textComprimentoBorracha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        //Inicializar os componentes
        textDiametroFerro = findViewById(R.id.textDiametroFerro);
        textDiametroBorracha = findViewById(R.id.textDiametroBorracha);
        textComprimentoBorracha = findViewById(R.id.textComprimentoBorracha);



    }
    public void chamarTelaInicial(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void calcularPesoBorracha(){

    }
}