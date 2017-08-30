package project1;

/**
 * Filename:    P1GUI
 * Author:      William Crutchfield
 * Date:        6/2/2017
 * Description: Handles the DivideByZero Exception
 */
class DivideByZero extends Exception {

  // Constructor that accepts a message
  DivideByZero(String message) {
    super(message);
  }
}
