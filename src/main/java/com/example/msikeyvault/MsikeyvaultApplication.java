package com.example.msikeyvault;

import com.example.msikeyvault.test.HttpUtil;
import com.example.msikeyvault.test.KeyVaultSecretsResponse;
import com.example.msikeyvault.test.KeyVaultTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

@SpringBootApplication
public class MsikeyvaultApplication {

    public static void main(String[] args) throws IOException {
        init2();
//        init3();
        SpringApplication.run(MsikeyvaultApplication.class, args);

        System.out.println("77777777777777");
        String path = System.getProperty("user.dir") + "/";
        System.out.println("路径");
        System.out.println(path);
        File toDelteFile = new File(path + "youfilename.pfx");
        if (toDelteFile.delete()) {
            System.out.println("stared---Deleted the certificate file: " + toDelteFile.getName());
        } else {
            System.out.println("Failed to delete the certificate file.");
        }
    }

//    本地使用拷贝后的证书
//    public static void init3() throws IOException {
//        //获取解密证书的密码
//        System.setProperty("SSL-PASSWORD", "123456");
////        //        1、将File、FileInputStream 转换为byte数组：
//////BASE后保存到key vault中
//        File file = new File("C:\\Users\\ldj\\Desktop\\youfilename.pfx");
//        InputStream input = Files.newInputStream(file.toPath());
//        byte[] byt = new byte[input.available()];
//        input.read(byt);
//        BASE64Encoder base64Encoder = new BASE64Encoder();
//        String s = base64Encoder.encodeBuffer(byt);
//
//        System.out.println(s);
//        System.out.println("---------------------------------------------------");
//        String sToSaveInKeyVault = s.replaceAll("\r\n", "===CA===TAG===");
//        System.out.println(sToSaveInKeyVault);
//        System.out.println("---------------------------------------------------");
//        String sToSaveInVM = sToSaveInKeyVault.replaceAll("===CA===TAG===", "\r\n");
//        System.out.println(sToSaveInVM);
//        System.out.println("---------------------------------------------------");
//        System.out.println(s.equals(sToSaveInVM));
//
//        //从key vault取出，base64解码，然后输出到resources目录作为证书
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] after = null;
//        try {
//            after = base64Decoder.decodeBuffer(sToSaveInVM);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        File fileRes = new File("C:\\Users\\ldj\\Desktop\\youfilename2.pfx");
//        FileOutputStream fos = new FileOutputStream(fileRes);
//        fos.write(after);
//        System.out.println(s);
//        fos.close();
//    }


    public static void init2() {
        String path = System.getProperty("user.dir") + "/";
        System.out.println("路径");
        System.out.println(path);


        //获取azure token
        String access_token = getKeyVaultToken();
        String keyVaultName = "mykv0614";

        //发起请求去key vault获取解密证书的密码
        String secretNameSSLPASSWORD = "SSL-PASSWORD";
        String SSL_PASSWORD = getKeyVaultSecretValue(access_token, keyVaultName, secretNameSSLPASSWORD);
        System.setProperty("SSL-PASSWORD", SSL_PASSWORD);

        //发起请求获取证书并存到op/module目录下
        String secretName = "pfx1";
        String keyVaultSecretValue = getKeyVaultSecretValue(access_token, keyVaultName, secretName);
        System.out.println(keyVaultSecretValue);
        System.out.println("---------------------------------------------------");

        String sToSaveInVM = keyVaultSecretValue.replaceAll("===CA===TAG===", "\r\n");
        System.out.println(sToSaveInVM);
        System.out.println("sToSaveInVM--------------------------------------------");


        //从key vault取出证书，base64解码，然后输出到resources目录作为证书
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] after;
        try {
            after = base64Decoder.decodeBuffer(sToSaveInVM);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileName = "youfilename.pfx";
//        File fileRes = new File("/opt/module/" + fileName);
        File fileRes = new File(path + fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileRes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fos.write(after);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(keyVaultSecretValue);
            try {
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static ObjectMapper objectMapper2 = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    private static String getKeyVaultSecretValue(String access_token, String keyVaultName, String secretName) {
        HashMap<String, String> headers = new HashMap<>();
        String urlKeyVault = "https://" + keyVaultName + ".vault.azure.net/secrets/" + secretName + "?api-version=2016-10-01";
        headers.put("Authorization", "Bearer " + access_token);
        String resKeyvaultSecretString = HttpUtil.doGet(urlKeyVault, headers);
        KeyVaultSecretsResponse keyVaultSecretsResponse;
        try {
            keyVaultSecretsResponse = objectMapper2.readValue(resKeyvaultSecretString, new TypeReference<KeyVaultSecretsResponse>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return keyVaultSecretsResponse.getValue();

    }

    private static String getKeyVaultToken() {
        String url = "http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01&resource=https%3A%2F%2Fvault.azure.net";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Metadata", "true");
        String resToken = HttpUtil.doGet(url, headers);

        KeyVaultTokenResponse keyVaultTokenResponse;
        try {
            keyVaultTokenResponse = objectMapper2.readValue(resToken, new TypeReference<KeyVaultTokenResponse>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return keyVaultTokenResponse.getAccess_token();
    }
}
