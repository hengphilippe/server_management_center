package com.dev.smc.servers;

import com.dev.smc.servers.credentials.Credential;
import com.dev.smc.systems.System__;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "remark")
    private String remark;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "ip_address")
    private String ipAddress;

    private String os;

    @Enumerated(EnumType.STRING)
    private ServerType type;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "system_id", nullable = true)
    private System__ system;

    @ManyToOne
    @JoinColumn(name = "credentail_id", nullable = true)
    private Credential credentail;

    public Credential getCredentail() {
        return credentail;
    }

    public void setCredentail(Credential credentail) {
        this.credentail = credentail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public System__ getSystem() {
        return system;
    }

    public void setSystem(System__ system) {
        this.system = system;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ServerType getType() {
        return type;
    }

    public void setType(ServerType type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
