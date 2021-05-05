package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itis.zheleznov.impl.exceptions.TokenRefreshException;
import ru.itis.zheleznov.impl.models.RefreshToken;
import ru.itis.zheleznov.impl.models.User;
import ru.itis.zheleznov.impl.repositories.UserRepository;
import ru.itis.zheleznov.web.payload.request.LoginRequest;
import ru.itis.zheleznov.web.payload.request.SignupRequest;
import ru.itis.zheleznov.web.payload.request.TokenRefreshRequest;
import ru.itis.zheleznov.web.payload.response.JwtResponse;
import ru.itis.zheleznov.web.payload.response.MessageResponse;
import ru.itis.zheleznov.web.payload.response.TokenRefreshResponse;
import ru.itis.zheleznov.web.security.details.RefreshTokenService;
import ru.itis.zheleznov.web.security.details.UserDetailsImpl;
import ru.itis.zheleznov.web.security.jwt.JwtUtils;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signIn")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
            .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUser().getId());

    return ResponseEntity.ok(new JwtResponse(jwt, "Bearer" ,refreshToken.getToken(), userDetails.getUser().getId(),
            userDetails.getUsername(), userDetails.getUser().getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user =  User.builder()
            .email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .build();

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
              String token = jwtUtils.generateTokenFromUsername(user.getEmail());
              return ResponseEntity.ok(TokenRefreshResponse.builder()
                      .token(token)
                      .refreshToken(requestRefreshToken)
                      .build());
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                    "Refresh token is not in database!"));
  }
}
