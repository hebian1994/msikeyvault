package com.example.msikeyvault.test;

import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ManagedIdentity {
    /**
     * Authenticate with a User Assigned Managed identity.
     */
//    public void createManagedIdentityCredential() {
//        ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
//                .clientId("<USER ASSIGNED MANAGED IDENTITY CLIENT ID>") // only required for user assigned
//                .build();
//
//        // Azure SDK client builders accept the credential as a parameter
//        SecretClient client = new SecretClientBuilder()
//                .vaultUrl("https://{YOUR_VAULT_NAME}.vault.azure.net")
//                .credential(managedIdentityCredential)
//                .buildClient();
//        KeyVaultSecret secrettest = client.getSecret("secrettest");
//
//        System.out.println(secrettest.getValue());
//    }

    /**
     * Authenticate with a System Assigned Managed identity.
     */
    public String createManagedIdentityCredential() {
        ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
                .build();

        // Azure SDK client builders accept the credential as a parameter
        SecretClient client = new SecretClientBuilder()
                .vaultUrl("https://mykv0614.vault.azure.net")
                .credential(managedIdentityCredential)
                .buildClient();

        KeyVaultSecret secrettest = client.getSecret("secrettest");

        System.out.println(secrettest.getValue());
        return secrettest.getValue();
    }
}
