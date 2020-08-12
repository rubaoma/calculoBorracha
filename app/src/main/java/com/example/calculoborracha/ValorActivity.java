package com.example.calculoborracha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ValorActivity extends AppCompatActivity {

    private TextInputEditText textDiametroFerro;
    private TextInputEditText textDiametroBorracha;
    private TextInputEditText textComprimentoBorracha;
    private TextInputEditText textPrecoQuilo;
    //private Button botaoCalcular;
    private TextView textResultado;
    private RadioButton radioCanal;
    private RadioButton radioRetifica;
    private RadioButton radioButtonNitrilica;
    private RadioButton radioButtonEpdm;
    private RadioButton radioButtonX300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valor);

        //Inicializar os componentes
        textDiametroFerro = findViewById(R.id.textInputVlDiametroFerro);
        textDiametroBorracha = findViewById(R.id.textInputVlDiametroBorracha);
        textComprimentoBorracha = findViewById(R.id.textInputVlComprimentoBorracha);
        textPrecoQuilo = findViewById(R.id.textInputPrecoQuilo);
        textResultado = findViewById(R.id.textViewVlResultadoPreco);
        radioCanal = findViewById(R.id.radioButtonCanais);
        radioRetifica = findViewById(R.id.radioButtonRetifica);
        radioButtonNitrilica = findViewById(R.id.radioButtonVlNitrilica);
        radioButtonEpdm = findViewById(R.id.radioButtonVlPrecoEPDM);
        radioButtonX300 = findViewById(R.id.radioButtonVlPrecoX300);
    }

    public void chamarTelaInicial(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void calcularPrecoBorracha(View view) {

        String diamFerro = textDiametroFerro.getText().toString();
        String diamBorracha = textDiametroBorracha.getText().toString();
        String compBorracha = textComprimentoBorracha.getText().toString();
        String precoQuilo = textPrecoQuilo.getText().toString();
        Boolean camposValidados = validarCampos(diamFerro, diamBorracha, compBorracha, precoQuilo);

        if (camposValidados) {
            Double valorDiamFerro = Double.parseDouble(diamFerro);
            Double valorDiamBorracha = Double.parseDouble(diamBorracha);
            Double valorCompBorracha = Double.parseDouble(compBorracha);
            Integer valorPrecoQuilo = Integer.parseInt(precoQuilo);

            if (radioButtonNitrilica.isChecked()) {

                calculoPrecoNitrilica(valorDiamFerro, valorDiamBorracha, valorCompBorracha, valorPrecoQuilo);
            }
            if (radioButtonEpdm.isChecked()) {

                calculoPrecoEPDM(valorDiamFerro, valorDiamBorracha, valorCompBorracha, valorPrecoQuilo);
            }

            if (radioButtonX300.isChecked()) {
                calculoX300(valorDiamFerro, valorDiamBorracha, valorCompBorracha);
//
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Preencha todos os valores !!",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    public Boolean validarCampos(String pDiamferro, String pDiamBorracha, String pCompBorracha, String pValorQuilo) {
        Boolean camposValidados = true;

        if (pDiamferro == null || pDiamferro.equals("")) {
            camposValidados = false;
        } else if (pDiamBorracha == null || pDiamBorracha.equals("")) {
            camposValidados = false;
        } else if (pCompBorracha == null || pCompBorracha.equals("")) {
            camposValidados = false;

        } else if (pValorQuilo == null || pValorQuilo.equals("")){
            camposValidados = false;
        }

        return camposValidados;
    }

    public void calculoPrecoNitrilica(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha, int valorBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;
        int _valorBorracha = valorBorracha;

        if (_diametroBorracha < 135) {
            if (radioCanal.isChecked()) {
                //adiciona 30% a mais no valor principal
                int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha) * _valorBorracha;
                Double calculoTotal = ((calculo * 0.30) + calculo)/10;
                String resultadoCalculo = String.valueOf(calculoTotal);
                textResultado.setText(resultadoCalculo);
            } else {

                int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha);
                int calculoTotal = (int) (calculo * _valorBorracha)/10;
                String resultadoCalculo = String.valueOf(calculoTotal);
                textResultado.setText(resultadoCalculo);
            }
        } else {
            // Diametro da Borracha nitrilica acima de 135 o peso teórico praticamente se iguala ao de uma borracha EDPM
            calculoPrecoEPDM(_diametroFerro, _diametroBorracha, _comprimentoBorracha, _valorBorracha);
        }
    }

    public void calculoPrecoEPDM(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha, int valorBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;
        int _valorBorracha = valorBorracha;

        if (radioCanal.isChecked()) {

            int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.015) * _comprimentoBorracha) * _valorBorracha;
            Double calculoTotal = ((calculo * 0.30) + calculo)/10;
            String resultadoCalculo = String.valueOf(calculoTotal);
            textResultado.setText(resultadoCalculo);
        } else {

            int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.015) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(resultadoCalculo);
        }
    }


    public void calculoX300(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;

        if (radioCanal.isChecked()) {
            int calculo = (int) (((((_diametroBorracha * _diametroBorracha) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(resultadoCalculo);
        } else {

            int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(resultadoCalculo);
        }
    }



}