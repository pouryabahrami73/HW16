package ir.pb.repositories.impl;


import ir.pb.base.repositories.impl.BaseRepositoryImpl;
import ir.pb.domains.Account;
import ir.pb.repositories.AccountRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Account, Long> implements AccountRepository {

    public AccountRepositoryImpl(Class<Account> clazz) {
        super(clazz);
    }

    @Override
    public Account findByEmailAddress(String emailAddress) {
        Account account = null;
        TypedQuery<Account> query = super.entityManager.createQuery("SELECT a FROM Account a WHERE a.emailAddress =:" +
                        "emailAddress", Account.class);
        try {
            account = query.setParameter("emailAddress", emailAddress).getSingleResult();
            System.err.println("EMAIL HAS ALREADY VERIFIED");
        }catch (NoResultException noSuchElementException){
            System.out.println("EMAIL IS OK");
            account = null;
        }
        return account;
    }
    @Override
    public void deleteFollower(Account account, Account follower) {
        account.deleteFollowers(follower);
    }
    @Override
    public void deleteFollowing(Account account, Account following){
        account.deleteFollowing(following);
    }

    @Override
    public void addFollowing(Account account, Account following) {
        account.setFollowings(following);
    }

    @Override
    public void addFollower(Account account, Account follower) {
        account.setFollowers(follower);
    }
}
