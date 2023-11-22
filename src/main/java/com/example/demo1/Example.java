package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Example extends Application
{
    public static void main(String[] args)
    {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException
    {
        Pane root = new VBox();

        Button button = new Button("Hello!");
        Label label = new Label();

        root.getChildren().addAll(button, label);

        TextArea textArea = new TextArea();

        root.getChildren().add(textArea);

        button.setOnAction(event -> label.setText(textArea.getText()));

        Scene scene = new Scene(root, 200, 100);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }
}