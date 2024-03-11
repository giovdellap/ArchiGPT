package it.uniroma1.commons.entity;

import it.uniroma1.commons.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Fine> fines = new ArrayList<>();

    @Column(name = "region")
    private String region;

    @Column(name = "passwordSalt")
    private String passwordSalt;

    @Column(name = "passwordHash")
    private String passwordHash;

    @Column(name = "role")
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "creator")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="creator_id",referencedColumnName = "username")
    private User creator;

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public boolean checkRegion(String region) {

        return this.region.equals(region);
    }

    
}