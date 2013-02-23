package android.ygo.utils;

import android.graphics.Color;

public class Configuration {
    public static int highlightColor() {
        return Color.BLUE;
    }

    public static int fontColor() {
        return Color.WHITE;
    }

    public static String baseDir() {
        return "/Device/bluetooth/images/ygo/";
    }

    public static String cardProtector() {
        return "cover";
    }

    public static boolean isMirror() {
        return false;
    }
}