package com.nikhil.MultiTenantNetworkApplication.model;

public record Device(
        String name,
        String ipAddress,
        int tenantId
) {
}