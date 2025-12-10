package com.IncogniTalk.IncogniTalkBackend.repository;

import com.IncogniTalk.IncogniTalkBackend.model.Device;
import com.IncogniTalk.IncogniTalkBackend.model.OneTimePreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneTimePreKeyRepository extends JpaRepository<OneTimePreKey, Integer> {
    Optional<OneTimePreKey> findTopByDevice(Device device);
}
