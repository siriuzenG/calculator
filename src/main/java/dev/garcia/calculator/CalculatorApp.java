package dev.garcia.calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        double minHeight = 400;
        double minWidth = 300;

        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout);

        CalculatorScreen screen = new CalculatorScreen(minWidth, minHeight);
        layout.setTop(screen.getLayout());

        CalculatorButtons buttons = new CalculatorButtons(screen, minWidth, minHeight);
        layout.setCenter(buttons.getLayout());

        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.setMinWidth(minWidth + 50);
        stage.setMinHeight(minHeight + 100);
        stage.show();

        layout.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            screen.setWidth(width);
            buttons.setWidth(width);
        });
        layout.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            screen.setHeight(height * 0.25);
            buttons.setHeight(height * 0.75);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
