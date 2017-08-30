package project1;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Filename:    P1GUI
 * Author:      William Crutchfield
 * Date:        6/1/2017
 * Description: Handles
 * all logic and calculations of a Infix Expression
 */
class InfixEval {

  // Variables
  private Stack<String> operand = new Stack<>();
  private Stack<String> operator = new Stack<>();

  /**
   * Takes an Infix Expression and uses stacks and methods to obtain the correct result
   *
   * @param equation Input string from user that contains Infix Expression
   * @return solution to equation
   * @throws DivideByZero is thrown when a user divides by 0
   * @throws InvalidCharacterException is thrown when characters other than 0-9 and +,-,*,/,(,), are
   * used
   */
  String evaluate(String equation)
      throws DivideByZero, InvalidCharacterException, EmptyStackException {
    // Method that splits list into tokens
    List<String> tokens = tokenize(equation);

    System.out.println(tokens);

    // Evaluates tokens
    for (String token : tokens) {
      // Patterns used to determine if token is an operator or operand
      Pattern operatorPat = Pattern.compile("[*/+\\-]");
      Pattern operandPat = Pattern.compile("[\\d.?]+");

      // Ensures no illegal characters are used
      if (!token.matches(String.valueOf(operandPat))
          && !token.matches(String.valueOf(operatorPat))
          && !token.equals("(")
          && !token.equals(")")) {
        throw new InvalidCharacterException();
      }

      // Checks if token is an operand
      if (token.matches(String.valueOf(operandPat))) {
        // Adds to operand stack
        operand.push(token);
        System.out.println("Operand: " + token);
      }

      // Checks if token is a "("
      else if (token.equals("(")) {
        // Adds to operator stack
        operator.push(token);
        System.out.println("Operator Left Paren: " + token);
      }

      // Checks if token is a ")"
      else if (token.equals(")")) {
        System.out.println("Operator Right Paren: " + token);

        // Executes the calculation method until a "(" is at the top of operator stack
        while (!operator.peek().equals("(")) {
          // Pushes the result of the calculation method onto the operand stack
          operand.push(calculation(operand.pop(), operator.pop(), operand.pop()));
        }
        operator.pop();
      }

      // Checks if token is an operator
      else if (token.matches(String.valueOf(operatorPat))) {
        // Executes the calculation method until current operator has highest precedence
        while (!operator.empty() && precedence(operator.peek()) >= precedence(
            token)) { //does not recheck precedence with new operators after first execution
          operand.push(calculation(operand.pop(), operator.pop(), operand.pop()));
        }
        // Adds to operator stack
        operator.push(token);
        System.out.println("Operator: " + token);
      }
    }

    // Executes the calculation method until there are no more operators
    while (!operator.empty()) {
      operand.push(calculation(operand.pop(), operator.pop(), operand.pop()));
      System.out.println("result: " + operand.peek());
    }

    return operand.pop();
  }

  /**
   * Preforms a calculation on two operands with the appropriate operator
   *
   * @param num2 Second operand used in a calculation
   * @param operator Operator for calculation
   * @param num1 First operand used in a calculation
   * @return result of calculation as a String
   * @throws DivideByZero is thrown when a user divides by 0
   */
  private String calculation(String num2, String operator, String num1) throws DivideByZero {
    int first = Integer.parseInt(num1);
    int second = Integer.parseInt(num2);
    int result = 0;

    switch (operator) {
      case "+":
        result = first + second;
        break;
      case "-":
        result = first - second;
        break;
      case "*":
        result = first * second;
        break;
      case "/":
        if (second == 0) {
          throw new DivideByZero("Division by zero");
        }
        result = first / second;
        break;
    }
    System.out.println(first + " " + operator + " " + second + " = " + result);
    return Integer.toString(result);
  }

  /**
   * Determines precedence of an operator
   *
   * @param operator an operator that is converted to an int
   * @return an int value that represents precedence
   */
  private int precedence(String operator) {
    // Precedence Boolean variable
    int precedence = 0;

    switch (operator) {
      case "+":
      case "-":
        precedence = 1;
        break;
      case "*":
      case "/":
        precedence = 2;
    }

    return precedence;
  }

  /**
   * Tokenizes the infix equation, ensuring multi-digit numbers are in a single token
   *
   * @param equation String containing Infix Expression
   * @return tokenized version of the Infix Expression
   */
  private List<String> tokenize(String equation) {
    // Variables
    List<String> tokens = new ArrayList<>();

    // Adds first character to a token
    tokens.add(Character.toString(equation.charAt(0)));

    // Iterates through string to add to tokens
    for (int i = 1; i < equation.length(); i++) {
      // Gets current char
      char c = equation.charAt(i);
      char lastc = equation.charAt(i - 1);

      // If c is a space, continues to next character
      if (c == ' ') {
        continue;
      }

      // Checks if c and lastc are digits, combines characters into single token if true
      if (Character.isDigit(c) && Character.isDigit(lastc)) {
        int lastIndex = (tokens.size() - 1);
        tokens.set(lastIndex, tokens.get(lastIndex) + c);
      } else { // Adds token like normal
        tokens.add(Character.toString(c));
      }
    }

    return tokens;
  }
}
