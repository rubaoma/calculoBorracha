package com.example.calculoborracha

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.calculoborracha.util.Valores
import com.google.android.material.textfield.TextInputEditText

class ValorActivity : AppCompatActivity() {
    private var textDiametroFerro: TextInputEditText? = null
    private var textDiametroBorracha: TextInputEditText? = null
    private var textComprimentoBorracha: TextInputEditText? = null
    private var textPrecoQuilo: TextInputEditText? = null
    private var textResultado: TextView? = null
    private var radioCanal: RadioButton? = null
    private var radioRetifica: RadioButton? = null
    private var radioButtonNitrilica: RadioButton? = null
    private var radioButtonEpdm: RadioButton? = null
    private var radioButtonX300: RadioButton? = null
    private val valores = Valores()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valor)

        //Inicializar os componentes
        textDiametroFerro = findViewById(R.id.textInputVlDiametroFerro)
        textDiametroBorracha = findViewById(R.id.textInputVlDiametroBorracha)
        textComprimentoBorracha = findViewById(R.id.textInputVlComprimentoBorracha)
        textPrecoQuilo = findViewById(R.id.textInputPrecoQuilo)
        textResultado = findViewById(R.id.textViewVlResultadoPreco)
        radioCanal = findViewById(R.id.radioButtonCanais)
        radioRetifica = findViewById(R.id.radioButtonRetifica)
        radioButtonNitrilica = findViewById(R.id.radioButtonVlNitrilica)
        radioButtonEpdm = findViewById(R.id.radioButtonVlPrecoEPDM)
        radioButtonX300 = findViewById(R.id.radioButtonVlPrecoX300)
    }

    fun chamarTelaInicial(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun calcularPrecoBorracha(view: View?) {
        val diamFerro = textDiametroFerro!!.text.toString()
        val diamBorracha = textDiametroBorracha!!.text.toString()
        val compBorracha = textComprimentoBorracha!!.text.toString()
        val precoQuilo = textPrecoQuilo!!.text.toString()
        val camposValidados = validarCampos(diamFerro, diamBorracha, compBorracha, precoQuilo)
        if (camposValidados) {
            val valorDiamFerro = diamFerro.toDouble()
            val valorDiamBorracha = diamBorracha.toDouble()
            val valorCompBorracha = compBorracha.toDouble()
            val valorPrecoQuilo = precoQuilo.toInt()
            if (radioButtonNitrilica!!.isChecked) {
                calculoPrecoNitrilica(valorDiamFerro, valorDiamBorracha, valorCompBorracha, valorPrecoQuilo)
            }
            if (radioButtonEpdm!!.isChecked) {
                calculoPrecoEPDM(valorDiamFerro, valorDiamBorracha, valorCompBorracha, valorPrecoQuilo)
            }
            if (radioButtonX300!!.isChecked) {
                calculoX300(valorDiamFerro, valorDiamBorracha, valorCompBorracha, valorPrecoQuilo)
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

    fun validarCampos(pDiamferro: String?, pDiamBorracha: String?, pCompBorracha: String?, pValorQuilo: String?): Boolean {
        var camposValidados = true
        if (pDiamferro == null || pDiamferro == "") {
            camposValidados = false
        } else if (pDiamBorracha == null || pDiamBorracha == "") {
            camposValidados = false
        } else if (pCompBorracha == null || pCompBorracha == "") {
            camposValidados = false
        } else if (pValorQuilo == null || pValorQuilo == "") {
            camposValidados = false
        }
        return camposValidados
    }

    fun calculoPrecoNitrilica(diametroFerro: Double, diametroBorracha: Double, comprimentoBorracha: Double, valorBorracha: Int) {
        if (diametroBorracha < 135) {
            if (radioCanal!!.isChecked) {
                //adiciona 30% a mais no valor principal
                val calculo = ((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.012 * comprimentoBorracha * valorBorracha
                val calculoTotal = (calculo * 0.30 + calculo) / 1000
                val calcFormat = valores.valorFormatado2(calculoTotal)
                val resultadoCalculo = calcFormat
                textResultado!!.text = "R$ $calcFormat,00"
            } else if (radioRetifica!!.isChecked) {
                /**
                 * buscar uma solução para o valor quebrado
                 */

                //retifica equivale a 30% do valor total de um revestimento
                val calculo = (((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.012 * comprimentoBorracha).toInt() * valorBorracha
                val calculoTotal = calculo * 0.30 / 1000
                val calcFormat = valores.valorFormatado(calculoTotal)
                textResultado!!.text = "R$ $calcFormat,00"
            } else {
                val calculo = (((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.012 * comprimentoBorracha).toInt()
                val calculoTotal = (calculo * valorBorracha) / 1000
                val calcFormat = valores.valorFormatado(calculoTotal.toDouble())
                val resultadoCalculo = calcFormat
                textResultado!!.text = "R$ $calcFormat,00"
            }
        } else {
            // Diametro da Borracha nitrilica acima de 135 o peso teórico praticamente se iguala ao de uma borracha EDPM
            calculoPrecoEPDM(diametroFerro,
                    diametroBorracha,
                    comprimentoBorracha,
                    valorBorracha)
        }
    }

    fun calculoPrecoEPDM(diametroFerro: Double, diametroBorracha: Double, comprimentoBorracha: Double, valorBorracha: Int) {
        if (radioCanal!!.isChecked) {
            val calculo = (((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.015 * comprimentoBorracha).toInt() * valorBorracha
            val calculoTotal = (calculo * 0.30 + calculo) / 1000
            val calcFormat = valores.valorFormatado2(calculoTotal)
            textResultado!!.text = "R$ $calcFormat,00"
        } else if (radioRetifica!!.isChecked) {
            /**
             * buscar uma solução para o valor quebrado
             */
            //retifica equivale a 30% do valor total de um revestimento
            val calculo = ((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.015 * comprimentoBorracha * valorBorracha
            val calculoTotal = calculo * 0.30 / 10
            val calcFormat = valores.pesoFormatado(calculoTotal)
            textResultado!!.text = calcFormat
        } else {
            val calculo = ((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.015 * comprimentoBorracha
            val calculoTotal = (calculo * valorBorracha).toInt() / 1000
            val calcFormat = valores.valorFormatado(calculoTotal.toDouble())
            val resultadoCalculo = calcFormat
            textResultado!!.text = "R$ $calcFormat,00"
        }
    }

    fun calculoX300(diametroFerro: Double, diametroBorracha: Double, comprimentoBorracha: Double, valorBorracha: Int) {
        if (radioCanal!!.isChecked) {
            val calculo = (((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.018 * comprimentoBorracha).toInt() * valorBorracha
            val calculoTotal = (calculo * 0.30 + calculo) / 1000
            val calcFormat = valores.valorFormatado2(calculoTotal)
            textResultado!!.text = "R$ $calcFormat,00"
        } else if (radioRetifica!!.isChecked) {
            /**
             * buscar uma solução para o valor quebrado
             */
            //retifica equivale a 30% do valor total de um revestimento
            val calculo = (((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.018 * comprimentoBorracha).toInt() * valorBorracha
            val calculoTotal = calculo * 0.30 / 10
            val resultadoCalculo = calculoTotal.toString()
            //            textResultado.setText(valoresFormatado.valorFormatado(resultadoCalculo));
        } else {
            val calculo = (((diametroBorracha + 15) * (diametroBorracha + 15) - diametroFerro * diametroFerro) * 0.0785 * 0.018 * comprimentoBorracha).toInt()
            val calculoTotal = (calculo * valorBorracha) / 1000
            val calcFormat = valores.valorFormatado(calculoTotal.toDouble())
            val resultadoCalculo = calcFormat
            textResultado!!.text = "R$ $calcFormat,00"
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun limparCampos(view: View?) {
        textDiametroFerro!!.setText("")
        textDiametroBorracha!!.setText("")
        textComprimentoBorracha!!.setText("")
        textPrecoQuilo!!.setText("")
        textResultado!!.setText(R.string.valor_padrao)
        radioRetifica!!.isChecked = false
        radioCanal!!.isChecked = false
        radioButtonNitrilica!!.isChecked = false
        radioButtonEpdm!!.isChecked = false
        radioButtonX300!!.isChecked = false
    }
}