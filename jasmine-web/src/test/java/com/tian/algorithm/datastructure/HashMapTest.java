package com.tian.algorithm.datastructure;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by xiaoxuan.jin on 2017/2/7.
 */
public class HashMapTest<K, V> {
    // 大小
    private int size;
    // 容量
    private int capacity;

    private Entry[] table = new Entry[]{};

    public HashMapTest(int capacity) {
        this.capacity = capacity;
    }

    class Entry<K, V> {
        K k;
        V v;
        int hash;
        Entry<K,V> next;

        Entry(int hash , K k, V v, Entry next) {

        }
    }

    public void put(K k, V v) {
//        int hash = hash(k);
//        int i = indexFor(hash, table.length);
//        for (Entry e = table[i]; e!=null; e = e.next()) {
//            if (e.hash == hash && (e.k == k || e.k.equals(k))) {
//                V oldValue = e.v;
//                e.v = v;
//                return oldValue;
//            }
//        }
//        addEntry();
    }

    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (e.k == key || e.k.equals(key))) {
          //      return e.v;
            }
        }
        return null;
    }

    public void addEntry(int hash, K key, V value, int index){
        Entry entry = table[index];
        table[index] = new Entry(hash, key, value, entry);
        size ++;
    }

    // hash algorithm
    private int hash(K k) {
        return k.hashCode();
    }


    private int indexFor(int hash, int length) {
        return hash & (length);
    }


    @Test
    public void testHashMap() throws Exception {


    }

    static void aa() {
        System.out.println("aa");
    }

    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < 17; i++) {
            hashMap.put(i, "v" + i);
        }
        System.out.println("done");
    }
}
