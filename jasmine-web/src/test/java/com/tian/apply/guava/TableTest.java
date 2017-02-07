package com.tian.apply.guava;

import com.google.common.collect.HashBasedTable;
import org.junit.Test;

/**
 * Created by xiaoxuan.jin on 2016/10/20.
 */
public class TableTest {

    @Test
    public void testTable() throws Exception {
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("cny", "usd", "0.6");
        table.put("usd", "cny", "1.6");
        table.put("cny", "hkd", "0.8");
        table.put("twd", "cny", "0.2");
        table.put("twd", "cny", "0.3");
        System.out.println(table.get("twd", "cny"));
        System.out.println(table);
    }
}
