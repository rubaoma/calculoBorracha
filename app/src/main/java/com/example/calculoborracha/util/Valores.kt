package com.example.calculoborracha.util

import java.text.DecimalFormat
import java.text.NumberFormat

class Valores {
    fun pesoFormatado(peso: Double): String {
        val padraoQuilos = DecimalFormat("###,###,###")
        return padraoQuilos.format(peso).replace(",", ".")
    }

    fun valorFormatado(valor: Double): String {
        val padraoMonetario = DecimalFormat("")
        return padraoMonetario.format(valor).replace(",", ".")
        //        NumberFormat padraoMonetario = NumberFormat.getIntegerInstance();
//        return padraoMonetario.format(valor);
    }

    fun valorFormatado2(valor: Double): String {

//        DecimalFormat padraoMonetario = new DecimalFormat("" );
//        return padraoMonetario.format(valor).replace(",", ".");
        val padraoMonetario = NumberFormat.getIntegerInstance()
        return padraoMonetario.format(valor)
    }
}