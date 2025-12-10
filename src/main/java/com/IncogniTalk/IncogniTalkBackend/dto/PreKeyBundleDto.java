package com.IncogniTalk.IncogniTalkBackend.dto;

public class PreKeyBundleDto {
    private int registrationId;
    private int deviceId;
    private int preKeyId;
    private String preKeyPublic;
    private int signedPreKeyId;
    private String signedPreKeyPublic;
    private String signedPreKeySignature;
    private String identityKey;

    // Getters and Setters
    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getPreKeyId() {
        return preKeyId;
    }

    public void setPreKeyId(int preKeyId) {
        this.preKeyId = preKeyId;
    }

    public String getPreKeyPublic() {
        return preKeyPublic;
    }

    public void setPreKeyPublic(String preKeyPublic) {
        this.preKeyPublic = preKeyPublic;
    }

    public int getSignedPreKeyId() {
        return signedPreKeyId;
    }

    public void setSignedPreKeyId(int signedPreKeyId) {
        this.signedPreKeyId = signedPreKeyId;
    }

    public String getSignedPreKeyPublic() {
        return signedPreKeyPublic;
    }

    public void setSignedPreKeyPublic(String signedPreKeyPublic) {
        this.signedPreKeyPublic = signedPreKeyPublic;
    }

    public String getSignedPreKeySignature() {
        return signedPreKeySignature;
    }

    public void setSignedPreKeySignature(String signedPreKeySignature) {
        this.signedPreKeySignature = signedPreKeySignature;
    }

    public String getIdentityKey() {
        return identityKey;
    }

    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }
}
