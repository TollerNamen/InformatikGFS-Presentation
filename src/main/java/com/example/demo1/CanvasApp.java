package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;

import java.io.IOException;

public class CanvasApp extends Application
{
    public static void main(String[] args)
    {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException
    {
        Canvas canvas = new Canvas(400, 300);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        var root = new Group(canvas);
        draw(graphicsContext);

        stage.setTitle("Canvas Example");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void draw(GraphicsContext graphicsContext) throws IOException
    {
        graphicsContext.setLineCap(StrokeLineCap.ROUND); // Enden sind hier hier rund
        graphicsContext.setStroke(Color.BLUE); // Linien sind blau
        graphicsContext.setLineWidth(6); // Linine sind 6px dick

        graphicsContext.strokeLine(10, 290, 30, 10); // kreiere Linie von A(10|290) bis B(30|10)

        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.BLACK);

        graphicsContext.strokeText("Eine andere Art, um Text darzustellen", 30, 110);

        graphicsContext.setLineWidth(3);
        graphicsContext.setStroke(Color.RED);

        for (int i = 0; i < 3; i++) // mehrere rote Kreise
        {
            graphicsContext.strokeOval(40+40*i, 10, 80, 80);
        }
        graphicsContext.setFill(Color.BLUE); // Füllfarbe wird hier blau gemacht
        graphicsContext.setStroke(Color.BLACK);

        for (int i = 0; i < 3; i++) // weitere blaugefüllte Kreise
        {
            graphicsContext.fillOval(40+80*i, 120, 80, 80);
            graphicsContext.strokeOval(40+80*i, 120, 80, 80);
        }
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);

        // ein zusammengehöriges Polygon
        double[] x = { 40,  70, 100, 100, 130, 160, 190, 220,  40};
        double[] y = {230, 210, 230, 260, 230, 210, 230, 260, 260};

        graphicsContext.fillPolygon(x, y, x.length);
        graphicsContext.strokePolygon(x, y, x.length);
    }
}
