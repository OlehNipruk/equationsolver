package com.ua.equationsolver.service;

import java.util.Stack;

public class RootValidator {

    public static boolean isRootOfEquation(String equation, double rootCandidate, double epsilon) {
        String[] sides = equation.split("=");
        double leftSideValue = evaluateExpression(sides[0], rootCandidate);
        double rightSideValue = evaluateExpression(sides[1], rootCandidate);

        return Math.abs(leftSideValue - rightSideValue) <= epsilon;
    }

    public static double evaluateExpression(String expression, double x) {
        String[] tokens = expression.split("(?=[-+*/()])|(?<=[-+*/()])");
        Stack<Double> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (token.equals("")) {
                continue;
            }
            if (isNumeric(token)) {
                operandStack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && hasHigherPrecedence(operatorStack.peek(), token)) {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.peek().equals("(")) {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.pop();
            } else if (token.equals("x")) {
                operandStack.push(x);
            }
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack);
        }

        return operandStack.pop();
    }

    private static boolean isNumeric(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static boolean hasHigherPrecedence(String op1, String op2) {
        int precedence1 = getPrecedence(op1);
        int precedence2 = getPrecedence(op2);
        return precedence1 >= precedence2;
    }

    private static int getPrecedence(String operator) {
        if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }
        return 0;
    }

    private static void performOperation(Stack<Double> operandStack, Stack<String> operatorStack) {
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        String operator = operatorStack.pop();

        double result = applyOperator(operator, operand1, operand2);
        operandStack.push(result);
    }

    private static double applyOperator(String operator, double operand1, double operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }


}
