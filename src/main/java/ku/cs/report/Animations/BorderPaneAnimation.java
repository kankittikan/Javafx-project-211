package ku.cs.report.Animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class BorderPaneAnimation implements Animation<BorderPane, Direction> {
    @Override
    public void move(BorderPane borderPane, int range, Direction direction) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), event -> {
            if (direction == Direction.up) borderPane.setLayoutY(borderPane.getLayoutY() - 1);
            if (direction == Direction.down) borderPane.setLayoutY(borderPane.getLayoutY() + 1);
            if (direction == Direction.left) borderPane.setLayoutX(borderPane.getLayoutX() - 1);
            if (direction == Direction.right) borderPane.setLayoutX(borderPane.getLayoutX() + 1);
        }));
        timeline.setCycleCount(range);
        timeline.play();
    }

    @Override
    public void fadeIn(BorderPane borderPane, Direction direction) {
        borderPane.setOpacity(0);
        if (direction == Direction.up) borderPane.setLayoutY(borderPane.getLayoutY() + 50);
        if (direction == Direction.down) borderPane.setLayoutY(borderPane.getLayoutY() - 50);
        if (direction == Direction.left) borderPane.setLayoutX(borderPane.getLayoutX() + 50);
        if (direction == Direction.right) borderPane.setLayoutX(borderPane.getLayoutX() - 50);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (direction == Direction.up) borderPane.setLayoutY(borderPane.getLayoutY() - 1);
            if (direction == Direction.down) borderPane.setLayoutY(borderPane.getLayoutY() + 1);
            if (direction == Direction.left) borderPane.setLayoutX(borderPane.getLayoutX() - 1);
            if (direction == Direction.right) borderPane.setLayoutX(borderPane.getLayoutX() + 1);
            borderPane.setOpacity(borderPane.getOpacity() + 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }

    @Override
    public void fadeOut(BorderPane borderPane, Direction direction) {
        borderPane.setOpacity(1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (direction == Direction.up) borderPane.setLayoutY(borderPane.getLayoutY() - 1);
            if (direction == Direction.down) borderPane.setLayoutY(borderPane.getLayoutY() + 1);
            if (direction == Direction.left) borderPane.setLayoutX(borderPane.getLayoutX() - 1);
            if (direction == Direction.right) borderPane.setLayoutX(borderPane.getLayoutX() + 1);
            borderPane.setOpacity(borderPane.getOpacity() - 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }
}
