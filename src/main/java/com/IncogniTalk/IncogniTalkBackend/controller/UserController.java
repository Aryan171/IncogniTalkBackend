package com.IncogniTalk.IncogniTalkBackend.controller;

import com.IncogniTalk.IncogniTalkBackend.dto.PreKeyBundleDto;
import com.IncogniTalk.IncogniTalkBackend.dto.RegisterRequest;
import com.IncogniTalk.IncogniTalkBackend.dto.ReplenishPreKeysRequest;
import com.IncogniTalk.IncogniTalkBackend.dto.RotateSignedPreKeyRequest;
import com.IncogniTalk.IncogniTalkBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/available/{username}")
    public ResponseEntity<Void> isUsernameAvailable(@PathVariable String username) {
        if (userService.isUsernameAvailable(username)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/users/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        if (!userService.isUsernameAvailable(request.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/keys/{userId}/{deviceId}")
    public ResponseEntity<PreKeyBundleDto> getPreKeyBundle(@PathVariable String userId, @PathVariable int deviceId) {
        PreKeyBundleDto bundle = userService.getPreKeyBundle(userId, deviceId);
        return ResponseEntity.ok(bundle);
    }

    @PostMapping("/pre-key/replenish")
    public ResponseEntity<Void> replenishPreKeys(@RequestBody ReplenishPreKeysRequest request) {
        userService.replenishPreKeys(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signed-pre-key/rotate")
    public ResponseEntity<Void> rotateSignedPreKey(@RequestBody RotateSignedPreKeyRequest request) {
        userService.rotateSignedPreKey(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
