package com.ua.equationsolver.service;

import com.ua.equationsolver.model.Equation;
import com.ua.equationsolver.model.Root;
import com.ua.equationsolver.repository.EquationRepository;
import com.ua.equationsolver.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquationService {
    private final EquationRepository equationRepository;
    private final RootRepository rootRepository;

    @Autowired
    public EquationService(EquationRepository equationRepository, RootRepository rootRepository) {
        this.equationRepository = equationRepository;
        this.rootRepository = rootRepository;
    }

    public void saveEquation(Equation equation) {
        equationRepository.save(equation);
    }

    public void addRootToEquation(Long equationId, Root root) {
        Equation equation = equationRepository.findById(equationId).orElse(null);

        if (equation != null) {
            root.setEquation(equation);
            rootRepository.save(root);
        }
    }

    public List<Equation> findEquationsWithRoot(double rootValue) {
        return equationRepository.findByRoots_RootValue(rootValue);
    }

    public List<Equation> findEquationsByRootCount(int rootCount) {
        List<Equation> equations = equationRepository.findAll();
        List<Equation> result = new ArrayList<>();

        for (Equation equation : equations) {
            if (equation.getRoots().size() == rootCount) {
                result.add(equation);
            }
        }
        return result;
    }

    public Equation findEquationById(Long equationId) {
        return equationRepository.findById(equationId).orElse(null);

    }

}