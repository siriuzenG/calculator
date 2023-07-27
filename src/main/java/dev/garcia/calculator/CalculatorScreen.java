package dev.garcia.calculator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CalculatorScreen {
    private final VBox layout = new VBox();
    private final Label lblOperator = new Label();
    private final Label lblOutput = new Label();
    private final TextField txtInput = new TextField();
    public CalculatorScreen(double width, double height) {
        layout.setMinWidth(width);
        layout.setMinHeight(height * 0.25);

        createScreen();
    }

    public VBox getLayout() {
        return layout;
    }
    public void setHeight(double height) {
        layout.setPrefHeight(height + 20);
    }
    public void setWidth(double width) {
        layout.setPrefWidth(width + 20);
    }
    public void setOperator(String operator) {
        lblOperator.setText(operator);
    }
    public void setOutput(String output) {
        lblOutput.setText(output);
    }
    public void setInput(String input) {
        txtInput.setText(input);
    }
    public void txtFocus() {
        txtInput.requestFocus();
    }
    public String getOutput() {
        return lblOutput.getText();
    }
    public String getInput() {
        return txtInput.getText();
    }
    public String getOperator() {
        return lblOperator.getText();
    }

    private void createScreen() {
        Font font = new Font("Arial", 24);
        Insets set10 = new Insets(10);

        lblOperator.setStyle("-fx-alignment: center-left;");
        lblOperator.setFont(font);
        lblOperator.setPadding(set10);
        lblOutput.setStyle("-fx-alignment: center-right;");
        lblOutput.setFont(font);
        lblOutput.setPadding(set10);

        HBox boxLabel = new HBox();
        boxLabel.getChildren().addAll(lblOperator, lblOutput);
        boxLabel.setAlignment(Pos.CENTER);

        txtInput.setAlignment(Pos.CENTER_RIGHT);
        txtInput.setFont(font);

        layout.getChildren().addAll(boxLabel, txtInput);
        layout.setPadding(set10);

        txtInput.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.matches("-?\\d*") && newVal.length() <= 10) {
                txtInput.setText(newVal);
            } else {
                txtInput.setText(oldVal);
            }
        });

        layout.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            boxLabel.setPrefWidth(width);
        });
        layout.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            boxLabel.setPrefHeight(height / 2);
            txtInput.setPrefHeight(height /2);
        });

        boxLabel.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            lblOperator.setPrefWidth(width * 0.25);
            lblOutput.setPrefWidth(width * 0.75);
        });
        boxLabel.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            lblOperator.setPrefHeight(height);
            lblOutput.setPrefHeight(height);
        });
    }
}
