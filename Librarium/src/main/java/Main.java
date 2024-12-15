import controller.LivroController;
import model.Livro;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LivroController livroController = new LivroController();

        // Adicionando 20 livros em cidades próximas a Monteiro (raio de 100km)
        System.out.println("Adicionando livros...");
        livroController.adicionarLivro("O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia", "Bom", -7.8939, -37.1199); // Monteiro
        livroController.adicionarLivro("1984", "George Orwell", "Distopia", "Ótimo", -7.8617, -37.0452); // Sertânia
        livroController.adicionarLivro("A Revolução dos Bichos", "George Orwell", "Satírico", "Regular", -7.8322, -36.9327); // Sumé
        livroController.adicionarLivro("Dom Casmurro", "Machado de Assis", "Romance", "Bom", -7.7418, -37.0578); // Camalaú
        livroController.adicionarLivro("Vidas Secas", "Graciliano Ramos", "Drama", "Excelente", -7.7256, -36.8978); // São José dos Cordeiros
        livroController.adicionarLivro("Grande Sertão: Veredas", "João Guimarães Rosa", "Ficção", "Ótimo", -8.0000, -37.2000); // Jataúba
        livroController.adicionarLivro("Memórias Póstumas de Brás Cubas", "Machado de Assis", "Clássico", "Bom", -8.0500, -37.0000); // Pesqueira
        livroController.adicionarLivro("Os Sertões", "Euclides da Cunha", "Histórico", "Ótimo", -8.1000, -37.3000); // Brejo da Madre de Deus
        livroController.adicionarLivro("A Moreninha", "Joaquim Manuel de Macedo", "Romance", "Bom", -7.9500, -36.8500); // Serra Branca
        livroController.adicionarLivro("Iracema", "José de Alencar", "Romance", "Regular", -8.2000, -37.1500); // Santa Cruz do Capibaribe
        livroController.adicionarLivro("O Primo Basílio", "José de Alencar", "Romance", "Bom", -7.9000, -37.0000); // Brejo do Cruz
        livroController.adicionarLivro("O Cortiço", "Aluísio Azevedo", "Naturalismo", "Ótimo", -7.8800, -37.0800); // Itaporanga
        livroController.adicionarLivro("Memórias de um Sargento de Milícias", "Manuel Antônio de Almeida", "Romance", "Bom", -7.9500, -37.0500); // Patos
        livroController.adicionarLivro("Senhora", "José de Alencar", "Romance", "Excelente", -7.8500, -37.0100); // Teixeira
        livroController.adicionarLivro("Marília de Dirceu", "Tomás Antônio Gonzaga", "Poemas", "Bom", -7.8200, -37.0900); // Princesa Isabel
        livroController.adicionarLivro("O Guarani", "José de Alencar", "Romance", "Regular", -7.7800, -37.1300); // São Sebastião do Umbuzeiro
        livroController.adicionarLivro("Iracema", "José de Alencar", "Romance", "Ótimo", -8.1000, -37.0800); // Arcoverde
        livroController.adicionarLivro("O Ateneu", "Raul Pompeia", "Romance", "Bom", -8.0500, -37.2000); // Garanhuns
        livroController.adicionarLivro("A Moreninha", "Joaquim Manuel de Macedo", "Romance", "Regular", -7.9100, -37.1500); // Pedra Branca
        livroController.adicionarLivro("O Primo Basílio", "José de Alencar", "Romance", "Bom", -7.9500, -37.1800); // Cacimba de Areia


        // Listando livros cadastrados
        System.out.println("\nListando livros...");
        List<Livro> livros = livroController.listarLivros();
        if (livros != null) {
            for (Livro livro : livros) {
                System.out.println("ID: " + livro.getId() + ", Título: " + livro.getTitulo() +
                        ", Autor: " + livro.getAutor() + ", Estado de Conservação: " + livro.getEstadoConservacao() +
                        ", Localização: (" + livro.getLocalizacao().getY() + ", " + livro.getLocalizacao().getX() + ")");
            }
        }

        // Buscando livros em um raio de 30km a partir de Monteiro (-7.8939, -37.1199)
        double latitudeUsuario = -7.8939;
        double longitudeUsuario = -37.1199;
        double raioBusca = 10000; // km em metros

        System.out.println("\nBuscando livros em um raio de " + (raioBusca / 1000) + "km de Monteiro...");
        List<Livro> livrosProximos = livroController.filtrarPorRaio(latitudeUsuario, longitudeUsuario, raioBusca);
        if (livrosProximos != null && !livrosProximos.isEmpty()) {
            System.out.println("Livros encontrados:");
            for (Livro livro : livrosProximos) {
                System.out.println("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() +
                        ", Localização: (" + livro.getLocalizacao().getY() + ", " + livro.getLocalizacao().getX() + ")");
            }
        } else {
            System.out.println("Nenhum livro encontrado no raio especificado.");
        }

        // Atualizando um livro (exemplo de ID 1)
        System.out.println("\nAtualizando um livro...");
        livroController.atualizarLivro(1L, "O Senhor dos Anéis - Edição Especial", "J.R.R. Tolkien", "Fantasia", "Ótimo", -7.8939, -37.1199);

        // Listando livros após atualização
        System.out.println("\nListando livros após atualização...");
        livros = livroController.listarLivros();
        if (livros != null) {
            for (Livro livro : livros) {
                System.out.println("ID: " + livro.getId() + ", Título: " + livro.getTitulo() +
                        ", Autor: " + livro.getAutor() + ", Estado de Conservação: " + livro.getEstadoConservacao() +
                        ", Localização: (" + livro.getLocalizacao().getY() + ", " + livro.getLocalizacao().getX() + ")");
            }
        }

        // Removendo um livro (exemplo de ID 1)
        System.out.println("\nRemovendo um livro...");
        livroController.removerLivro(1L);

        // Listando livros após remoção
        System.out.println("\nListando livros após remoção...");
        livros = livroController.listarLivros();
        if (livros != null) {
            for (Livro livro : livros) {
                System.out.println("ID: " + livro.getId() + ", Título: " + livro.getTitulo() +
                        ", Autor: " + livro.getAutor() + ", Estado de Conservação: " + livro.getEstadoConservacao() +
                        ", Localização: (" + livro.getLocalizacao().getY() + ", " + livro.getLocalizacao().getX() + ")");
            }
        }
    }
}
