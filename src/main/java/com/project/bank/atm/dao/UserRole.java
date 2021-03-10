package com.project.bank.atm.dao;

/**
 * @author raviteja karumuri
 *
 */
public enum UserRole {

    ROLE_ADMIN("System Admin"),
    ROLE_USER("User");

    private final String displayName;

     UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
