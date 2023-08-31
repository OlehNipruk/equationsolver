package com.ua.equationsolver.service;

import com.ua.equationsolver.model.Root;
import com.ua.equationsolver.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RootService {
    private final RootRepository rootRepository;

    @Autowired
    public RootService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
    }

    public List<Root> getRootsByEquationId(Long equationId) {
        return rootRepository.findByEquationId(equationId);
    }
}