package com.IncogniTalk.IncogniTalkBackend.service;

import com.IncogniTalk.IncogniTalkBackend.dto.PreKeyBundleDto;
import com.IncogniTalk.IncogniTalkBackend.dto.RegisterRequest;
import com.IncogniTalk.IncogniTalkBackend.dto.ReplenishPreKeysRequest;
import com.IncogniTalk.IncogniTalkBackend.dto.RotateSignedPreKeyRequest;
import com.IncogniTalk.IncogniTalkBackend.exception.ResourceNotFoundException;
import com.IncogniTalk.IncogniTalkBackend.handler.ChatWebSocketHandler;
import com.IncogniTalk.IncogniTalkBackend.model.Device;
import com.IncogniTalk.IncogniTalkBackend.model.OneTimePreKey;
import com.IncogniTalk.IncogniTalkBackend.model.User;
import com.IncogniTalk.IncogniTalkBackend.repository.DeviceRepository;
import com.IncogniTalk.IncogniTalkBackend.repository.OneTimePreKeyRepository;
import com.IncogniTalk.IncogniTalkBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private OneTimePreKeyRepository oneTimePreKeyRepository;

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUniqueId(username).isEmpty();
    }

    @Transactional
    public void register(RegisterRequest request) {
        User user = new User();
        user.setUniqueId(request.getUsername());
        user.setRegistrationId(request.getRegistrationBundle().getRegistrationId());
        user.setIdentityKey(request.getRegistrationBundle().getIdentityKey());
        userRepository.save(user);

        Device device = new Device();
        device.setUser(user);
        device.setDeviceId(request.getRegistrationBundle().getDeviceId());
        device.setSignedPreKeyId(request.getRegistrationBundle().getSignedPreKey().getId());
        device.setSignedPreKey(request.getRegistrationBundle().getSignedPreKey().getPublicKey());
        device.setSpkSignature(request.getRegistrationBundle().getSignedPreKey().getSignature());
        device.setSpkExpirationTime(Timestamp.from(Instant.now().plusSeconds(315360000))); // 10 years
        deviceRepository.save(device);

        List<OneTimePreKey> opkList = new ArrayList<>();
        for (Map.Entry<String, String> entry : request.getRegistrationBundle().getPreKeys().entrySet()) {
            OneTimePreKey opk = new OneTimePreKey();
            opk.setDevice(device);
            opk.setOpkValue(entry.getValue());
            opkList.add(opk);
        }
        oneTimePreKeyRepository.saveAll(opkList);
    }

    @Transactional
    public PreKeyBundleDto getPreKeyBundle(String userId, int deviceId) {
        User user = userRepository.findByUniqueId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Device.DeviceIdClass deviceIdClass = new Device.DeviceIdClass();
        deviceIdClass.setUser(user);
        deviceIdClass.setDeviceId(deviceId);
        Device device = deviceRepository.findById(deviceIdClass)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + deviceId + " for user: " + userId));

        OneTimePreKey opk = oneTimePreKeyRepository.findTopByDevice(device)
                .orElseThrow(() -> {
                    try {
                        chatWebSocketHandler.sendControlMessage(userId, "PRE-KEYS_DEPLETED");
                    } catch (IOException e) {
                        // Handle exception
                    }
                    return new ResourceNotFoundException("No available pre-keys for device: " + deviceId);
                });

        oneTimePreKeyRepository.delete(opk);

        PreKeyBundleDto bundle = new PreKeyBundleDto();
        bundle.setRegistrationId(user.getRegistrationId());
        bundle.setDeviceId(device.getDeviceId());
        bundle.setPreKeyId(opk.getOpkId());
        bundle.setPreKeyPublic(opk.getOpkValue());
        bundle.setSignedPreKeyId(device.getSignedPreKeyId());
        bundle.setSignedPreKeyPublic(device.getSignedPreKey());
        bundle.setSignedPreKeySignature(device.getSpkSignature());
        bundle.setIdentityKey(user.getIdentityKey());

        return bundle;
    }

    @Transactional
    public void replenishPreKeys(ReplenishPreKeysRequest request) {
        User user = userRepository.findByUniqueId(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Device.DeviceIdClass deviceIdClass = new Device.DeviceIdClass();
        deviceIdClass.setUser(user);
        deviceIdClass.setDeviceId(request.getDeviceId());
        Device device = deviceRepository.findById(deviceIdClass)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + request.getDeviceId() + " for user: " + request.getUserId()));

        List<OneTimePreKey> opkList = new ArrayList<>();
        for (Map.Entry<String, String> entry : request.getPreKeys().entrySet()) {
            OneTimePreKey opk = new OneTimePreKey();
            opk.setDevice(device);
            opk.setOpkValue(entry.getValue());
            opkList.add(opk);
        }
        oneTimePreKeyRepository.saveAll(opkList);
    }

    @Transactional
    public void rotateSignedPreKey(RotateSignedPreKeyRequest request) {
        User user = userRepository.findByUniqueId(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Device.DeviceIdClass deviceIdClass = new Device.DeviceIdClass();
        deviceIdClass.setUser(user);
        deviceIdClass.setDeviceId(request.getDeviceId());
        Device device = deviceRepository.findById(deviceIdClass)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + request.getDeviceId() + " for user: " + request.getUserId()));

        device.setSignedPreKeyId(request.getSignedPreKey().getId());
        device.setSignedPreKey(request.getSignedPreKey().getPublicKey());
        device.setSpkSignature(request.getSignedPreKey().getSignature());
        deviceRepository.save(device);
    }

    @Scheduled(cron = "0 0 0 * * 0") // Every Sunday at midnight
    public void triggerSignedPreKeyRotation() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            try {
                chatWebSocketHandler.sendControlMessage(user.getUniqueId(), "ROTATE-SIGNED-PRE-KEY");
            } catch (IOException e) {
                // Handle exception
            }
        }
    }
}
