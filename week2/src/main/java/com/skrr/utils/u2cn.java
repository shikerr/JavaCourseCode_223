package com.skrr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class u2cn {

    public static void main(String[] args) {
        for (String str : args) {
            System.out.println(str);
            System.out.println(unicodeToString(str));
            System.out.println();
        }
    }

    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
}
