package ru.itis.zheleznov.api.validations.annotations;

import ru.itis.zheleznov.api.validations.validators.PasswordsMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {
    String message() default "Пароли не совпадают";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
