package com.example.ec.application.user;

import com.example.ec.domain.user.User;
import com.example.ec.domain.user.UserRepository;
import com.example.ec.domain.user.UserRole;
import com.example.ec.domain.user.port.MailPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    private UserRepository userRepo;
    private MailPort mailPort;
    private RegistrationService service;

    // テストごとに毎回リセット
    @BeforeEach
    void setUp()
    {
        userRepo = mock(UserRepository.class);
        mailPort = mock(MailPort.class);
        var passwordEncoder = new BCryptPasswordEncoder();
        service = new RegistrationService(userRepo, passwordEncoder, mailPort);
    }

    @Test
    @DisplayName("新規登録：ユーザーが保存され、メールが送信される")
    void registerUser_正常()
    {
        when(userRepo.existsByEmail("admin@example.com")).thenReturn(false);

         // User を受け取って User を返却
         // any(User.class) ：User型にマッチ = saveにUserのインスタンスが渡されたら反応
         // thenAnswer      ：呼び出された情報を使って動的に返却
         // inv             ：呼び出し情報 = inv.getArgument(0) は0番目の引数をそのまま返却
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        service.registerUser("admin@example.com", "password1!", "テスト管理者");

        // verify(モック, 何回呼ばれたか).メソッド(引数の条件);
        // argThat：この条件を満たす引数で呼ばれたか
        verify(userRepo, times(1)).save(argThat(u ->
                u.getEmail().equals("admin@example.com") &&
                u.getDisplayName().equals("テスト管理者") &&
                u.getRole().equals(UserRole.USER) &&
                        Boolean.TRUE.equals(u.getEnabled()) &&
                        !u.getPasswordHash().equals("password1!")
        ));

        // send の引数は文字型なので argThat を使用しない
        // eq      ：完全一致
        // contains：部分一致
        verify(mailPort, times(1)).send(
                eq("admin@example.com"),
                eq("Welcome to EC Practice"),
                contains("テスト管理者")
        );
    }
    @Test
    @DisplayName("重複：IllegalArgumentException が投げられ、保存もメール送信もされない")
    void registerUser_重複() {
        when(userRepo.existsByEmail("admin@example.com")).thenReturn(true);

        // assertThatThrownBy：この処理で例外が投げられること
        assertThatThrownBy(() ->
                service.registerUser("admin@example.com", "password1", "テスト管理者")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already used");

        // never：一度も呼ばれていないことを確認
        verify(userRepo, never()).save(any());
        verify(mailPort, never()).send(any(), any(), any());
    }

}
