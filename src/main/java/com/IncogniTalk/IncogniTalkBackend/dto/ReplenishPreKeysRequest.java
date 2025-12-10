package com.IncogniTalk.IncogniTalkBackend.dto;

import java.util.Map;

public class ReplenishPreKeysRequest {
    private String userId;
    private int deviceId;
    private Map<String, String> preKeys;

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

    public Map<String, String> getPreKeys() {
        return preKeys;
    }

    public void setPreKeys(Map<String, String> preKeys) {
        this.preKeys = preKeys;
    }
}
