import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatorScreen extends JFrame {
    private JTextField display;
    private JButton[] buttons;
    private String[] labels = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "x",
            "0", "C", "/", "="
    };

    private int num1 = 0;
    private int num2 = 0;
    private int result = 0;
    private char operator = ' ';
    private boolean clearDisplay = false;

    public CalculatorScreen() {
        display = new JTextField();
        display.setPreferredSize(new Dimension(300, 50));
        display.setEditable(false);

        buttons = new JButton[16];
        JPanel panel = new JPanel(new GridLayout(4, 4));

        ActionListener listener = new CalculatorActionListener();

        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton(labels[i]);
            buttons[i].addActionListener(listener);
            panel.add(buttons[i]);
        }

        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setTitle("Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class CalculatorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String label = e.getActionCommand();

            if (label.equals("C")) {
                display.setText("");
                num1 = 0;
                num2 = 0;
                result = 0;
                operator = ' ';
                clearDisplay = false;
            } else if (label.matches("[0-9]")) {
                if (display.getText().equals("0") || clearDisplay) {
                    display.setText(label);
                    clearDisplay = false;
                } else {
                    display.setText(display.getText() + label);
                }
            } else if (label.matches("[/x\\-+]")) {
                num1 = Integer.parseInt(display.getText());
                operator = label.charAt(0);
                display.setText(display.getText() + operator);
                clearDisplay = false;
            } else if (label.equals("=")) {
                if (operator == ' ')
                    return;

                num2 = Integer.parseInt(display.getText().substring(display.getText().indexOf(operator) + 1));

                if (operator == '/' && num2 == 0) {
                    JOptionPane.showMessageDialog(CalculatorScreen.this, "Não existe divisão por zero", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case 'x':
                        result = num1 * num2;
                        break;
                    case '/':
                        result = num1 / num2;
                        break;
                }

                display.setText(String.valueOf(result));
                clearDisplay = true;
            }
        }
    }

    public static void main(String[] args) {
        new CalculatorScreen();
    }
}