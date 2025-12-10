package com.IncogniTalk.IncogniTalkBackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "OneTimePreKey")
public class OneTimePreKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opkId")
    private int opkId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "uniqueId", referencedColumnName = "uniqueId"),
            @JoinColumn(name = "deviceId", referencedColumnName = "deviceId")
    })
    private Device device;

    @Column(name = "opkValue")
    private String opkValue;

    // Getters and Setters

    public int getOpkId() {
        return opkId;
    }

    public void setOpkId(int opkId) {
        this.opkId = opkId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getOpkValue() {
        return opkValue;
    }

    public void setOpkValue(String opkValue) {
        this.opkValue = opkValue;
    }
}
