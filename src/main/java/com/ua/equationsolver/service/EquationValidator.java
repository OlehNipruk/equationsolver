package com.ua.equationsolver.service;

import com.ua.equationsolver.model.Equation;

import java.util.Stack;

public class EquationValidator {

    public static boolean validateEquation(Equation equation) {
        String equationStringBody = equation.getEquation();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < equationStringBody.length(); i++) {
            char ch = equationStringBody.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
            if (i > 0) {
                char prevCh = equationStringBody.charAt(i - 1);
                if (isOperator(prevCh) && isOperator(ch)) {
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }


}
