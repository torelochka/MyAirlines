package ru.itis.zheleznov.api.validations.validators;

import ru.itis.zheleznov.api.forms.SignUpForm;
import ru.itis.zheleznov.api.validations.annotations.PasswordsMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, SignUpForm> {

    @Override
    public boolean isValid(SignUpForm signUpForm, ConstraintValidatorContext constraintValidatorContext) {
        return signUpForm.getPasswordAgain().equals(signUpForm.getPassword());
    }
}
