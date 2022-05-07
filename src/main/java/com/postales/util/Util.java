package com.postales.util;

import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static double generarEnteroAleatorio(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }


}
