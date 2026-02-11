package com.example.ec.user.security;

import com.example.ec.user.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * Spring Security がログイン時に呼び出す「ユーザー読み出し」サービス
  * ログインフォームの username（= email）を受け取り、DBのユーザーを UserDetails に変換する
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // email でユーザー検索、見つからなければ例外（= 認証失敗）
        var u = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        // Spring Security 用の UserDetails に詰め替え
        return User.withUsername(u.getDisplayName())
                .password(u.getPasswordHash())
                .roles(u.getRole().name()) // "ROLE_" 自動付与
                .disabled(!Boolean.TRUE.equals(u.getEnabled())) // ユーザーが有効
                .build();
    }
}
