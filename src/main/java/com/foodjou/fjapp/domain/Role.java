package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "role", nullable = false,unique = true, length = 50)
    private String name;

    public Role(Long id) {
        super();
        this.id = id;
    }

    public Role() {
    }

    public Role(String name) {
        super();
        this.name = name;
    }
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return name;
    }
}
