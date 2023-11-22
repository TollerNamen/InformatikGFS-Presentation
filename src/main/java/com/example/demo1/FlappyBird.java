package com.example.demo1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.Random;

public class FlappyBird extends Application
{
    private static final double BIRD_RADIUS = 10;
    private static final double GRAVITY = 2;
    private static final double JUMP = -75;
    private static Circle bird;
    private static Pane root;
    private static Timeline birdFall;
    private static Timeline pipeMove;
    private static Timeline collisionCheck;
    private static Scene scene;
    @Override
    public void start(Stage stage)
    {
        root = new Pane();
        scene = new Scene(root, 800, 800);

        bird = new Circle(50, 50, BIRD_RADIUS);
        bird.setFill(Color.RED);
        root.getChildren().add(bird);

        birdFall = new Timeline(new KeyFrame(Duration.millis(10), event -> updateBird()));
        birdFall.setCycleCount(Timeline.INDEFINITE);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE)
                jumpBird();
        });

        Rectangle[] pipes = new Rectangle[8];
        int y;
        Random random = new Random();
        for (int i = 0; i < pipes.length - 1; i = i + 2)
        {
            y = 100 * (random.nextInt(3) + 1);
            pipes[i] = new Rectangle(800+(150*i), 0, 100, y);
            pipes[i].setFill(Color.GREEN);
            pipes[i + 1] = new Rectangle(800+(150*i), y + 200, 100, 800);
            pipes[i + 1].setFill(Color.GREEN);
        }
        root.getChildren().addAll(pipes);

        pipeMove = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            for (int i = 0; i < pipes.length - 1; i = i + 2)
            {
                pipes[i].setX(pipes[i].getX() - 1);
                pipes[i + 1].setX(pipes[i].getX() - 1);
                if ((pipes[i].getX() % 100) == 0)
                {
                    //System.out.println(i + ": step " + pipes[i].getX());
                }
                if (pipes[i].getX() < -200)
                {
                    //System.out.println("updating pos");
                    updatePipePosition(pipes, i);
                }
            }
        }));
        pipeMove.setCycleCount(Timeline.INDEFINITE);

        collisionCheck = new Timeline(new KeyFrame(Duration.millis(10), event -> checkCollision(pipes, stage)));
        collisionCheck.setCycleCount(Timeline.INDEFINITE);

        Button button = new Button("Start");
        button.setOnAction(event -> {
            stage.setScene(scene);
            birdFall.play();
            pipeMove.play();
            collisionCheck.play();
        });
        button.setStyle("-fx-font-size: 30px;");
        Pane pane = new StackPane(button);
        Scene startScene = new Scene(pane, 800, 800);

        stage.setScene(startScene);
        stage.setTitle("Flappy Bird Demo");
        stage.show();
    }
    private void updateBird()
    {
        bird.setCenterY(bird.getCenterY() + GRAVITY);
    }
    private void jumpBird()
    {
        bird.setCenterY(bird.getCenterY() + JUMP);
    }
    private void checkCollision(Rectangle[] pipes, Stage stage)
    {
        for (Rectangle pipe: pipes)
        {
            if (bird.getBoundsInParent().intersects(pipe.getBoundsInParent()))
            {
                Label label = new Label("You Died.");
                label.setStyle("-fx-font-size: 30px;");
                Pane pane = new StackPane(label);
                Scene endScene = new Scene(pane, 800, 800);
                stage.setScene(endScene);
                birdFall.stop();
                pipeMove.stop();
                collisionCheck.stop();
            }
        }
    }
    public void updatePipePosition(Rectangle[] pipes, int i)
    {
        root.getChildren().remove(pipes[i]);
        root.getChildren().remove(pipes[i + 1]);
        int y;
        Random random = new Random();
        y = 100 * (random.nextInt(3) + 1);
        pipes[i] = new Rectangle(800+150, 0, 100, y);
        pipes[i].setFill(Color.GREEN);
        pipes[i + 1] = new Rectangle(800+150, y + 200, 100, 800);
        pipes[i + 1].setFill(Color.GREEN);
        root.getChildren().add(pipes[i]);
        root.getChildren().add(pipes[i + 1]);
    }
}