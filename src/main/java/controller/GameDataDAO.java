package controller;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class GameDataDAO {
    static private GameDataDAO instance;
    static private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    private GameDataDAO() {
    }

    public static GameDataDAO getInstance(){
        if (instance == null) {
            instance = new GameDataDAO();
            instance.setEntityManager(Persistence.createEntityManagerFactory("org.hibernate.mysql.jpa").createEntityManager());
        }
        return instance;
    }

    public void persist(GameData gameData){
        entityManager.getTransaction().begin();
        entityManager.persist(gameData);
        entityManager.getTransaction().commit();
    }

    public List<GameData> listData(int n, BoardType boardType) {
        return entityManager.createQuery("SELECT d FROM GameData d WHERE d.boardType = " + boardType.ordinal() +" ORDER BY d.remainingMarbles, d.duration DESC", GameData.class)
                .setMaxResults(n)
                .getResultList();
    }

}
