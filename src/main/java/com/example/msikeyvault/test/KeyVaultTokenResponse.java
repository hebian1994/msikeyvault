package com.example.msikeyvault.test;

import lombok.Data;

@Data
public class KeyVaultTokenResponse {
    private String access_token;
    private String client_id;
    private String expires_in;
    private String expires_on;
    private String ext_expires_in;
    private String not_before;
    private String resource;
    private String token_type;

}
