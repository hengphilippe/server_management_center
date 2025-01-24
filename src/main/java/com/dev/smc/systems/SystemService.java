package com.dev.smc.systems;

import com.dev.smc.servers.Server;
import com.dev.smc.servers.ServerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SystemService {
    private final SystemRepository systemRepository;
    private final ServerRepository serverRepository;

    public SystemService(SystemRepository systemRepository, ServerRepository serverRepository) {
        this.systemRepository = systemRepository;
        this.serverRepository = serverRepository;
    }

    public System__ createSystem(System__ system) {
        return systemRepository.save(system);
    }

    public List<System__> getAllSystems() {
        return systemRepository.findAll();
    }

    public Server addServerToSystem(Long id, Server server) {
        System__ system = systemRepository.findById(id).orElseThrow( () -> new RuntimeException("System not found"));
        server.setSystem(system);
        return serverRepository.save(server);
    }
}

