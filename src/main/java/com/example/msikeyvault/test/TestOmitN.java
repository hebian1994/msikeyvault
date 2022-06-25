package com.example.msikeyvault.test;

public class TestOmitN {
    public static void main(String[] args) {

        String s = "'sdsgdasda" +
                "6666666";

        System.out.println("转换前：" + s);

        s = s.replaceAll("\n", "===CA===TAG===");

        System.out.println("转换后：" + s);

        String s1 = s.replaceAll("===CA===TAG===", "\n");
        System.out.println("还原" + s1);
    }
}
