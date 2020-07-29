package com.example.calculoborracha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PesoActivity extends AppCompatActivity {

    private TextInputEditText textDiametroFerro;
    private TextInputEditText textDiametroBorracha;
    private TextInputEditText textComprimentoBorracha;
    private Button botaoCalcular;
    private TextView textResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        //Inicializar os componentes
        textDiametroFerro = findViewById(R.id.textDiametroFerro);
        textDiametroBorracha = findViewById(R.id.textDiametroBorracha);
        textComprimentoBorracha = findViewById(R.id.textComprimentoBorracha);
        textResultado = findViewById(R.id.textViewResultado);


    }

    public void chamarTelaInicial(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void calcularPesoBorracha(View view) {

        String diamFerro = textDiametroFerro.getText().toString();
        String diamBorracha = textDiametroBorracha.getText().toString();
        String compBorracha = textComprimentoBorracha.getText().toString();
        Boolean camposValidados = validarCampos(diamFerro, diamBorracha, compBorracha);

        if (camposValidados) {
            Double valorDiamFerro = Double.parseDouble(diamFerro);
            Double valorDiamBorracha = Double.parseDouble(diamBorracha);
            Double valorCompBorracha = Double.parseDouble(compBorracha);

            Double calculo = ((((((valorDiamBorracha + 15) * (valorDiamBorracha + 15)) - (valorDiamFerro * 2)) * 0.0785) * 0.015) * valorCompBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(resultadoCalculo);

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Preencha todos os valores !!",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    public Boolean validarCampos(String pDiamferro, String pDiamBorracha, String pCompBorracha) {
        Boolean camposValidados = true;

        if (pDiamferro == null || pDiamferro.equals("")) {
            camposValidados = false;
        } else if (pDiamBorracha == null || pDiamBorracha.equals("")) {
            camposValidados = false;
        } else if (pCompBorracha == null || pCompBorracha.equals("")) {
            camposValidados = false;
        }

        return camposValidados;
    }
}