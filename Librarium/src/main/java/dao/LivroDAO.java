package dao;

import jakarta.persistence.Query;
import model.Livro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class LivroDAO extends DAO {

    public void salvar(Livro livro) throws PersistenciaDacException {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(livro);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            handlePersistenceException(e, "Erro ao tentar salvar o livro.");
        } finally {
            em.close();
        }
    }

    public Livro buscarPorId(Long id) throws PersistenciaDacException {
        EntityManager em = getEntityManager();
        try {
            return em.find(Livro.class, id);
        } catch (PersistenceException e) {
            handlePersistenceException(e, "Erro ao tentar recuperar o livro com o ID: " + id);
        } finally {
            em.close();
        }
        return null;
    }

    public List<Livro> listarTodos() throws PersistenciaDacException {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("FROM Livro", Livro.class).getResultList();
        } catch (PersistenceException e) {
            handlePersistenceException(e, "Erro ao tentar listar todos os livros.");
        } finally {
            em.close();
        }
        return null;
    }

    public void atualizar(Livro livro) throws PersistenciaDacException {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(livro);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            handlePersistenceException(e, "Erro ao tentar atualizar o livro.");
        } finally {
            em.close();
        }
    }
    public List<Livro> buscarPorRaio(double latitude, double longitude, double raioMetros) throws PersistenciaDacException {
        EntityManager em = getEntityManager();
        try {
            // Usando query nativa para trabalhar com ST_DWithin
            String sql = "SELECT * FROM livros WHERE ST_DWithin(localizacao, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :raio)";
            Query query = em.createNativeQuery(sql, Livro.class);
            query.setParameter("latitude", latitude);
            query.setParameter("longitude", longitude);
            query.setParameter("raio", raioMetros);

            return query.getResultList();
        } catch (PersistenceException e) {
            handlePersistenceException(e, "Erro ao buscar livros por raio.");
        } finally {
            em.close();
        }
        return null;
    }

    public void remover(Long id) throws PersistenciaDacException {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Livro livro = em.find(Livro.class, id);
            if (livro != null) {
                em.remove(livro);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            handlePersistenceException(e, "Erro ao tentar remover o livro com ID: " + id);
        } finally {
            em.close();
        }
    }
}