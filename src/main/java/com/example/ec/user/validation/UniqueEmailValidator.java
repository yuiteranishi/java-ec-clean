package com.example.ec.user.validation;

import com.example.ec.domain.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * @ UniqueEmail が付いたフィールド（String）に対し、
 * UserRepository を用いて「既に同じメールが存在しないか」を確認
 */
@Component // Spring のコンポーネントスキャン対象にして依存注入できるようにする
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepository repo;
    public UniqueEmailValidator(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return !repo.existsByEmail(value);
    }

}
