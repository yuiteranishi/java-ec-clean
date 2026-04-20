package com.example.ec.adapter.handler;

import com.example.ec.domain.product.exception.CategoryNotFoundException;
import com.example.ec.domain.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductNotFound(ProductNotFoundException ex, Model model)
    {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("pageTitle", "404 - ページが見つかりません");
        return "error/404";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCategoryNotFound(CategoryNotFoundException ex, Model model)
    {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("pageTitle", "404 - ページが見つかりません");
        return "error/404";
    }
}
