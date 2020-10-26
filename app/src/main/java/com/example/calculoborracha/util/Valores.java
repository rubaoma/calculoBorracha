package com.example.calculoborracha.util;

import java.text.DecimalFormat;

public class Valores {

    public String pesoFormatado(double peso ) {

        DecimalFormat padraoQuilos = new DecimalFormat("###,###,###" );
        return padraoQuilos.format( peso ).replace(",", ".");

    };


    public String valorFormatado( String valor ) {

        DecimalFormat padraoMonetario = new DecimalFormat("###,###,##" );
        return padraoMonetario.format(valor);
    }
}
