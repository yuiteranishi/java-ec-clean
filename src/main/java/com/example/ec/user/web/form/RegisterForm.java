package com.example.ec.user.web.form;

import com.example.ec.user.validation.PasswordMatches;
import com.example.ec.user.validation.UniqueEmail;
import jakarta.validation.constraints.*;

/**
 * ユーザー登録の入力DTO（画面の値を受け取る箱）
 * - エンティティに直接バリデーションを書くのではなく、
 *   画面入力の段階でもっとも手前で弾くのがUX的に望ましい
 */
@PasswordMatches
public record RegisterForm(
        // 必須・メール形式・重複不可
        @Email
        @NotBlank
        @UniqueEmail
        String email,

        // BCryptの上限72文字に合わせ、最低8文字
        // 英大文字/英小文字/数字のうち2種以上を含む
        @NotBlank
        @Size(min=8, max=72)
        @Pattern(
                // 2条件のいずれかを満たす（大+小 / 大+数 / 小+数）
                regexp = "^(?:(?=.*[a-z])(?=.*[A-Z])|(?=.*[a-z])(?=.*\\d)|(?=.*[A-Z])(?=.*\\d)).{8,72}$",
                message = "{validation.password.weak}"
        )
        String password,

        // 必須、等価性のチェック自体は @PasswordMatches 側で行う
        @NotBlank
        String confirmPassword,

        @NotBlank
        @Size(max=50)
        // 必須 + DB上限
        String displayName
) {}
