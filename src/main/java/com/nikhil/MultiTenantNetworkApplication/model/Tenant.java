package com.nikhil.MultiTenantNetworkApplication.model;

public record Tenant(
        int id,
        String name,
        String network
){

}