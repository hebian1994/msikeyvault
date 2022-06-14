package com.example.msikeyvault.controller;

import com.example.msikeyvault.test.ManagedIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @Autowired
    ManagedIdentity managedIdentity;

    @RequestMapping("/")
    public String get() {
        String managedIdentityCredential = managedIdentity.createManagedIdentityCredential();
        log.info("===========>{}", managedIdentityCredential);
        return managedIdentityCredential;
    }
}
