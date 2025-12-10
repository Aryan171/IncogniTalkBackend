package com.IncogniTalk.IncogniTalkBackend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Device")
@IdClass(Device.DeviceIdClass.class)
public class Device {

    @Id
    @Column(name = "deviceId")
    private int deviceId;

    @Id
    @ManyToOne
    @JoinColumn(name = "uniqueId", referencedColumnName = "uniqueId")
    private User user;

    @Column(name = "signedPreKeyId")
    private int signedPreKeyId;

    @Column(name = "signedPreKey")
    private String signedPreKey;

    @Column(name = "spkSignature")
    private String spkSignature;

    @Column(name = "spkExpirationTime")
    private Timestamp spkExpirationTime;

    // Getters and Setters

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSignedPreKeyId() {
        return signedPreKeyId;
    }

    public void setSignedPreKeyId(int signedPreKeyId) {
        this.signedPreKeyId = signedPreKeyId;
    }

    public String getSignedPreKey() {
        return signedPreKey;
    }

    public void setSignedPreKey(String signedPreKey) {
        this.signedPreKey = signedPreKey;
    }

    public String getSpkSignature() {
        return spkSignature;
    }

    public void setSpkSignature(String spkSignature) {
        this.spkSignature = spkSignature;
    }

    public Timestamp getSpkExpirationTime() {
        return spkExpirationTime;
    }

    public void setSpkExpirationTime(Timestamp spkExpirationTime) {
        this.spkExpirationTime = spkExpirationTime;
    }

    // Composite Primary Key Class
    public static class DeviceIdClass implements Serializable {
        private int deviceId;
        private User user;

        // Getters, Setters, equals, hashCode
        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DeviceIdClass that = (DeviceIdClass) o;

            if (deviceId != that.deviceId) return false;
            return user != null ? user.equals(that.user) : that.user == null;
        }

        @Override
        public int hashCode() {
            int result = deviceId;
            result = 31 * result + (user != null ? user.hashCode() : 0);
            return result;
        }
    }
}
