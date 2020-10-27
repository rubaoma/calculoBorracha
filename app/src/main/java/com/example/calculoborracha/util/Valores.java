package com.example.calculoborracha.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Valores {

    public String pesoFormatado(double peso ) {

        DecimalFormat padraoQuilos = new DecimalFormat("###,###,###" );
        return padraoQuilos.format( peso ).replace(",", ".");

    };


    public String valorFormatado(double valor ) {

        DecimalFormat padraoMonetario = new DecimalFormat("" );
        return padraoMonetario.format(valor).replace(",", ".");
//        NumberFormat padraoMonetario = NumberFormat.getIntegerInstance();
//        return padraoMonetario.format(valor);
    }

    public String valorFormatado2(double valor ) {

//        DecimalFormat padraoMonetario = new DecimalFormat("" );
//        return padraoMonetario.format(valor).replace(",", ".");
        NumberFormat padraoMonetario = NumberFormat.getIntegerInstance();
        return padraoMonetario.format(valor);


    }
}

