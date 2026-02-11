package com.example.ec.product.web;

import com.example.ec.product.application.ProductQueryUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductPageController {

    private final ProductQueryUseCase useCase;

    public ProductPageController(ProductQueryUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public String index(@RequestParam(name = "category", required = false) String category, Model model) {
        // カテゴリ一覧
        model.addAttribute("categories", useCase.listVisibleCategories());
        // 商品一覧
        model.addAttribute("products", useCase.list(category));
        // 選択カテゴリ
        model.addAttribute("selectedCategory", category);
        return "product/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // 商品詳細
        model.addAttribute("product", useCase.getOr404(id));
        return "product/detail";
    }
}
