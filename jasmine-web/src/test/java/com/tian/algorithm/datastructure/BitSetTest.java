package com.tian.algorithm.datastructure;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by xiaoxuan.jin on 2016/8/17.
 */
public class BitSetTest {

    public enum Type {
        dev, beta, prod;
    }

    @Test
    public void testBitSet() throws Exception {
        BitSet bitSet = new BitSet();
        bitSet.and(new BitSet(10));
        System.out.println(bitSet);
        String tsId =  Joiner.on(".").join("qss.baoxiao", Type.dev.name(), "tsId");
        System.out.println(tsId);

        long miSe  = 485845;
        System.out.println("耗时: " + miSe/1000 + "秒, " + miSe%1000 + "毫秒");

        List<String> list = Lists.newArrayList("a", "b", "c", "d", "e", "f");
        List<String> strings = list.subList(0, 5);
        System.out.println(strings);

    }


}
