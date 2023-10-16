package com.example.spotifyfestival.Controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ConcertCanvasController {
    public Canvas canvas;

    public void initialize(){
        double canvasWidth = 400;
        double canvasHeight = 400;

        canvas.setHeight(canvasHeight);
        canvas.setWidth(canvasWidth);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double centerX = canvas.getWidth()/2;
        double centerY = canvas.getHeight()/2;
        double radius = Math.min(centerX, centerY);
        Color circleColor = Color.BLUE;

        gc.setFill(circleColor);
        gc.fillOval(centerX-radius, centerY-radius, 2*radius, 2*radius);
    }
}
