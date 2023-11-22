package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application
{
    public static void main(String[] args)
    {
        launch();
    }
    @Override
    public void start(Stage stage)
    {
        Button button = new Button("Switch Scene");
        button.setOnAction(event -> {
            try
            {
                /*
                // FXMLLoader Objekt erstellen
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("hello-view.fxml"));

                // Parent bekommen
                Parent root = loader.load();

                // Controllerkommunikation
                HelloController controller = loader.getController();
                controller.setLabelText("Hello!");
                 */


                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

                // die Stage bekommen
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));

            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });

        Pane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
