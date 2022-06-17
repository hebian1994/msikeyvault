package com.example.msikeyvault.controller;

//import com.ctc.wstx.api.EmptyElementHandler;

import com.example.msikeyvault.test.HttpUtil;
//import com.example.msikeyvault.test.ManagedIdentity;
import com.example.msikeyvault.test.KeyVaultSecretsResponse;
import com.example.msikeyvault.test.KeyVaultTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class TestController {
    // jackson转换工具
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @RequestMapping("/")
    public String get() {

        String access_token = getKeyVaultToken();

        String keyVaultName = "mykv0614";

        String secretName = "secrettest";
        String keyVaultSecretValue = getKeyVaultSecretValue(access_token, keyVaultName, secretName);

        String secretName2 = "test0617";
        String keyVaultSecretValue2 = getKeyVaultSecretValue(access_token, keyVaultName, secretName2);

        log.info("===========>{}{}", keyVaultSecretValue, keyVaultSecretValue2);
        return keyVaultSecretValue + keyVaultSecretValue2;
    }

    private String getKeyVaultSecretValue(String access_token, String keyVaultName, String secretName) {
        HashMap<String, String> headers = new HashMap<>();
        String urlKeyVault = "https://" + keyVaultName + ".vault.azure.net/secrets/" + secretName + "?api-version=2016-10-01";
        headers.put("Authorization", "Bearer " + access_token);
        String resKeyvaultSecretString = HttpUtil.doGet(urlKeyVault, headers);
        KeyVaultSecretsResponse keyVaultSecretsResponse;
        try {
            keyVaultSecretsResponse = objectMapper.readValue(resKeyvaultSecretString, new TypeReference<KeyVaultSecretsResponse>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return keyVaultSecretsResponse.getValue();

    }

    private String getKeyVaultToken() {
        String url = "http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01&resource=https%3A%2F%2Fvault.azure.net";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Metadata", "true");
        String resToken = HttpUtil.doGet(url, headers);

        KeyVaultTokenResponse keyVaultTokenResponse;
        try {
            keyVaultTokenResponse = objectMapper.readValue(resToken, new TypeReference<KeyVaultTokenResponse>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return keyVaultTokenResponse.getAccess_token();
    }
}
