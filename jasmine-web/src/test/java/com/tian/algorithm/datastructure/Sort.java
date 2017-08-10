package com.tian.algorithm.datastructure;


import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by xiaoxuan.jin on 2017/8/10.
 */
public class Sort {


    public static void quickSort(int[] array, int pointI, int pointJ) {
        int pivot = array[0];
        int sentinelA = 0;
        int sentinelB = array.length - 1;
        int pivotIndex = -1;
        loop:
        for (; sentinelB >= 0; sentinelB--) {
            if (sentinelA == sentinelB) { // 哨兵相遇
                array[sentinelA] = pivot;
                pivotIndex = sentinelA;
                break;
            }
            if (array[sentinelB] < pivot) {
                array[sentinelA] = array[sentinelB];
                for (sentinelA = sentinelA + 1; sentinelA < array.length; sentinelA++) {
                    if (sentinelA == sentinelB) { // 哨兵相遇
                        array[sentinelB] = pivot;
                        pivotIndex = sentinelB;
                        break loop;
                    }
                    if (array[sentinelA] > pivot) {
                        array[sentinelB] = array[sentinelA];
                        break;
                    }
                }
            }
        }
        if (pivotIndex == -1) {
            pivotIndex = pointI;
        }
        int subALeftIndex = pointI;
        int subARightIndex = pivotIndex - 1;
        int subBLeftIndex = pivotIndex + 1;
        int subBRightIndex = pointJ;
        if (pivotIndex == 0) {
            quickSort(array, 1, array.length - 1);
        } else if (pivotIndex == array.length - 1) {
            quickSort(array, 0, array.length - 1);
        } else {
            if (subALeftIndex == subARightIndex) {
                return;
            } else {
                quickSort(array, subALeftIndex, subARightIndex);
            }
            if (subBLeftIndex == subBRightIndex) {

            } else {
                quickSort(array, subBLeftIndex, subBRightIndex);
            }
        }
    }



    private static void qsort(int[] arr, int low, int high){
        if (low < high){
            int pivot=partition(arr, low, high);        //将数组分为两部分
            qsort(arr, low, pivot-1);                   //递归排序左子数组
            qsort(arr, pivot+1, high);                  //递归排序右子数组
        }
    }
    private static int partition(int[] arr, int low, int high){
        int pivot = arr[low];     //枢轴记录
        while (low<high){
            while (low<high && arr[high]>=pivot) --high;
            arr[low]=arr[high];             //交换比枢轴小的记录到左端
            while (low<high && arr[low]<=pivot) ++low;
            arr[high] = arr[low];           //交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }

    public static void main(String[] args) throws Exception {
        int[] array = new int[]{6, 7,8,9,11,0};
        qsort(array, 0, 5);

        for (int i : array) {
            System.out.print(i + ",");
        }
    }
}
