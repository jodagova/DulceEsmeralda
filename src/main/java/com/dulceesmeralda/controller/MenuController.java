package com.dulceesmeralda.controller;

import com.dulceesmeralda.service.CategoriaService;
import com.dulceesmeralda.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public MenuController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/menu")
    public String menu(@RequestParam(value = "q", required = false) String q, Model model) {
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        model.addAttribute("productos",
                (q == null || q.isBlank()) ? productoService.getProductos(true) : productoService.buscar(q));
        model.addAttribute("q", q);
        return "menu/listado";
    }

    @GetMapping("/menu/categoria/{idCategoria}")
    public String porCategoria(@PathVariable Integer idCategoria, Model model) {
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        model.addAttribute("productos", productoService.getPorCategoria(idCategoria));
        model.addAttribute("categoriaActual", categoriaService.getCategoria(idCategoria).orElse(null));
        return "menu/listado";
    }
}
