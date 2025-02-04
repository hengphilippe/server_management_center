package com.dev.smc.servers.credentials;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "credential")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_sudo", nullable = true)
    private Boolean isSudo = false;

    @Column(name = "credential_name") // ✅ កែសរសេរ
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "passwd")
    private String password;

    @Transient // 👉 បញ្ជាក់ថាមិនផ្ទុកនៅក្នុង Database
    @Autowired
    private CredentialUtil credentialUtil;

    @PrePersist
    @PreUpdate
    protected void encryptPassword() {
        if (password != null) {
            System.out.println("Encrypting password...");
            password = credentialUtil.encrypt(password);
        }
    }

    @PostLoad
    protected void decryptPassword() {
        if (password != null) {
            System.out.println("Decrypting password...");
            password = credentialUtil.decrypt(password);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSudo() {
        return isSudo;
    }

    public void setSudo(Boolean sudo) {
        isSudo = sudo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
