package com.tian.apply.regx;

import org.junit.Test;

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

    @Test
    public void testSplit() throws Exception {
        System.out.println("1,".split(",").length);
        System.out.println(",1".split(",").length);
        System.out.println("1,1".split(",").length);
        System.out.println(",".split(",", -1).length);
        System.out.println("--------------");
        Pattern compile = Pattern.compile(",");
        Matcher matcher = compile.matcher("12,");
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }
        System.out.println("--------------");
        String aaa = "111";
        String bbb = "111";
        String sa = new String("111");
        String sb = sa;
        sb = "333";
        System.out.println(aaa == bbb);
        System.out.println(sa == sb);
    }
}
