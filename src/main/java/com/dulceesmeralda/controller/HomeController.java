package com.dulceesmeralda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "acceso_denegado";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}
