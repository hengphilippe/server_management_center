package com.dev.smc.servers;

import com.dev.smc.servers.credentials.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servers")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @GetMapping
    public List<Server> getAllServers(){
        return serverService.getAllServers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Server> getServerById(@PathVariable Long id) {
        return serverService.getServerById(id).map(ResponseEntity::ok
        ).orElse(ResponseEntity.notFound().build()) ;
    }

    @PostMapping
    public Server createServer(@RequestBody Server server) {
        return serverService.createServer(server);
    }

    @PostMapping("/{id}/setcredential")
    public ResponseEntity<Boolean> validateCredentials(@PathVariable Long id,@RequestBody Credential credential) {
        System.out.println("Request username : "  + credential.getUsername());
        if (serverService.saveCredential(id, credential))
            return ResponseEntity.ok(Boolean.TRUE);
        return ResponseEntity.ok(Boolean.FALSE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Server> updateServer(@PathVariable Long id,@RequestBody Server server) {
         Server updatedServer = serverService.updateServer(id, server);
         return ResponseEntity.ok(updatedServer);
    }

}
