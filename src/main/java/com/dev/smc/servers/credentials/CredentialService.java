package com.dev.smc.servers.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    @Autowired
    public CredentialRepository repository;
    public Credential saveCredential(Credential credential) {

        return repository.save(credential);
    }
}
