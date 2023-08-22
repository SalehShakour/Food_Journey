package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "description")
    private String description;
}
