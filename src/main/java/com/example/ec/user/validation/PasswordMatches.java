package com.example.ec.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * password と confirmPassword が一致しているかを検証する
 * RegisterForm のクラスに付与して使用する
 */
@Target(ElementType.TYPE) // クラスやレコードに付ける
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {
    // Bean Validationのカスタム制約に必須の3つ
    String message() default "{validation.password.mismatch}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
