package com.dev.smc.servers.credentials;

import jakarta.persistence.*;

@Entity
@Table(name = "credential")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_sudo", nullable = true)
    private Boolean isSudo;

    @Column(name = "credentail_name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "passwd")
    private String password;

    @PrePersist
    protected void onCreate() {
        isSudo = false;
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
        System.out.println("Password Process...");
//        return password;
        return CredentialUtil.decrypt(password);
    }

    public void setPassword(String password) {
        System.out.println("Password Process...");
//        this.password = password;
        this.password = CredentialUtil.encrypt(password);
    }
}
