package com.ua.equationsolver.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "equations")
@Data
@NoArgsConstructor
public class Equation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equation")
    private String equation;

    @OneToMany(mappedBy = "equation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Root> roots;
}
