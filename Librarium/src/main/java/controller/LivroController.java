package controller;

import dao.LivroDAO;
import dao.PersistenciaDacException;
import model.Livro;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class LivroController {
    private LivroDAO livroDAO;

    public LivroController() {
        this.livroDAO = new LivroDAO();
    }

    public void adicionarLivro(String titulo, String autor, String genero, String estadoConservacao, double latitude, double longitude) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setGenero(genero);
        livro.setEstadoConservacao(estadoConservacao);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point localizacao = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        livro.setLocalizacao(localizacao);

        try {
            livroDAO.salvar(livro);
        } catch (PersistenciaDacException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> listarLivros() {
        try {
            return livroDAO.listarTodos();
        } catch (PersistenciaDacException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Livro buscarLivro(Long id) {
        try {
            return livroDAO.buscarPorId(id);
        } catch (PersistenciaDacException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Livro> filtrarPorRaio(double latitudeUsuario, double longitudeUsuario, double raioBusca) {
        List<Livro> todosLivros = listarLivros(); // Obtém todos os livros disponíveis
        List<Livro> livrosNoRaio = new ArrayList<>();

        if (todosLivros != null) {
            for (Livro livro : todosLivros) {
                if (livro.getLocalizacao() != null) {
                    double latitudeLivro = livro.getLocalizacao().getY(); // Latitude de Point
                    double longitudeLivro = livro.getLocalizacao().getX(); // Longitude de Point
                    double distancia = calcularDistancia(latitudeUsuario, longitudeUsuario, latitudeLivro, longitudeLivro);

                    if (distancia <= raioBusca) {
                        livrosNoRaio.add(livro);
                    }
                }
            }
        }
        return livrosNoRaio;
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Raio da Terra em metros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public void atualizarLivro(Long id, String titulo, String autor, String genero, String estadoConservacao, double latitude, double longitude) {
        try {
            Livro livro = livroDAO.buscarPorId(id);
            if (livro != null) {
                livro.setTitulo(titulo);
                livro.setAutor(autor);
                livro.setGenero(genero);
                livro.setEstadoConservacao(estadoConservacao);

                GeometryFactory geometryFactory = new GeometryFactory();
                Point localizacao = geometryFactory.createPoint(new Coordinate(longitude, latitude));
                livro.setLocalizacao(localizacao);

                livroDAO.atualizar(livro);
            }
        } catch (PersistenciaDacException e) {
            e.printStackTrace();
        }
    }

    public void removerLivro(Long id) {
        try {
            livroDAO.remover(id);
        } catch (PersistenciaDacException e) {
            e.printStackTrace();
        }
    }
}
