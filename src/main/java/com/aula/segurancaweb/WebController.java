package com.aula.segurancaweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final List<String> comentarios = new ArrayList<>();
    private double saldo = 1000.00;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("saldo", saldo);
        model.addAttribute("comentarios", comentarios);
        return "dashboard";
    }

    @PostMapping("/comentario")
    public String comentario(@RequestParam("texto") String texto, Model model) {
        comentarios.add(texto);
        return "redirect:/dashboard";
    }

    @PostMapping("/transferir")
    @ResponseBody
    public String transferir(@RequestParam("destino") String destino,
                             @RequestParam("valor") double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            return "Transferido " + valor + " para " + destino + ". Saldo: " + saldo;
        }
        return "Saldo insuficiente ou valor inválido.";
    }

    @GetMapping("/pesquisar")
    @ResponseBody
    public String pesquisar(@RequestParam("q") String query) {
        if (query.matches("(a|aa)+b")) {
            return "Resultado: padrão A encontrado";
        }
        return "Nenhum resultado para: " + query;
    }

    @GetMapping("/contato")
    public String contato() {
        return "contato";
    }
}