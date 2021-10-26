package com.rawrick.flicklist.data.util;

public class Formatter {

    public static String runtimeFormatter(int min) {
        int minutes = min % 60;
        int hours = min / 60;
        String output = hours + "h " + minutes + "m";
        return output;
    }
}
