import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
//            UsersEntity users = new UsersEntity();
//            //users.setId(10);
//            users.setName("Nickname123");
//            users.setLogin("hairdresser5000");
//            users.setPassword("pass34");
//            entityManager.persist(users);
//            TypedQuery<UsersEntity> userByMyQuery = entityManager.createNamedQuery("myQuery", UsersEntity.class);
//            userByMyQuery.setParameter(1, "login1");
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
