package com.example.ec.product.web;

import com.example.ec.category.repository.CategoryRepository;
import com.example.ec.product.application.AdminProductUseCase;
import com.example.ec.product.form.AdminProductForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    private final AdminProductUseCase useCase;
    private final CategoryRepository categoryRepo;

    public AdminProductController (AdminProductUseCase useCase, CategoryRepository categoryRepo) {
        this.useCase = useCase;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("products", useCase.listAll());
        return "admin/product/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", useCase.newForm());
        model.addAttribute("categories", categoryRepo.findAllByIsVisibleTrueOrderBySortOrderAscNameAsc());
        return "admin/product/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("form") AdminProductForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAllByIsVisibleTrueOrderBySortOrderAscNameAsc());
        return  "admin/product/form";
        }
        Long id = useCase.create(form);
        return "redirect:/admin/products?created=" + id;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("form", useCase.loadForm(id));
        model.addAttribute("categories", categoryRepo.findAllByIsVisibleTrueOrderBySortOrderAscNameAsc());
        return "admin/product/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute AdminProductForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAllByIsVisibleTrueOrderBySortOrderAscNameAsc());
            return "admin/product/form";
        }
        useCase.update(id, form);
        return "redirect:/admin/products?updated=" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        useCase.delete(id);
        return "redirect:/admin/products?deleted=" + id;
    }
}
