package ru.itis.zheleznov.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itis.zheleznov.api.dto.UserDto;
import ru.itis.zheleznov.api.forms.SignUpForm;
import ru.itis.zheleznov.api.services.SignUpService;
import ru.itis.zheleznov.api.services.UserService;
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

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

  @Autowired
  private SignUpService signUpService;

  @Autowired
  private UserService userService;

  @ApiOperation(value = "Sign in to receive jwt token")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = JwtResponse.class)})
  @PostMapping("/signIn")
//  @PermitAll
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Optional<UserDto> userDto = userService.userByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

    if (userDto.isPresent()) {
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.get().getId());

      return ResponseEntity.ok(JwtResponse.builder()
              .refreshToken(refreshToken.getToken())
              .token(jwtUtils.generateJwtToken(userDto.get().getEmail()))
              .build());
    }

    return ResponseEntity.status(403).build();
  }

  @ApiOperation(value = "Sign up to sign in to receive jwt token")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = MessageResponse.class)})
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    SignUpForm signUpForm = SignUpForm.builder()
            .email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .passwordAgain(signUpRequest.getPassword())
            .build();

    if (signUpService.signUp(signUpForm)) {
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
  }

  @ApiOperation(value = "refresh token")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = TokenRefreshResponse.class)})
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
