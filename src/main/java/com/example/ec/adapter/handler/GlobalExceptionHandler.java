package com.example.ec.adapter.handler;

import com.example.ec.domain.product.exception.CategoryNotFoundException;
import com.example.ec.domain.product.exception.ProductNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException ex, Model model)
    {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("pageTitle", "404 - ページが見つかりません");
        return "error/404";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleCategoryNotFound(CategoryNotFoundException ex, Model model)
    {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("pageTitle", "404 - ページが見つかりません");
        return "error/404";
    }
}
