package com.kruger.test.enitity;

import com.kruger.test.enitity.AbstractEntity;
import com.kruger.test.enitity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_USERNAME", columnNames = {"username"})
})
@NoArgsConstructor
public class User extends AbstractEntity {

    @NotNull
    @Column(name="username", unique = true)
    private String username;

    @NotNull
    @Column(name="password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    @OneToOne(optional = false)
    @JoinColumn(name="person_id", referencedColumnName="id")
    private Person person;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
