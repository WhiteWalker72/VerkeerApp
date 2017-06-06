package com.proj.my.verkeerapp.utils;

import java.util.Random;

public class MathUtils {

    public static int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }

}
