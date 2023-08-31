package com.ua.equationsolver.repository;

import com.ua.equationsolver.model.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RootRepository extends JpaRepository<Root, Long> {
    List<Root> findByEquationId(Long equationId);
}