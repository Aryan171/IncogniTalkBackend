package com.IncogniTalk.IncogniTalkBackend.dto;

import java.util.Map;

public class RegistrationBundleDto {
    private String identityKey;
    private int registrationId;
    private Map<String, String> preKeys;
    private SignedPreKeyDto signedPreKey;
    private int deviceId;

    // Getters and Setters
    public String getIdentityKey() {
        return identityKey;
    }

    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public Map<String, String> getPreKeys() {
        return preKeys;
    }

    public void setPreKeys(Map<String, String> preKeys) {
        this.preKeys = preKeys;
    }

    public SignedPreKeyDto getSignedPreKey() {
        return signedPreKey;
    }

    public void setSignedPreKey(SignedPreKeyDto signedPreKey) {
        this.signedPreKey = signedPreKey;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
