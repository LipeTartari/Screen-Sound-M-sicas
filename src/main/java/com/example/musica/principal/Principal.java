package com.example.musica.principal;

import com.example.musica.model.Artista;
import com.example.musica.model.Musica;
import com.example.musica.model.TipoArtista;
import com.example.musica.reposiitory.ArtistaRepository;
import com.example.musica.service.ConsultaChatGPT;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final ArtistaRepository artistaRepository;
    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public void exibirMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarMusicas() {
        List<Artista> artista = artistaRepository.findAll();
        artista.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";

        while(cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("informe o nome do artista: ");
            var nome = leitura.nextLine();
            System.out.println("informe o tipo desse artista(solo, dupla ou banda): ");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            artistaRepository.save(artista);
            System.out.println("deseja cadastrar outro artista?(S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }
    private void cadastrarMusicas(){
        System.out.println("musica de qual artista?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = artistaRepository.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()){
            System.out.println("qual vai ser o nome da musica?");
            var musicaNome = leitura.nextLine();
            Musica musica = new Musica(musicaNome);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            artistaRepository.save(artista.get());
        }else {
            System.out.println("esse artista nao existe ainda");
        }

    }
    private void buscarMusicasPorArtista(){
        System.out.println("qual musica para achar a artista?");
        var nome = leitura.nextLine();
        List<Musica> musicas = artistaRepository.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }
    private void pesquisarDadosDoArtista(){
        System.out.println("quer pesquisar os dados de qual artista?");
        var artista = leitura.nextLine();
        var resposta = ConsultaChatGPT.obterInformacao(artista);
        System.out.println(resposta.trim());
    }
}