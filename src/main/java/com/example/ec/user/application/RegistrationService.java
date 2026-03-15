package com.example.ec.user.application;

import com.example.ec.domain.user.User;
import com.example.ec.domain.user.UserRole;
import com.example.ec.domain.user.UserRepository;
import com.example.ec.shared.mail.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
  * 新規登録のアプリケーションサービス
  * - バリデーションは Controller(@Valid) と UniqueEmail で弾く
  * - 二重防御として existsByEmail でも重複チェック
  * - パスワードは BCrypt でハッシュ化して保存
  * - Mailtrap へウェルカムメール送信（検証用）
  */
@Service
public class RegistrationService {
    private final UserRepository repo;
    private final PasswordEncoder pe;
    private final MailService mail;

    public RegistrationService(UserRepository repo, PasswordEncoder pe, MailService mail){
        this.repo = repo;
        this.pe = pe;
        this.mail = mail;
    }

    @Transactional
    public void registerUser(String email, String rawPassword, String displayName){
        if (repo.existsByEmail(email)) throw new IllegalArgumentException("Email already used");
        var u = new User();
        u.setEmail(email);
        u.setPasswordHash(pe.encode(rawPassword)); // BCryptでハッシュ化
        u.setDisplayName(displayName);
        u.setRole(UserRole.USER);
        u.setEnabled(true);
        repo.save(u);

        // Mailtrapへ確認メール
        mail.send(email, "Welcome to EC Practice",
                "Hi " + displayName + "!");
    }
}
