package com.tian.generateCode;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author xiaoxuan.jin
 * Date 2020/6/29
 */
public class PrintGetAndSet {


    public static void main(String[] args) {
//        printSet(TRCustomerAuditView.class, "view");
//        printSet(TPFinanceApplyInvoice.class, "invoice");
//        printSet(TPFinanceApplyContract.class, "contract");
        System.out.println("分割线-------------");
//        printGet(CmbCustomerPushInfoDO.class, "pushInfoDO");
    }

    /**
     * 打印Get方法
     * @param clazz
     * @param var
     * @throws ClassNotFoundException
     */
    private static void printGet(Class clazz, String var) throws ClassNotFoundException {
        String name = clazz.getName();
        System.out.println("class name: " + name);
        Class<?> aClass = Class.forName(name);
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            System.out.println(var + ".get" +
                    StringUtils.upperCase(StringUtils.substring(fieldName, 0, 1)) +
                    StringUtils.substring(fieldName, 1, fieldName.length()) +
                    "()");
        }
    }

    /**
     * 打印Set方法
     * @param clazz
     * @param var
     * @throws ClassNotFoundException
     */
    private static void printSet(Class clazz, String var) throws ClassNotFoundException {
        String name = clazz.getName();
        System.out.println("class name: " + name);
        Class<?> aClass = Class.forName(name);
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            System.out.println(var + ".set" +
                    StringUtils.upperCase(StringUtils.substring(fieldName, 0, 1)) +
                    StringUtils.substring(fieldName, 1, fieldName.length()) +
                    "();");
        }
    }
}
