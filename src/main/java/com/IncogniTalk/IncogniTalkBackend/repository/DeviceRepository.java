package com.IncogniTalk.IncogniTalkBackend.repository;

import com.IncogniTalk.IncogniTalkBackend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Device.DeviceIdClass> {
}
