package koszyk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;


public class KoszykApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Kliknij mnie");
        VBox vbox = new VBox();
        TextField textField = new TextField();
        textField.setPromptText("Wpisz coś");
        vbox.getChildren().addAll(textField, button);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setTitle("Moja pierwsza aplikacja JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        button.setOnAction(e -> {;
            System.out.println(textField.getText());
            textField.setText("");
        });
    }

    public static void main(String[] args) {
        launch(args);

    }
}