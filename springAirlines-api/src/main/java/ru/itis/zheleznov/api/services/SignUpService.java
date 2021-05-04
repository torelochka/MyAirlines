package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.forms.SignUpForm;

public interface SignUpService {
    Boolean signUp(SignUpForm signUpForm);
}
