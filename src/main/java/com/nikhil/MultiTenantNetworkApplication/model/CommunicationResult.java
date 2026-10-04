package com.nikhil.MultiTenantNetworkApplication.model;

public record CommunicationResult(
        String sourceIp,
        String destinationIp,
        String sourceDevice,
        String destinationDevice,
        boolean allowed,
        String message
) {
}