package com.ua.equationsolver.controller;

import com.ua.equationsolver.dto.EquationDTO;
import com.ua.equationsolver.dto.RootDTO;
import com.ua.equationsolver.model.Equation;
import com.ua.equationsolver.model.Root;
import com.ua.equationsolver.service.EquationService;
import com.ua.equationsolver.service.EquationValidator;
import com.ua.equationsolver.service.RootService;
import com.ua.equationsolver.service.RootValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equations")
public class EquationController {
    private final EquationService equationService;
    private final RootService rootService;
    private static final double ROOT_VALIDATION_EPSILON = 1e-9;

    @Autowired
    public EquationController(EquationService equationService, RootService rootService) {
        this.equationService = equationService;
        this.rootService = rootService;
    }

    @PostMapping
    public ResponseEntity<String> saveEquation(@RequestBody Equation equation) {
        if (EquationValidator.validateEquation(equation)) {
            equationService.saveEquation(equation);
            return ResponseEntity.ok("Equation saved successfully");
        } else {
            return ResponseEntity.badRequest().body("Equation validation failed");
        }
    }

    @PostMapping("/{equationId}/add-root")
    public ResponseEntity<String> addRootToEquation(@PathVariable Long equationId, @RequestBody Root root) {
        Equation equation = equationService.findEquationById(equationId);

        if (RootValidator.isRootOfEquation(equation.getEquation(), root.getRootValue(), ROOT_VALIDATION_EPSILON)) {
            equationService.addRootToEquation(equationId, root);
            return ResponseEntity.ok("Root added successfully");
        } else {
            return ResponseEntity.badRequest().body("Root validation failed");
        }
    }

    @GetMapping("/search")
    public List<EquationDTO> findEquationsWithRoot(@RequestParam double rootValue) {
        List<Equation> equations = equationService.findEquationsWithRoot(rootValue);
        return equations.stream()
                .map(this::convertToEquationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-root-count")
    public List<EquationDTO> findEquationsByRootCount(@RequestParam int rootCount) {
        List<Equation> equations = equationService.findEquationsByRootCount(rootCount);
        return equations.stream()
                .map(this::convertToEquationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{equationId}/roots")
    public List<RootDTO> getRootsByEquationId(@PathVariable Long equationId) {
        List<RootDTO> rootsDTO = rootService.getRootsByEquationId(equationId).stream()
                .map(root -> new RootDTO(root.getId(), root.getRootValue()))
                .collect(Collectors.toList());
        return rootsDTO;
    }

    private EquationDTO convertToEquationDTO(Equation equation) {
        EquationDTO equationDTO = new EquationDTO();
        equationDTO.setId(equation.getId());
        equationDTO.setEquation(equation.getEquation());
        List<RootDTO> rootDTOs = equation.getRoots().stream()
                .map(root -> new RootDTO(root.getId(), root.getRootValue()))
                .collect(Collectors.toList());
        equationDTO.setRoots(rootDTOs);
        return equationDTO;
    }
}