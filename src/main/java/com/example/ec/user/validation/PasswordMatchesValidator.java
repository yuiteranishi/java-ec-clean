package com.example.ec.user.validation;

import com.example.ec.dto.user.form.RegisterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * RegisterForm 全体を受け取り、
 * password と confirmPassword の一致をチェック
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterForm> {

    @Override
    public boolean isValid(RegisterForm value, ConstraintValidatorContext context) {
        if (value == null) return true;
        var p1 = value.password();
        var p2 = value.confirmPassword();
        if (p1 == null || p2 == null) return true;
        return p1.equals(p2);
    }
}
