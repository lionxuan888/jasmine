package com.tian.apply.guava;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FunctionTest {


    @org.junit.Test
    public void testFunction() throws Exception {
        List<Integer> activePeriods = Lists.newArrayList();
        activePeriods.add(201609);
        activePeriods.add(201607);
        System.out.println(activePeriods.contains(201608));
        System.out.println(activePeriods.contains(201609));
        System.out.println(activePeriods.contains(201607));
        System.out.println(activePeriods);
        List<Foo> list = Lists.newArrayList();
        Foo a = new Foo(201601, "aaa");
        Foo b = new Foo(201602, "bbb");
        Foo c = new Foo(201603, "ccc");
        Foo d = new Foo(201601, "ddd");
        Foo e = new Foo(201603, "eee");
        list.add(a);list.add(b);
        list.add(c);list.add(d);
        list.add(e);
        ImmutableListMultimap<Integer, Foo> index = Multimaps.index(list, new Function<Foo, Integer>() {
            public Integer apply(Foo input) {
                return input.getPeriod();
            }
        });

        for (Integer period : index.keys()) {
            ImmutableList<Foo> foos = index.get(period);
            System.out.println(period + " == " + foos.toString());
        }
        for (Integer integer : index.keySet()) {
            ImmutableList<Foo> foos = index.get(integer);
            System.out.println(integer + " == " + foos.toString());
        }

        String path = "1:/qreaper/balance/carryOver,2:/qreaper/balance/carryOver";
        Map<String, String> split = Splitter.on(",").omitEmptyStrings().trimResults().withKeyValueSeparator(":").split(path);
        System.out.println(split);


        DateTime contractDueDate = new DateTime(DateUtils.parseDate("2016-12-01", new String[]{"yyyy-MM-dd"}));
        contractDueDate = contractDueDate.withDayOfMonth(1);
        DateTime periodMonth = new DateTime(DateUtils.parseDate("2016-10-01", new String[]{"yyyy-MM-dd"}));
        Months monthsBetween = Months.monthsBetween(contractDueDate, periodMonth);

        System.out.println(monthsBetween.getMonths());

        List<String> revaluationAndWriteOffInChargeEmail = Lists.newArrayList(Splitter.on(",").trimResults().omitEmptyStrings().split("xiaoxuan.jin@qunar.com"));
        revaluationAndWriteOffInChargeEmail.add("22@qunbar.com");
        System.out.println(revaluationAndWriteOffInChargeEmail);


    }

    @Test
    public void test() throws Exception {
        System.out.println("10092 : " + Integer.toHexString(10092));
        System.out.println("9789 : " + Integer.toHexString(9789));
        System.out.println("9790 : " + Integer.toHexString(9790));
        System.out.println("9791 : " + Integer.toHexString(9791));
        System.out.println("9792 : " + Integer.toHexString(9792));
        System.out.println("9793 : " + Integer.toHexString(9793));

        List<String> strings = Splitter.on(",").trimResults().omitEmptyStrings().splitToList("");
        System.out.println(strings);

        Set<Integer> set = Sets.newHashSet();
set.add(Integer.valueOf(1));
        set.add(Integer.valueOf(2));
        set.add(Integer.valueOf(1));
        System.out.println(set);

    }
}
