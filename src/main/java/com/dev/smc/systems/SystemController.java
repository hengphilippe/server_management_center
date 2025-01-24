package com.dev.smc.systems;

import com.dev.smc.servers.Server;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/systems")
public class SystemController {
    private final SystemService systemService;

    public SystemController(final SystemService systemService) {
        this.systemService = systemService;
    }

    @PostMapping
    public System__ createSystem(@RequestBody System__ system) {
        return systemService.createSystem(system);
    }

    @GetMapping
    public List<System__> getSystems() {
        return systemService.getAllSystems();
    }

    @PostMapping("/{systemId}/servers")
    public Server addServerToSystem(@PathVariable Long systemId, @RequestBody Server server){
        return systemService.addServerToSystem(systemId, server);
    }
}
