package com.hexaware.assetmanagement.dto;

public class LoginRequestDTO {

    private String username;
    private String password;

    // No-arg constructor
    public LoginRequestDTO() {}

    // All-arg constructor
    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
