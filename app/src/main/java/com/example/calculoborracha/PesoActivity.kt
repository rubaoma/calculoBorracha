package com.example.calculoborracha

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.calculoborracha.util.Valores
import com.google.android.material.textfield.TextInputEditText

class PesoActivity : AppCompatActivity() {
    private var textDiametroFerro: TextInputEditText? = null
    private var textDiametroBorracha: TextInputEditText? = null
    private var textComprimentoBorracha: TextInputEditText? = null

    private var textResultado: TextView? = null
    private var checkCapa: CheckBox? = null
    private var radioButtonNitrilica: RadioButton? = null
    private var radioButtonEpdm: RadioButton? = null
    private var radioButtonX300: RadioButton? = null
    var valores = Valores()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peso)

        //Inicializar os componentes
        textDiametroFerro = findViewById(R.id.textDiametroFerro)
        textDiametroBorracha = findViewById(R.id.textDiametroBorracha)
        textComprimentoBorracha = findViewById(R.id.textComprimentoBorracha)
        textResultado = findViewById(R.id.textViewResultado)
        checkCapa = findViewById(R.id.checkBoxCapa)
        radioButtonNitrilica = findViewById(R.id.radioButtonVlNitrilica)
        radioButtonEpdm = findViewById(R.id.radioButtonEpdm)
        radioButtonX300 = findViewById(R.id.radioButtonX300)
    }

    fun chamarTelaInicial(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun calcularPesoBorracha(view: View?) {
        val diamFerro = textDiametroFerro!!.text.toString()
        val diamBorracha = textDiametroBorracha!!.text.toString()
        val compBorracha = textComprimentoBorracha!!.text.toString()
        val camposValidados = validarCampos(diamFerro, diamBorracha, compBorracha)
        if (camposValidados) {
            val valorDiamFerro = diamFerro.toDouble()
            val valorDiamBorracha = diamBorracha.toDouble()
            val valorCompBorracha = compBorracha.toDouble()
            if (radioButtonNitrilica!!.isChecked) {
                calculoNitrilica(valorDiamFerro, valorDiamBorracha, valorCompBorracha)
            }
            if (radioButtonEpdm!!.isChecked) {
                calculoEPDM(valorDiamFerro, valorDiamBorracha, valorCompBorracha)
            }
            if (radioButtonX300!!.isChecked) {
                calculoX300(valorDiamFerro, valorDiamBorracha, valorCompBorracha)
                //
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    "Preencha todos os valores !!",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun validarCampos(pDiamferro: String?, pDiamBorracha: String?, pCompBorracha: String?): Boolean {
        var camposValidados = true
        if (pDiamferro == null || pDiamferro == "") {
            camposValidados = false
        } else if (pDiamBorracha == null || pDiamBorracha == "") {
            camposValidados = false
        } else if (pCompBorracha == null || pCompBorracha == "") {
            camposValidados = false
        }
        return camposValidados
    }

    fun calculoNitrilica(diametroFerro: Double, diametroBorracha: Double, comprimentoBorracha: Double) {
        if (diametroBorracha < 135) {
            if (checkCapa!!.isChecked) {
                val calculo = ((diametroBorracha * diametroBorracha -
                        diametroFerro * diametroFerro) * 0.0785
                        * 0.012) * comprimentoBorracha
                val calcFormat = valores.pesoFormatado(calculo)
                textResultado!!.text = calcFormat
            } else {
                val calculo = ((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.012 * comprimentoBorracha
                val calcFormat = valores.pesoFormatado(calculo)
                textResultado!!.text = calcFormat
            }
        } else {
            // Diametro da Borracha nitrilica acima de 135 o peso teórico praticamente se iguala ao de uma borracha EDPM
            calculoEPDM(diametroFerro, diametroBorracha, comprimentoBorracha)
        }
    }

    fun calculoEPDM(diametroFerro: Double, diametroBorracha: Double, comprimentoBorracha: Double) {
        if (checkCapa!!.isChecked) {
            val calculo = (diametroBorracha * diametroBorracha - diametroFerro * diametroFerro) * 0.0785 * 0.015 * comprimentoBorracha
            val calcFormat = valores.pesoFormatado(calculo)
            textResultado!!.text = calcFormat
        } else {
            val calculo = ((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.015 * comprimentoBorracha
            val calcFormat = valores.pesoFormatado(calculo)
            textResultado!!.text = calcFormat
        }
    }

    fun calculoX300(diametroFerro: Double, diametroBorracha: Double, comprimentoBorracha: Double) {
        if (checkCapa!!.isChecked) {
            val calculo = (diametroBorracha * diametroBorracha - diametroFerro * diametroFerro) * 0.0785 * 0.018 * comprimentoBorracha
            val calcFormat = valores.pesoFormatado(calculo)
            textResultado!!.text = calcFormat
        } else {
            val calculo = ((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.018 * comprimentoBorracha
            val calcFormat = valores.pesoFormatado(calculo)
            textResultado!!.text = calcFormat
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun limparCampos(view: View?) {
        textDiametroFerro!!.setText("")
        textDiametroBorracha!!.setText("")
        textComprimentoBorracha!!.setText("")
        textResultado!!.text = "R.string.valor_padrao"
        checkCapa!!.isChecked = false
        radioButtonNitrilica!!.isChecked = false
        radioButtonEpdm!!.isChecked = false
        radioButtonX300!!.isChecked = false
        textDiametroFerro!!.focusable
    }
}