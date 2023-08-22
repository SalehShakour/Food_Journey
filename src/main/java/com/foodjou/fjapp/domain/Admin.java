package com.foodjou.fjapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin_table")
public class Admin {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String phone_number;
}
