package com.example.calculoborracha.util;

import java.text.DecimalFormat;

public class Valores {

    public String pesoFormatado( String peso ) {

        DecimalFormat padraoQuilos = new DecimalFormat("###.###" );
        return padraoQuilos.format( peso );

    };


    public String valorFormatado( String valor ) {

        DecimalFormat padraoMonetario = new DecimalFormat("#.###.##" );
        return padraoMonetario.format(valor);
    }
}
