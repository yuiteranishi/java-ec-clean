package com.example.ec.user.web;

import com.example.ec.user.application.RegistrationService;
import com.example.ec.user.web.form.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class AuthController {
    private final RegistrationService registrationService;
    public AuthController(RegistrationService rs){ this.registrationService = rs; }

    /**
     * ログイン画面（GET）
     * 既にログイン済みであれば、/login を見せず /products にリダイレクト
     */
    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/products";
        }
        return "auth/login";
    }

    /**
     * 新規登録フォーム（GET）
     * - 画面で使う th:object="${form}" のために空フォームをモデルに積む
     */
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("form", new RegisterForm("", "", "", ""));
        return "auth/register";
    }

    /**
     * 新規登録（POST）
     * - @Valid でサーバサイドバリデーション
     * - BindingResult は @Valid の直後に置く（順序が違うと拾えない）
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterForm form,
                           BindingResult br){
        if (br.hasErrors()) return "auth/register";
        registrationService.registerUser(form.email(), form.password(), form.displayName());
        return "redirect:/login?registered";
    }
}
