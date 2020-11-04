package ir.pb.services.menu;

import com.google.common.hash.Hashing;
import ir.pb.domains.Account;
import ir.pb.repositories.impl.AccountRepositoryImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountService {
    private final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(Account.class);
    private Account account;


    private String makeHash(String input) {
        String hashOfInput = Hashing.adler32()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
        return hashOfInput;
    }

    public Account signUp() {
        Scanner strIn = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        String userName = strIn.nextLine();
        System.out.print("Please enter your email address: ");
        String emailAddress = strIn.nextLine();
        account = accountRepository.findByEmailAddress(emailAddress);
        if (account == null) {
            System.out.print("Please enter a password :");
            String passWord = strIn.nextLine();
            System.out.print("Please enter your phone number :");
            String phoneNumber = strIn.nextLine();
            account = new Account(userName, passWord, emailAddress, phoneNumber);
            accountRepository.save(account);
        } else {
            System.err.println("SORRY THIS EMAIL HAS ALREADY REGISTERED");
            account = null;
        }
        return account;
    }

    public Account signIn() {
        Scanner strIn = new Scanner(System.in);
        System.out.print("Please enter your emailAddress :");
        String emailAddress = strIn.nextLine();
        System.out.print("Please enter your password :");
        String passWord = strIn.nextLine();
        account = accountRepository.findByEmailAddress(emailAddress);
        if (account == null) {
            System.err.println("EMAIL ADDRESS IS WRONG");
            account = null;
        } else if (account != null && !account.getPassWord().equals(makeHash(passWord))) {
            System.err.println("YOUR PASSWORD IS WRONG");
            account = null;
        } else {
            System.out.println("\\033[0;32mYOUR LOGGED IN\\033[0m");
        }
        return account;
    }

    private String giveMethodKey(HashMap<String, Method> hashMap) {
        HashMap<Integer, String> keysOfEditableFields = new HashMap<>();
        AtomicInteger i = new AtomicInteger(1);
        hashMap.keySet().stream().forEach(key -> keysOfEditableFields.put(i.getAndIncrement(), key));
        Scanner intIn = new Scanner(System.in);
        String keyForMethod = null;
        int choice = 0;
        try {
            System.out.println("Please choose one of the alternatives bellow :");
            keysOfEditableFields.keySet().stream().forEach(key -> System.out.println(key + ". " +
                    keysOfEditableFields.get(key)));
            choice = intIn.nextInt();
            keyForMethod = keysOfEditableFields.get(choice);
        } catch (Exception exception) {
            System.err.println("WRONG CHOICE PLEASE TRY AGAIN");
        }
        return keyForMethod;
    }

    public void accountSetting(Account account) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        HashMap<String, Method> editableFields = new HashMap<>();
        editableFields.put("User name", Account.class.getMethod("setUserName", String.class));
        editableFields.put("Password", Account.class.getMethod("setPassWord", String.class));
        editableFields.put("Phone number", Account.class.getMethod("setPhoneNumber", String.class));
        editableFields.put("Status", Account.class.getMethod("setStatus", String.class));
        String keyForMethod = giveMethodKey(editableFields);
        Method method = editableFields.get(keyForMethod);
        Scanner strIn = new Scanner(System.in);
        System.out.print("Please enter new " + keyForMethod + " :");
        String newField = strIn.nextLine();
        method.invoke(account, newField);
        accountRepository.save(account);
    }

    public void followSetting(Account account) throws NoSuchMethodException {
        Scanner strIn = new Scanner(System.in);
        Scanner intIn = new Scanner(System.in);
        System.out.println("Please choose one of the alternatives below :");
        System.out.println("1. Followings\n2. Followers");
        int choice = 0;
        try {
            choice = intIn.nextInt();
        } catch (Exception exception) {
            System.err.println("WRONG CHOICE PLEASE TRY AGAIN");
        }
        if (choice == 1) {
            System.out.println("1. Add an account\n2. Remove an account");
            int choice1 = 0;
            try {
                choice1 = intIn.nextInt();

            } catch (Exception exception) {
                System.err.println("SORRY WRONG CHOICE");
                followSetting(account);
            }
            if (choice1 == 1) {
                System.out.print("Enter the user name of the account you want to follow :");
                Account account1 = accountRepository.findByTitle(strIn.nextLine());
                if (account1 != null && !account.getFollowings().contains(account1)) {
                    accountRepository.addFollowing(account, account1);
                    accountRepository.addFollower(account1, account);
                    accountRepository.save(account);
                    accountRepository.save(account1);
                }
            } else if (choice1 == 2) {
                System.out.print("Enter the user name of the account you want to un follow :");
                Account account1 = accountRepository.findByTitle(strIn.nextLine());
                if (account1 != null && account.getFollowings().contains(account1)) {
                    accountRepository.deleteFollowing(account, account1);
                    accountRepository.deleteFollower(account1, account);
                    accountRepository.save(account);
                    accountRepository.save(account1);
                }
            }
        } else if (choice == 2) {
            AtomicInteger i = new AtomicInteger(1);
            HashMap<Integer, Account> followers = new HashMap<>();
            account.getFollowers().stream().forEach(follower -> followers.put(i.getAndIncrement(), follower));
            System.out.println("The accounts bellow are following you :");
            followers.keySet().stream().forEach(key -> System.out.println(key + ". " + followers.get(key).getUserName()));
            System.out.print("If you want to omit any, enter it's number. If not enter 0 :");
            int choice2 = 0;
            try {
                choice2 = intIn.nextInt();
            } catch (NoSuchElementException exception) {
                System.err.println("WRONG CHOICE, TRY AGAIN");
                followSetting(account);
            }
            if (choice2 == 0) {
                return;
            } else {
                accountRepository.deleteFollower(account, followers.get(choice));
                accountRepository.deleteFollowing(followers.get(choice), account);
                accountRepository.save(account);
                accountRepository.save(followers.get(choice));
            }
        }
    }


    public Account searchAnAccount() {
        Scanner strIn = new Scanner(System.in);
        System.out.println("Please enter the account you want to search :");
        String accountName = strIn.nextLine();
        Account account = accountRepository.findByTitle(accountName);
        if (account == null) {
            System.err.println("NO ACCOUNT FOUND");
        }
        return account;
    }


    public void finisher() {
        accountRepository.commitEntityManager();
    }
}
