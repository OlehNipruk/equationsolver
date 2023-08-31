package com.ua.equationsolver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquationDTO {
    private Long id;
    private String equation;
    private List<RootDTO> roots;

}