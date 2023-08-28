package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return name;
    }
}
