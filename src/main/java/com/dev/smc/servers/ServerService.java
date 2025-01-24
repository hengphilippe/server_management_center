package com.dev.smc.servers;

import com.dev.smc.servers.credentials.Credential;
import com.dev.smc.servers.credentials.CredentialService;
import com.dev.smc.servers.sshConnection.Connection;
import com.dev.smc.systems.System__;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
//    @Autowired
    private final ServerRepository serverRepository;
    private final CredentialService credentialService;

    public ServerService(ServerRepository serverRepository, CredentialService credentialService) {
        this.serverRepository = serverRepository;
        this.credentialService = credentialService;
    }
//    public ServerService(ServerRepository serverRepository) {
//        this.serverRepository = serverRepository;
//    }

    public Optional<Server> getServerById(Long id) {
        return serverRepository.findById(id);
    }

    public Server createServer(Server server) {
        return serverRepository.save(server);
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Server updateServer(Long id, Server serverUpdate) {
        Server server = serverRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Server not found"));
        if(server != null)
            serverUpdate.setId(id);
        return serverRepository.save(server);
    }

    public Boolean saveCredential(Long id, Credential credential) {
          Server server = serverRepository.findById(id).orElseThrow( () -> new RuntimeException("Server not found"));
          int port = 22;
          if (Connection.verifyConnectionByPassword(
                  server.getIpAddress(),
                  port,
                  credential.getUsername(),
                  credential.getPassword())){
              credentialService.saveCredential(credential);
              server.setCredentail(credential);
              serverRepository.save(server);
              return true;
          }
          return false;
    }
}
