package com.ua.equationsolver.repository;

import com.ua.equationsolver.model.Equation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Long> {
    List<Equation> findByRoots_RootValue(double rootValue);
}