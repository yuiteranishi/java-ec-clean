package com.example.ec.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest; // 静的/ H2 の matcher
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * パスワードハッシュ化用（BCryptを使うのが現在の実務的デファクト）
     * - ユーザー登録時に生パスワードをこのEncoderでハッシュ化して保存
     * - ログイン時は、入力値（生）を同じアルゴリズムで照合
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security のHTTP設定本体
     * 重要ポイント：
     * - /products/** を含む「公開に明示していないURI」はすべて認証必須
     * - ログイン成功後は /products に遷移
     * - ログアウトは POST /logout（CSRFトークン必須）
     * - CSRFは有効（H2コンソールを使う開発時だけ例外設定をコメント解除）
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ---- 認可ポリシー ----
                .authorizeHttpRequests(auth -> auth
                        // よくある静的リソース（/css, /js, /images 等）は全面許可
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // 公開ページ
                        .requestMatchers("/", "/login", "/register",
                                "/css/**", "/images/**").permitAll()

                        // H2コンソールも公開
                        .requestMatchers(PathRequest.toH2Console()).permitAll()

                        // 管理配下は ADMIN のみ
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // それ以外は認証必須（/products/** もここで保護される）
                        .anyRequest().authenticated()
                )

                // ---- フォームログイン ----
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/products", true) // 成功後は商品一覧へ
                        .permitAll()
                )

                // ---- ログアウト（POST /logout、CSRF トークン必須）----
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")   // ログアウト後の遷移先
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                // ---- CSRF ----
                .csrf(csrf -> csrf
                        // ★H2 コンソールを使う開発時だけ有効化（本番は不要）
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                )

        // ★H2 コンソールを使う開発時だけ同一オリジン iframe 許可（本番は不要）
        .headers(h -> h.frameOptions(f -> f.sameOrigin()))
        ;

        return http.build();
    }
}
