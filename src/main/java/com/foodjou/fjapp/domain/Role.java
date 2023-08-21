package com.foodjou.fjapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    private String role_name;
    private String description;
    @OneToMany(mappedBy = "role")
    private List<User> users;
}
