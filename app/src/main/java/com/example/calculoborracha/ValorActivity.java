package com.example.calculoborracha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculoborracha.util.Valores;
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

    Valores valores = new Valores();


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
                calculoX300(valorDiamFerro, valorDiamBorracha, valorCompBorracha, valorPrecoQuilo);
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
        boolean camposValidados = true;

        if (pDiamferro == null || pDiamferro.equals("")) {
            camposValidados = false;
        } else if (pDiamBorracha == null || pDiamBorracha.equals("")) {
            camposValidados = false;
        } else if (pCompBorracha == null || pCompBorracha.equals("")) {
            camposValidados = false;

        } else if (pValorQuilo == null || pValorQuilo.equals("")) {
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
                double calculo = ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha) * _valorBorracha;
                double calculoTotal = ((calculo * 0.30) + calculo) / 1000;
                String calcFormat = valores.valorFormatado2(calculoTotal);
                String resultadoCalculo = String.valueOf(calcFormat);
                textResultado.setText("R$ "+calcFormat+",00");

            } else if (radioRetifica.isChecked()) {
                //retifica equivale a 30% do valor total de um revestimento
                int calculo = (int)((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha) * _valorBorracha;
                double calculoTotal = (calculo * 0.30) / 1000;
                String calcFormat = valores.valorFormatado(calculoTotal);
                textResultado.setText("R$ "+calcFormat+",00");

            } else {
                int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha);
                int calculoTotal = (int) (calculo * _valorBorracha) / 1000;
                String calcFormat = valores.valorFormatado(calculoTotal);
                String resultadoCalculo = String.valueOf(calcFormat);
                textResultado.setText("R$ "+calcFormat+",00");
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
            double calculoTotal = ((calculo * 0.30) + calculo) / 1000;
            String calcFormat = valores.valorFormatado2(calculoTotal);
            textResultado.setText("R$ "+calcFormat+",00");

        } else if (radioRetifica.isChecked()) {
            //retifica equivale a 30% do valor total de um revestimento
            Double calculo = ((((((_diametroBorracha + 15) * (_diametroBorracha + 15))- (_diametroFerro * _diametroFerro)) * 0.0785) * 0.015) * _comprimentoBorracha) * _valorBorracha;
            double calculoTotal = (calculo * 0.30) / 10;
            String calcFormat = valores.pesoFormatado(calculoTotal);
            String resultadoCalculo = String.valueOf(calcFormat);
            textResultado.setText(resultadoCalculo);

        } else {

            Double calculo = ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.015) * _comprimentoBorracha);
            int calculoTotal = (int) (calculo * _valorBorracha) / 1000;
            String calcFormat = valores.valorFormatado(calculoTotal);
            String resultadoCalculo = String.valueOf(calcFormat);
            textResultado.setText("R$ "+calcFormat+",00");
        }
    }


    public void calculoX300(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha, int valorBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;
        int _valorBorracha = valorBorracha;

        if (radioCanal.isChecked()) {

            int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha) * _valorBorracha;
            double calculoTotal = ((calculo * 0.30) + calculo) / 1000;
            String calcFormat = valores.valorFormatado2(calculoTotal);
            textResultado.setText("R$ "+calcFormat+",00");

        } else if (radioRetifica.isChecked()) {
            //retifica equivale a 30% do valor total de um revestimento
            int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha) * _valorBorracha;
            Double calculoTotal = (calculo * 0.30) / 10;
            String resultadoCalculo = String.valueOf(calculoTotal);
//            textResultado.setText(valoresFormatado.valorFormatado(resultadoCalculo));

        } else {

            int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha);
            int calculoTotal = (int) (calculo * _valorBorracha) / 1000;
            String calcFormat = valores.valorFormatado(calculoTotal);
            String resultadoCalculo = String.valueOf(calcFormat);
            textResultado.setText("R$ "+calcFormat+",00");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void limparCampos(View view) {
        textDiametroFerro.setText("");
        textDiametroBorracha.setText("");
        textComprimentoBorracha.setText("");
        textPrecoQuilo.setText("");
        textResultado.setText("0,00");
        radioRetifica.setChecked(false);
        radioCanal.setChecked(false);
        radioButtonNitrilica.setChecked(false);
        radioButtonEpdm.setChecked(false);
        radioButtonX300.setChecked(false);



    }


}