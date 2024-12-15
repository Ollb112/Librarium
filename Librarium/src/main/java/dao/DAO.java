package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class DAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("Librarium");

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

    protected void handlePersistenceException(Exception e, String message) throws PersistenciaDacException {
        throw new PersistenciaDacException(message, e);
    }
}
