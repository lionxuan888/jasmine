package com.tian.apply.regx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoxuan.jin on 2017/2/10.
 */
public class RegularExpressionTest {

    public static void main(String[] args) {
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(<A>.*?)(<B>\\d+)(<C>.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            String b = m.group("B");
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }
    }

}
