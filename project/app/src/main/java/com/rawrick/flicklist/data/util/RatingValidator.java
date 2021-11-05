package com.rawrick.flicklist.data.util;

public class RatingValidator {

    public static boolean isRatingValid(float value) {
        if (value > 10.0f) return false;
        if (value < 0.5f) return false;
        if (value % 0.5 == 0) {
            return true;
        } else {
            return false;
        }
    }
}
