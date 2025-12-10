package com.IncogniTalk.IncogniTalkBackend.dto;

public class RegisterRequest {
    private String username;
    private RegistrationBundleDto registrationBundle;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RegistrationBundleDto getRegistrationBundle() {
        return registrationBundle;
    }

    public void setRegistrationBundle(RegistrationBundleDto registrationBundle) {
        this.registrationBundle = registrationBundle;
    }
}
