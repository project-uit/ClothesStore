/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author quochung
 */
public class SildingWindowAnimation {

    public enum Direction {
        SildeLeft, SildeRight, SildeUp, SildeDown;
    }

    public void Silde(AnchorPane root, AnchorPane view1, AnchorPane view2, Direction dir) {

        root.getChildren().add(view2);
        double width = root.getWidth();
        KeyFrame start = null, end = null;

        switch (dir) {
            case SildeLeft:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view2.translateXProperty(), width),
                        new KeyValue(view1.translateXProperty(), 0));
                end = new KeyFrame(Duration.seconds(1.7),
                        new KeyValue(view2.translateXProperty(), 0),
                        new KeyValue(view1.translateXProperty(), -width));
                break;
            case SildeRight:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view2.translateXProperty(), -width),
                        new KeyValue(view1.translateXProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view2.translateXProperty(), 0),
                        new KeyValue(view1.translateXProperty(), width));
                break;
            case SildeDown:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view2.translateYProperty(), width),
                        new KeyValue(view1.translateYProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view2.translateYProperty(), 0),
                        new KeyValue(view1.translateYProperty(), -width));
                break;
            case SildeUp:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view2.translateYProperty(), -width),
                        new KeyValue(view1.translateYProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view2.translateYProperty(), 0),
                        new KeyValue(view1.translateYProperty(), width));
                break;
            default:
                break;
        }

        if (start != null && end != null) {
            Timeline slide = new Timeline(start, end);
            slide.setOnFinished(e -> root.getChildren().remove(0));// remove(view1)
            slide.play();
        }
    }

    public void SildeBack(AnchorPane root, AnchorPane view, Direction dir) {

        //root.getChildren().add(view2);     
        double width = root.getWidth();
        KeyFrame start = null, end = null;

        switch (dir) {
            case SildeLeft:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateXProperty(), width),
                        new KeyValue(view.translateXProperty(), width));
                end = new KeyFrame(Duration.seconds(1.7),
                        new KeyValue(view.translateXProperty(), 0),
                        new KeyValue(view.translateXProperty(), 0));
                break;
            case SildeRight:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateXProperty(), 0));
                end = new KeyFrame(Duration.seconds(1.7),
                        new KeyValue(view.translateXProperty(), width));
                break;
            case SildeDown:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateYProperty(), width),
                        new KeyValue(view.translateYProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view.translateYProperty(), 0),
                        new KeyValue(view.translateYProperty(), -width));
                break;
            case SildeUp:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateYProperty(), -width),
                        new KeyValue(view.translateYProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view.translateYProperty(), 0),
                        new KeyValue(view.translateYProperty(), width));
                break;
            default:
                break;
        }

        if (start != null && end != null) {
            Timeline slide = new Timeline(start, end);
            slide.setOnFinished(e -> root.getChildren().remove(root.getChildren().size() - 1));// remove(view)
            slide.play();
        }
    }
    
    public void SildeTo(AnchorPane root, AnchorPane view, Direction dir) {

        root.getChildren().add(view);
        double width = root.getWidth();
        KeyFrame start = null, end = null;

        switch (dir) {
            case SildeLeft:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateXProperty(), width)
                );
                end = new KeyFrame(Duration.seconds(1.7),
                        new KeyValue(view.translateXProperty(), 0));
                break;
            case SildeRight:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateXProperty(), -width)
                );
                end = new KeyFrame(Duration.seconds(1.7),
                        new KeyValue(view.translateXProperty(), 0));
                break;
            case SildeDown:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateYProperty(), width),
                        new KeyValue(view.translateYProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view.translateYProperty(), 0),
                        new KeyValue(view.translateYProperty(), -width));
                break;
            case SildeUp:
                start = new KeyFrame(Duration.ZERO,
                        new KeyValue(view.translateYProperty(), -width),
                        new KeyValue(view.translateYProperty(), 0));
                end = new KeyFrame(Duration.seconds(1),
                        new KeyValue(view.translateYProperty(), 0),
                        new KeyValue(view.translateYProperty(), width));
                break;
            default:
                break;
        }

        if (start != null && end != null) {
            Timeline slide = new Timeline(start, end);
            slide.play();
        }
    }
}
