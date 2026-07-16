package com.aiingredient.service;

import com.aiingredient.dto.LoginRequest;
import com.aiingredient.dto.LoginResponse;
import com.aiingredient.model.User;
import com.aiingredient.repository.UserRepository;
import com.aiingredient.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository; this.passwordEncoder = passwordEncoder; this.jwtUtil = jwtUtil;
    }
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("用户不存在"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("密码错误");
        if (!user.getEnabled()) throw new RuntimeException("账号已被禁用");
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
        LoginResponse resp = new LoginResponse();
        resp.setToken(token); resp.setUsername(user.getUsername());
        resp.setDisplayName(user.getDisplayName()); resp.setRole(user.getRole());
        resp.setUserId(user.getId());
        return resp;
    }
    public void register(LoginRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("用户名已存在");
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDisplayName(request.getUsername());
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
    public User getCurrentUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}
