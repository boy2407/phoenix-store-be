package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Role;
import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.payload.user.UserCreateRequest;
import com.example.phoenixstorebe.payload.user.UserReponse;
import com.example.phoenixstorebe.repository.RoleRepository;
import com.example.phoenixstorebe.repository.UserRepository;
import com.example.phoenixstorebe.security.JwtService;
import com.example.phoenixstorebe.service.CartService;
import com.example.phoenixstorebe.service.MailService;
import com.example.phoenixstorebe.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${app.base-url}")
    private String baseUrl;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final CartService cartService;
    @Override
    @Transactional
    public UserReponse registerUser(UserCreateRequest request) {
        try {
            Role defaultRole = roleRepository.findRoleByRoleNameIgnoreCase("user");
            if (defaultRole == null) {
                throw new BadRequestException("The system is maintenance. ");
            }

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username already exists");
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
            }

            if(request.getPassword() == null || !request.getPassword().equals(request.getPassword2())) {
                throw new BadRequestException("Passwords do not match");
            }

            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEnabled(false);

            user.setRole(defaultRole);

            String token = jwtService.generateToken(user);
            userRepository.save(user);

            String verifyLink = baseUrl + "/api/auth/verify?token=" + token;
            mailService.sendVerificationMail(user.getEmail(), verifyLink, user.getUsername());

            UserReponse response = new UserReponse();
            response.setId(user.getId() != null ? user.getId().longValue() : null);
            response.setFirstname(user.getFirstname());
            response.setLastname(user.getLastname());
            response.setUsername(user.getUsername());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());
            return response;
        } catch (Exception ex) {
           throw new BadRequestException("Register user failed: " + ex.getMessage());
        }

    }

    @Override
    @Transactional
    public boolean verifyUser(String verifyToken) {
        try {
            String username = jwtService.getUsernameFromToken(verifyToken);

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException("User not found"));
            if (user.isEnabled()) return false;

            user.setEnabled(true);
            userRepository.save(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    @Override
    public String login(String username, String password, HttpSession session) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadRequestException("Invalid password");
            }

            if (!user.isEnabled()) {
                throw new BadRequestException("Account not verified");
            }

            // Generate JWT token and return
            String token = jwtService.generateToken(user);
            user = this.findUserEntityByUsername(username);
            cartService.mergeCartSessionToDb(user, session);

            return token ;
        } catch (Exception ex) {
            throw new BadRequestException("Login failed: " + ex.getMessage());
        }
    }

    @Override
    public UserReponse findByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException("User not found"));
            UserReponse response = new UserReponse();
            response.setId(user.getId() != null ? user.getId().longValue() : null);
            response.setFirstname(user.getFirstname());
            response.setLastname(user.getLastname());
            response.setUsername(user.getUsername());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());
            return response;
        } catch (Exception ex) {
            throw new BadRequestException("Find user failed: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateUser(UserReponse userDto) {
        try {
            User user = userRepository.findByUsername(userDto.getUsername())
                    .orElseThrow(() -> new BadRequestException("User not found"));
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            userRepository.save(user);
        } catch (Exception ex) {
            throw new BadRequestException("Update user failed: " + ex.getMessage());
        }
    }

    @Override
    public void deleteUser(String username) { throw new UnsupportedOperationException(); }

    @Override
    public User findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        if (principal instanceof String username && !"anonymousUser".equals(username)) {
            return userRepository.findByUsername(username).orElse(null);
        }
        return null;
    }
}
