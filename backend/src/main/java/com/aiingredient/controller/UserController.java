package com.aiingredient.controller;

import com.aiingredient.dto.ApiResponse;
import com.aiingredient.model.User;
import com.aiingredient.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) { this.userRepository = userRepository; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(userRepository.findAll()));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> update(@PathVariable Long id, @RequestBody User user) {
        User existing = userRepository.findById(id).orElseThrow();
        existing.setDisplayName(user.getDisplayName());
        existing.setPhone(user.getPhone());
        existing.setEmail(user.getEmail());
        existing.setEnabled(user.getEnabled());
        existing.setRole(user.getRole());
        return ResponseEntity.ok(ApiResponse.success(userRepository.save(existing)));
    }
}
