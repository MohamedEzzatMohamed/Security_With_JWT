package com.mezzat.security_with_jwt.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

}
