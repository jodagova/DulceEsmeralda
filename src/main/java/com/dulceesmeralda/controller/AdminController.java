package com.dulceesmeralda.controller;

import com.dulceesmeralda.service.CategoriaService;
import com.dulceesmeralda.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public AdminController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalProductos", productoService.getProductos(false).size());
        model.addAttribute("totalCategorias", categoriaService.getCategorias(false).size());
        model.addAttribute("productos", productoService.getProductos(false));
        return "admin/dashboard";
    }
}
