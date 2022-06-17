package com.example.msikeyvault.test;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class KeyVaultSecretsResponse {
    private String value;
    private String id;
    private List<Map<String, String>> attributes;
    private List<Object> tags;
}
