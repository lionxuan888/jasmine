package com.tian.apply.regx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoxuan.jin on 2017/2/10.
 */
public class RegularExpressionTest {

    public static void main(String[] args) {
        String pattern = "a?";
        String line = "aaaaa";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        boolean matches = m.matches();
        System.out.println(matches);
        int i = m.groupCount();
        System.out.println(i);

    }

}
