package com.yasmin.atividade;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AppController {

    private final UsuarioRepository usuarios;
    private final CadastroRepository cadastros;
    private final BCryptPasswordEncoder encoder;

    public AppController(UsuarioRepository usuarios, CadastroRepository cadastros,
                         BCryptPasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.cadastros = cadastros;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login.html";
    }

    @GetMapping("/login.html")
    public String telaLogin() {
        return "login";
    }

    @GetMapping("/cadastro.html")
    public String telaCadastro() {
        return "cadastro";
    }
    @PostMapping("/entrar")
    public String entrar(@RequestParam String email, @RequestParam String senha, Model model) {
        Optional<Usuario> usuario = usuarios.findByEmail(email);

        if (usuario.isPresent() && encoder.matches(senha, usuario.get().getSenha())) {
            return "redirect:/cadastro.html";
        }
        model.addAttribute("texto", "E-mail ou senha incorretos.");
        model.addAttribute("voltar", "/login.html");
        return "mensagem";
    }
    @PostMapping("/salvar")
    public String salvar(Cadastro cadastro) {
        cadastros.save(cadastro);
        return "sucesso";
    }
    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("lista", cadastros.findAll());
        return "lista";
    }

    @GetMapping("/ver")
    public String ver(@RequestParam Long id, Model model) {
        Optional<Cadastro> cadastro = cadastros.findById(id);

        if (cadastro.isEmpty()) {
            model.addAttribute("texto", "Cadastro não encontrado.");
            model.addAttribute("voltar", "/lista");
            return "mensagem";
        }
        model.addAttribute("c", cadastro.get());
        return "ver";
    }
}