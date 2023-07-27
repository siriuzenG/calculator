package dev.garcia.calculator;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.math.BigInteger;

public class CalculatorButtons {
    private final GridPane layout = new GridPane();
    private final Button[][] buttons = new Button[4][4];
    private final CalculatorScreen screen;

    public CalculatorButtons(CalculatorScreen screen, double width, double height) {
        this.screen = screen;
        layout.setMinWidth(width);
        layout.setMinHeight(height * 0.75);

        createButtons();
    }

    public GridPane getLayout() {
        return layout;
    }

    public void setHeight(double height) {
        layout.setPrefHeight(height + 20);
    }

    public void setWidth(double width) {
        layout.setPrefWidth(width + 40);
    }

    private void createButtons() {
        Font font = new Font("Arial", 24);
        String[] labels = {
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "+",
                "C", "0", "=", "-"
        };

        int row = 0;
        int col = 0;

        for (String label : labels) {
            buttons[row][col] = new Button(label);
            layout.add(buttons[row][col], col, row);

            buttons[row][col].setFont(font);
            setButtonEvent(buttons[row][col], label);

            col += 1;
            if (col > 3) {
                col = 0;
                row += 1;
            }
        }

        layout.setPadding(new Insets(10));
        layout.setVgap(10);
        layout.setHgap(10);

        layout.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            for (Button[] btnRow : buttons) {
                for (Button btn : btnRow) {
                    btn.setPrefWidth(width / 4);
                }
            }
        });
        layout.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            for (Button[] btnRow : buttons) {
                for (Button btn : btnRow) {
                    btn.setPrefHeight(height / 4);
                }
            }
        });
    }

    private void setButtonEvent(Button button, String label) {
        if (label.equals("C")) {
            button.setOnMouseClicked(e -> {
                if (e.getClickCount() >= 1) {
                    screen.setOperator("");
                    screen.setInput("");
                }
                if (e.getClickCount() >= 2) {
                    screen.setOutput("");
                }
                screen.txtFocus();
            });
        }
        if (label.matches("[-+×÷]")) {
            button.setOnMouseClicked(e -> {
                String input = screen.getInput();
                String output = screen.getOutput();
                handleOperator(input, output, label);
                screen.txtFocus();
            });
        }
        if (label.matches("=")) {
            button.setOnMouseClicked(e -> {
                String input = screen.getInput();
                String output = screen.getOutput();
                String operator = screen.getOperator();
                if (!operator.isEmpty() && !operator.equals("=")) {
                    handleOperator(input, output, operator);
                    screen.setOperator("=");
                } else {
                    if (output.isEmpty()) {
                        if (!input.isEmpty()) {
                            screen.setOutput(input);
                            screen.setInput("");
                        }
                    } else {
                        screen.setInput("");
                    }
                }
                screen.txtFocus();
            });
        }
        if (label.matches("\\d")) {
            button.setOnMouseClicked(e -> screen.setInput(screen.getInput() + label));
        }
    }

    private void handleOperator(String input, String output, String operator) {
        if (!input.isEmpty()) {
            if (!output.isEmpty()) {
                BigInteger num1 = new BigInteger(output);
                BigInteger num2 = new BigInteger(input);
                BigInteger result = switch (operator) {
                    case "+" -> num1.add(num2);
                    case "-" -> num1.subtract(num2);
                    case "×" -> num1.multiply(num2);
                    case "÷" -> num1.divide(num2);
                    default -> BigInteger.ZERO;
                };
                screen.setOutput(result.toString());
            } else {
                screen.setOutput(input);
            }
        }
        screen.setOperator(operator);
        screen.setInput("");
    }
}
