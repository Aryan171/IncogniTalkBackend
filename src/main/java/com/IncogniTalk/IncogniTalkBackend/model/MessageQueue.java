package com.IncogniTalk.IncogniTalkBackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MessageQueue")
public class MessageQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private int messageId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "recipientUniqueId", referencedColumnName = "uniqueId"),
            @JoinColumn(name = "recipientDeviceId", referencedColumnName = "deviceId")
    })
    private Device recipientDevice;

    @Column(name = "senderUniqueId")
    private String senderUniqueId;

    @Lob
    @Column(name = "ciphertext")
    private String ciphertext;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    // Getters and Setters

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Device getRecipientDevice() {
        return recipientDevice;
    }

    public void setRecipientDevice(Device recipientDevice) {
        this.recipientDevice = recipientDevice;
    }

    public String getSenderUniqueId() {
        return senderUniqueId;
    }

    public void setSenderUniqueId(String senderUniqueId) {
        this.senderUniqueId = senderUniqueId;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
