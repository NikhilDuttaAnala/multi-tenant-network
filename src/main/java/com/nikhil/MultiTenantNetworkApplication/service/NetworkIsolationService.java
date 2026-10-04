package com.nikhil.MultiTenantNetworkApplication.service;

import com.nikhil.MultiTenantNetworkApplication.model.CommunicationResult;
import com.nikhil.MultiTenantNetworkApplication.model.Device;
import com.nikhil.MultiTenantNetworkApplication.model.Tenant;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class NetworkIsolationService {

    private final Map<Integer, Tenant> tenants =
            new LinkedHashMap<>();

    private final Map<String, Device> devices =
            new LinkedHashMap<>();

    private final Set<Integer> tenantsAllowedToUseSharedServices =
            new HashSet<>();

    public NetworkIsolationService() {
        loadInitialNetwork();
    }

    private void loadInitialNetwork() {

        // Tenants
        tenants.put(
                10,
                new Tenant(
                        10,
                        "Computer Science",
                        "192.168.10.0/24"
                )
        );

        tenants.put(
                20,
                new Tenant(
                        20,
                        "Electronics",
                        "192.168.20.0/24"
                )
        );

        tenants.put(
                30,
                new Tenant(
                        30,
                        "Administration",
                        "192.168.30.0/24"
                )
        );

        tenants.put(
                100,
                new Tenant(
                        100,
                        "Shared Services",
                        "192.168.100.0/24"
                )
        );

        // Devices
        devices.put(
                "192.168.10.10",
                new Device(
                        "CS-PC-1",
                        "192.168.10.10",
                        10
                )
        );

        devices.put(
                "192.168.10.11",
                new Device(
                        "CS-PC-2",
                        "192.168.10.11",
                        10
                )
        );

        devices.put(
                "192.168.20.10",
                new Device(
                        "ECE-PC-1",
                        "192.168.20.10",
                        20
                )
        );

        devices.put(
                "192.168.20.11",
                new Device(
                        "ECE-PC-2",
                        "192.168.20.11",
                        20
                )
        );

        devices.put(
                "192.168.30.10",
                new Device(
                        "ADMIN-PC-1",
                        "192.168.30.10",
                        30
                )
        );

        devices.put(
                "192.168.30.11",
                new Device(
                        "ADMIN-PC-2",
                        "192.168.30.11",
                        30
                )
        );

        devices.put(
                "192.168.100.10",
                new Device(
                        "WEB-SERVER",
                        "192.168.100.10",
                        100
                )
        );

        // Allow normal tenants to access shared services.
        tenantsAllowedToUseSharedServices.add(10);
        tenantsAllowedToUseSharedServices.add(20);
        tenantsAllowedToUseSharedServices.add(30);
    }

    public List<Tenant> getTenants() {
        return new ArrayList<>(tenants.values());
    }

    public List<Device> getDevices() {
        return new ArrayList<>(devices.values());
    }

    public Device addDevice(Device device) {
        devices.put(device.ipAddress(), device);
        return device;
    }

    public CommunicationResult checkCommunication(
            String sourceIp,
            String destinationIp
    ) {
        Device source = devices.get(sourceIp);
        Device destination = devices.get(destinationIp);

        if (source == null || destination == null) {
            return new CommunicationResult(
                    sourceIp,
                    destinationIp,
                    source == null ? "Unknown" : source.name(),
                    destination == null
                            ? "Unknown"
                            : destination.name(),
                    false,
                    "BLOCKED: Source or destination device not found"
            );
        }

        // Same tenant communication is allowed.
        if (source.tenantId() == destination.tenantId()) {
            return new CommunicationResult(
                    sourceIp,
                    destinationIp,
                    source.name(),
                    destination.name(),
                    true,
                    "ALLOWED: Same-tenant communication"
            );
        }

        // Access to shared services is allowed.
        if (destination.tenantId() == 100
                && tenantsAllowedToUseSharedServices
                .contains(source.tenantId())) {

            return new CommunicationResult(
                    sourceIp,
                    destinationIp,
                    source.name(),
                    destination.name(),
                    true,
                    "ALLOWED: Access to shared services"
            );
        }

        // All other cross-tenant traffic is blocked.
        return new CommunicationResult(
                sourceIp,
                destinationIp,
                source.name(),
                destination.name(),
                false,
                "BLOCKED: Cross-tenant communication is not allowed"
        );
    }
}
