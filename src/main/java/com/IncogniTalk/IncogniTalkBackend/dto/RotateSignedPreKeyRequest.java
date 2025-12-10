package com.IncogniTalk.IncogniTalkBackend.dto;

public class RotateSignedPreKeyRequest {
    private String userId;
    private int deviceId;
    private SignedPreKeyDto signedPreKey;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public SignedPreKeyDto getSignedPreKey() {
        return signedPreKey;
    }

    public void setSignedPreKey(SignedPreKeyDto signedPreKey) {
        this.signedPreKey = signedPreKey;
    }
}
