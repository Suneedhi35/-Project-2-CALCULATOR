package basic_calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private boolean operatorPressed = false;

    public Calculator() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Create display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        display.setText("0");
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button labels in order
        String[] buttonLabels = {
            "C", "CE", "⌫", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "±", "0", ".", "="
        };

        // Create and add buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            
            // Color coding for different button types
            if (label.matches("[0-9]")) {
                button.setBackground(new Color(240, 240, 240));
            } else if (label.matches("[+\\-×÷=]")) {
                button.setBackground(new Color(255, 159, 10));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(200, 200, 200));
            }
            
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            buttonPanel.add(button);
        }

        // Add components to frame
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Set window properties
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.matches("[0-9]")) {
                handleNumber(command);
            } else if (command.equals(".")) {
                handleDecimal();
            } else if (command.matches("[+\\-×÷]")) {
                handleOperator(command.charAt(0));
            } else if (command.equals("=")) {
                handleEquals();
            } else if (command.equals("C")) {
                handleClear();
            } else if (command.equals("CE")) {
                handleClearEntry();
            } else if (command.equals("⌫")) {
                handleBackspace();
            } else if (command.equals("±")) {
                handlePlusMinus();
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void handleNumber(String number) {
        if (operatorPressed) {
            display.setText(number);
            operatorPressed = false;
        } else {
            String currentText = display.getText();
            if (currentText.equals("0")) {
                display.setText(number);
            } else {
                display.setText(currentText + number);
            }
        }
    }

    private void handleDecimal() {
        String currentText = display.getText();
        if (operatorPressed) {
            display.setText("0.");
            operatorPressed = false;
        } else if (!currentText.contains(".")) {
            display.setText(currentText + ".");
        }
    }

    private void handleOperator(char op) {
        if (!operatorPressed) {
            num1 = Double.parseDouble(display.getText());
        }
        operator = op;
        operatorPressed = true;
    }

    private void handleEquals() {
        if (operatorPressed) return;
        
        num2 = Double.parseDouble(display.getText());
        
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '×':
                result = num1 * num2;
                break;
            case '÷':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    display.setText("Cannot divide by zero");
                    return;
                }
                break;
            default:
                return;
        }
        
        // Format result to avoid unnecessary decimal places
        if (result == (long) result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }
        
        operatorPressed = true;
    }

    private void handleClear() {
        display.setText("0");
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = '\0';
        operatorPressed = false;
    }

    private void handleClearEntry() {
        display.setText("0");
    }

    private void handleBackspace() {
        String currentText = display.getText();
        if (currentText.length() > 1) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            display.setText("0");
        }
    }

    private void handlePlusMinus() {
        String currentText = display.getText();
        if (!currentText.equals("0")) {
            if (currentText.startsWith("-")) {
                display.setText(currentText.substring(1));
            } else {
                display.setText("-" + currentText);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator();
        });
    }
}