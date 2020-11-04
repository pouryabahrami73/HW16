package java.ir.pb.repositories.impl;


import ir.pb.domains.Account;
import ir.pb.repositories.impl.AccountRepositoryImpl;
import ir.pb.services.impl.EntityManagerMaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;





class AccountRepositoryImplTest {
    EntityManager entityManager = EntityManagerMaker.getEntityManager();
    TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.emailAddress =" +
                    "\'pooria.bahrami73@gmail.com\'", Account.class);
    Account account = query.getSingleResult();
    /*@BeforeAll
    static void beforeAll(){
        EntityManager entityManager = EntityManagerMaker.getEntityManager();
        entityManager.getTransaction().begin();
        Account account = new Account("pourya", "1234asd", "pooria.bahrami73@gmail.com",
                "09122154172");
        Account account2 = new Account("ali", "1234asd", "ali.vahedi60@gmail.com",
                "09123434344");
        Account account3 = new Account("mahmood", "1234asd", "m.javaheri@yahoo.com",
                "09121212122");
        Account account4 = new Account("saman", "1234asd", "sgooran@hotmail.com",
                "09125656565");
        account.setFollowers(account2);
        account.setFollowers(account3);
        account.setFollowers(account4);
        account.setFollowings(account2);
        account2.setFollowers(account3);
        account2.setFollowings(account3);
        account3.setFollowings(account4);
        account4.setFollowers(account2);
        account.setStatus("I am pourya");
        account2.setStatus("I am ali");
        account3.setStatus("I am mahmood");
        account4.setStatus("I am saman");
        entityManager.persist(account);
        entityManager.persist(account2);
        entityManager.persist(account3);
        entityManager.persist(account4);
        entityManager.getTransaction().commit();
    }*/

    @Test
    void findByEmailAddress() {
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(Account.class);
        Assertions.assertEquals(account, accountRepository.findByEmailAddress("pooria.bahrami73@gmail.com"));
    }

    @Test
    void findById(){
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(Account.class);
        Assertions.assertEquals(account, accountRepository.findById(1L));
    }

    @Test
    void deleteFollower() {
    }

    @Test
    void deleteFollowing() {
    }

    @Test
    void addFollowing() {
    }

    @Test
    void addFollower() {
    }

    @Test
    void addPost() {
    }

    @Test
    void deletePost() {
    }
}