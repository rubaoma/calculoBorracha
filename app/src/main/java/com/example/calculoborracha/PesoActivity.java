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

import com.example.calculoborracha.util.Valores;
import com.google.android.material.textfield.TextInputEditText;

public class PesoActivity extends AppCompatActivity {

    private TextInputEditText textDiametroFerro;
    private TextInputEditText textDiametroBorracha;
    private TextInputEditText textComprimentoBorracha;
    //private Button botaoCalcular;
    private TextView textResultado;
    private CheckBox checkCapa;
    private RadioButton radioButtonNitrilica;
    private RadioButton radioButtonEpdm;
    private RadioButton radioButtonX300;

    Valores valores = new Valores();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        //Inicializar os componentes
        textDiametroFerro = findViewById(R.id.textDiametroFerro);
        textDiametroBorracha = findViewById(R.id.textDiametroBorracha);
        textComprimentoBorracha = findViewById(R.id.textComprimentoBorracha);
        textResultado = findViewById(R.id.textViewResultado);
        checkCapa = findViewById(R.id.checkBoxCapa);
        radioButtonNitrilica = findViewById(R.id.radioButtonVlNitrilica);
        radioButtonEpdm = findViewById(R.id.radioButtonEpdm);
        radioButtonX300 = findViewById(R.id.radioButtonX300);


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

            if (radioButtonNitrilica.isChecked()) {

                calculoNitrilica(valorDiamFerro, valorDiamBorracha, valorCompBorracha);
            }
            if (radioButtonEpdm.isChecked()) {

                calculoEPDM(valorDiamFerro, valorDiamBorracha, valorCompBorracha);
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

    public void calculoNitrilica(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;

        if (_diametroBorracha < 135) {
            if (checkCapa.isChecked()) {

                int calculo = (int) (((((_diametroBorracha * _diametroBorracha) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha);
                String resultadoCalculo = String.valueOf(calculo);
                textResultado.setText(valores.pesoFormatado(resultadoCalculo));
            } else {

                int calculo = (int) ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.012) * _comprimentoBorracha);
                String resultadoCalculo = String.valueOf(calculo);
                textResultado.setText(valores.pesoFormatado(resultadoCalculo));
            }
        } else {
            // Diametro da Borracha nitrilica acima de 135 o peso teórico praticamente se iguala ao de uma borracha EDPM
            calculoEPDM(_diametroFerro, _diametroBorracha, _comprimentoBorracha);
        }
    }

    public void calculoEPDM(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;

        if (checkCapa.isChecked()) {

            Double calculo = (((((_diametroBorracha * _diametroBorracha) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.015) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(valores.pesoFormatado(resultadoCalculo));
        } else {

            Double calculo =  ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.015) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(valores.pesoFormatado(resultadoCalculo));
        }
    }


    public void calculoX300(Double diametroFerro, Double diametroBorracha, Double comprimentoBorracha) {
        Double _diametroFerro = diametroFerro;
        Double _diametroBorracha = diametroBorracha;
        Double _comprimentoBorracha = comprimentoBorracha;

        if (checkCapa.isChecked()) {
            Double calculo = (((((_diametroBorracha * _diametroBorracha) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(valores.pesoFormatado(resultadoCalculo));
        } else {

            Double calculo =  ((((((_diametroBorracha + 15) * (_diametroBorracha + 15)) - (_diametroFerro * _diametroFerro)) * 0.0785) * 0.018) * _comprimentoBorracha);
            String resultadoCalculo = String.valueOf(calculo);
            textResultado.setText(valores.pesoFormatado(resultadoCalculo));
        }
    }
    public void limparCampos(View view){
        textDiametroFerro.setText("");
        textDiametroBorracha.setText("");
        textComprimentoBorracha.setText("");
        textResultado.setText("0,00");
        checkCapa.setChecked(false);
        radioButtonNitrilica.setChecked(false);
        radioButtonEpdm.setChecked(false);
        radioButtonX300.setChecked(false);


    }

}