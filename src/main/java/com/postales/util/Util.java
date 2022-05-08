package com.postales.util;

import java.util.concurrent.ThreadLocalRandom;

public class Util {

    /**
     * Genera un número entero aleatorio
     * @param minimo Mínimo valor entero
     * @param maximo Máximo valor entero
     * @return Número entero aleatorio
     */
    public static int generarEnteroAleatorio(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }


}
