package com.example.musica;

import com.example.musica.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MusicaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MusicaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibirMenu();
    }
}
