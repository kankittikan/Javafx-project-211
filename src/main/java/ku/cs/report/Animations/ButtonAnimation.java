package ku.cs.report.Animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class ButtonAnimation implements Animation<Button, Direction> {
    @Override
    public void move(Button button, int range, Direction direction) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), event -> {
            if (direction == Direction.up) button.setLayoutY(button.getLayoutY() - 1);
            if (direction == Direction.down) button.setLayoutY(button.getLayoutY() + 1);
            if (direction == Direction.left) button.setLayoutX(button.getLayoutX() - 1);
            if (direction == Direction.right) button.setLayoutX(button.getLayoutX() + 1);
        }));
        timeline.setCycleCount(range);
        timeline.play();
    }

    @Override
    public void fadeIn(Button button, Direction direction) {
        button.setOpacity(0);
        if (direction == Direction.up) button.setLayoutY(button.getLayoutY() + 50);
        if (direction == Direction.down) button.setLayoutY(button.getLayoutY() - 50);
        if (direction == Direction.left) button.setLayoutX(button.getLayoutX() + 50);
        if (direction == Direction.right) button.setLayoutX(button.getLayoutX() - 50);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (direction == Direction.up) button.setLayoutY(button.getLayoutY() - 1);
            if (direction == Direction.down) button.setLayoutY(button.getLayoutY() + 1);
            if (direction == Direction.left) button.setLayoutX(button.getLayoutX() - 1);
            if (direction == Direction.right) button.setLayoutX(button.getLayoutX() + 1);
            button.setOpacity(button.getOpacity() + 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }

    @Override
    public void fadeOut(Button button, Direction direction) {
        button.setOpacity(1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (direction == Direction.up) button.setLayoutY(button.getLayoutY() - 1);
            if (direction == Direction.down) button.setLayoutY(button.getLayoutY() + 1);
            if (direction == Direction.left) button.setLayoutX(button.getLayoutX() - 1);
            if (direction == Direction.right) button.setLayoutX(button.getLayoutX() + 1);
            button.setOpacity(button.getOpacity() - 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
        if (direction == Direction.up) button.setLayoutY(button.getLayoutY() + 50);
        if (direction == Direction.down) button.setLayoutY(button.getLayoutY() - 50);
        if (direction == Direction.left) button.setLayoutX(button.getLayoutX() + 50);
        if (direction == Direction.right) button.setLayoutX(button.getLayoutX() - 50);
    }
}
