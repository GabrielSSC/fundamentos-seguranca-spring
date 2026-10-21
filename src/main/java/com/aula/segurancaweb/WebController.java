package com.aula.segurancaweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final List<String> comentarios = new ArrayList<>();
    private double saldo = 1000.00;
    private static final int TAMANHO_MAXIMO_COMENTARIO = 200;

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
        if (texto != null && !texto.trim().isEmpty()) {
            // 3. Validação de tamanho máximo
            if (texto.length() > TAMANHO_MAXIMO_COMENTARIO) {
                texto = texto.substring(0, TAMANHO_MAXIMO_COMENTARIO);
            }
            // 1. Sanitização com HtmlUtils.htmlEscape()
            String textoSanitizado = HtmlUtils.htmlEscape(texto.trim());
            
            comentarios.add(textoSanitizado);
        }
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