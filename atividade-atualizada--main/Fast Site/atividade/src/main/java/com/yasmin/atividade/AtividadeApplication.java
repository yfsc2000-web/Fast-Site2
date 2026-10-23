package com.yasmin.atividade;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AtividadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtividadeApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner criarUsuario(UsuarioRepository repo, BCryptPasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmail("yasmin@email.com").isEmpty()) {
                Usuario u = new Usuario();
                u.setEmail("yasmin@email.com");
                u.setSenha(encoder.encode("123456"));
                repo.save(u);
                System.out.println("Usuário criado!");
            }
        };
    }
}