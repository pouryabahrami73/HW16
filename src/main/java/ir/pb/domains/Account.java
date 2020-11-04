package ir.pb.domains;


import com.google.common.hash.Hashing;
import ir.pb.base.domians.BaseEntity;


import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account extends BaseEntity<Long> {
    @Column
    private String userName;
    @Column
    private String passWord;
    @Column
    private String phoneNumber;
    @Column
    private String status;
    @Column
    private String emailAddress;
    @ManyToMany
    @JoinTable(name = "account_follower", joinColumns = {@JoinColumn(name = "fk_account")},
            inverseJoinColumns = {@JoinColumn(name = "fk_follower")})
    private List<Account> followers = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "account_following", joinColumns = {@JoinColumn(name = "fk_account")},
            inverseJoinColumns = {@JoinColumn(name = "fk_following")})
    private List<Account> followings = new ArrayList<>();

    public Account() {
    }

    public Account(String userName, String passWord, String emailAddress, String phoneNumber) {
        this.userName = userName;
        this.passWord = Hashing.adler32()
                .hashString(passWord, StandardCharsets.UTF_8)
                .toString();
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUserName() {
        return userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = Hashing.adler32()
                .hashString(passWord, StandardCharsets.UTF_8)
                .toString();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(Account follower) {
        this.followers.add(follower);
    }

    public void deleteFollowers(Account follower) {
        this.followers.remove(follower);
    }

    public List<Account> getFollowings() {
        return followings;
    }

    public void setFollowings(Account following) {
        this.followings.add(following);
    }

    public void deleteFollowing(Account following) {
        this.followings.remove(following);
    }
}