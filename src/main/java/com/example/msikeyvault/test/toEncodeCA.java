package com.example.msikeyvault.test;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class toEncodeCA {
    public static void main(String[] args) throws IOException {
        //        1、将File、FileInputStream 转换为byte数组：
//将pfx证书BASE后保存到key vault中
        File file = new File("C:\\Users\\ldj\\Desktop\\youfilename.pfx");
        InputStream input = Files.newInputStream(file.toPath());
        byte[] byt = new byte[input.available()];
        input.read(byt);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String s = base64Encoder.encodeBuffer(byt);

        System.out.println(s);
        System.out.println("native-----------------------------------------");
        String sToSaveInKeyVault = s.replaceAll("\r\n", "===CA===TAG===");
        System.out.println(sToSaveInKeyVault);
        System.out.println("sToSaveInKeyVault------------------------------------------");
        String sToSaveInVM = sToSaveInKeyVault.replaceAll("===CA===TAG===", "\r\n");
        System.out.println(sToSaveInVM);
        System.out.println("sToSaveInVM-------------------------------------------");
        System.out.println(s.equals(sToSaveInVM));

    }
}
