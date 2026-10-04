package com.nikhil.MultiTenantNetworkApplication.controller;


import com.nikhil.MultiTenantNetworkApplication.model.CommunicationResult;
import com.nikhil.MultiTenantNetworkApplication.model.Device;
import com.nikhil.MultiTenantNetworkApplication.model.Tenant;
import com.nikhil.MultiTenantNetworkApplication.service.NetworkIsolationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network")
public class NetworkApiController {

    private final NetworkIsolationService networkIsolationService;

    public NetworkApiController(
            NetworkIsolationService networkIsolationService
    ) {
        this.networkIsolationService =
                networkIsolationService;
    }

    @GetMapping("/tenants")
    public List<Tenant> getTenants() {
        return networkIsolationService.getTenants();
    }

    @GetMapping("/devices")
    public List<Device> getDevices() {
        return networkIsolationService.getDevices();
    }

    @GetMapping("/check")
    public CommunicationResult checkCommunication(
            @RequestParam String sourceIp,
            @RequestParam String destinationIp
    ) {
        return networkIsolationService.checkCommunication(
                sourceIp,
                destinationIp
        );
    }

    @PostMapping("/devices")
    public Device addDevice(
            @RequestBody Device device
    ) {
        return networkIsolationService.addDevice(device);
    }
}