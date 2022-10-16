package com.onlineenergyutilityplatform.db.model;

import com.onlineenergyutilityplatform.utilities.RoleConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to define User db entity
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "role", nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Device> deviceList;

    public User(String name, Role role, String username, String password) {
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
        this.deviceList = new ArrayList<>();
    }
}
