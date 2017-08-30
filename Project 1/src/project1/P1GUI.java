package project1;

import java.awt.event.ActionEvent;
import java.util.EmptyStackException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Filename :   P1GUI
 * Author :     William Crutchfield
 * Date:        6/1/2017
 * Description: Allows the user to input an Infix Expression and outputs the result
 */
public class P1GUI extends JFrame {

  // Variables
  private String equation;
  private String result;

  // Creates new Instance of InfixEval
  private InfixEval infixEval = new InfixEval();

  // Constructs GUI
  private P1GUI() {

    // Set Title
    super("");

    // Create Panels
    JPanel main = new JPanel();
    JPanel inputPanel = new JPanel();
    JPanel evalPanel = new JPanel();
    JPanel resultPanel = new JPanel();

    // Set Layout
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

    // Create Components
    JLabel inputLabel = new JLabel("Enter Infix Expression");
    JTextField inputTxt = new JTextField(null, 20);
    JButton evaluateBtn = new JButton("Evaluate");
    JLabel resultLabel = new JLabel("Result");
    JTextField resultTxt = new JTextField(null, 20);

    // Add Input Components
    inputPanel.add(inputLabel);
    inputPanel.add(inputTxt);

    // Add Evaluate Button
    evalPanel.add(evaluateBtn);

    // Add Result Components
    resultPanel.add(resultLabel);
    resultPanel.add(resultTxt);
    resultTxt.setEditable(false);

    // Add Panels to main
    main.add(inputPanel);
    main.add(evalPanel);
    main.add(resultPanel);

    // Add main to JFrame
    add(main);

    // JFrame Settings
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(400, 145);
    setLocation(300, 400);
    setResizable(false);
    setVisible(true);

    // Evaluate Button Event Handler
    evaluateBtn.addActionListener((ActionEvent e) -> {
      // Assign inputTxt to equation
      equation = inputTxt.getText();

      try {
        if (equation.isEmpty()) {
          throw new NullPointerException();
        } else {
          result = (infixEval.evaluate(equation));
          resultTxt.setText(result);
        }
      } catch (NullPointerException nullPointer) {
        JOptionPane.showMessageDialog(null, "An Infix Expression is required.", "Error",
            JOptionPane.ERROR_MESSAGE);
      } catch (DivideByZero divideByZero) {
        JOptionPane
            .showMessageDialog(null, "Division by Zero.", "Error", JOptionPane.ERROR_MESSAGE);
      } catch (InvalidCharacterException invalidCharacter) {
        JOptionPane.showMessageDialog(null,
            "Invalid Character/s Used. Only 0-9 and +,-,*,/,(,) characters allowed.", "Error",
            JOptionPane.ERROR_MESSAGE);
      } catch (EmptyStackException empty) {
        JOptionPane.showMessageDialog(null,
            "Invalid Infix Expression. Check Parentheses, Operators, and Operands.", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  public static void main(String[] args) {
    new P1GUI();
  }
}
