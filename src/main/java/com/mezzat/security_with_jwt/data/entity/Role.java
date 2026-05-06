package com.mezzat.security_with_jwt.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    private Set<User> users;


    @Override
    public String getAuthority() {
        return name;
    }
}
