package ru.itis.zheleznov.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.zheleznov.api.forms.SignUpForm;
import ru.itis.zheleznov.api.services.SignUpService;
import ru.itis.zheleznov.impl.models.User;
import ru.itis.zheleznov.impl.repositories.UserRepository;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean signUp(SignUpForm signUpForm) {
        if (userRepository.findByEmail(signUpForm.getEmail()).isPresent()) {
            return false;
        }

        User user = User.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .build();

        userRepository.save(user);

        return true;
    }
}
