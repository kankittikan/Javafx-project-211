package ku.cs.report.Animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class LabelAnimation implements Animation<Label, Direction> {
    @Override
    public void move(Label label, int range, Direction direction) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), event -> {
            if (direction == Direction.up) label.setLayoutY(label.getLayoutY() - 1);
            if (direction == Direction.down) label.setLayoutY(label.getLayoutY() + 1);
            if (direction == Direction.left) label.setLayoutX(label.getLayoutX() - 1);
            if (direction == Direction.right) label.setLayoutX(label.getLayoutX() + 1);
        }));
        timeline.setCycleCount(range);
        timeline.play();
    }

    @Override
    public void fadeIn(Label label, Direction direction) {
        label.setOpacity(0);
        if (direction == Direction.up) label.setLayoutY(label.getLayoutY() + 50);
        if (direction == Direction.down) label.setLayoutY(label.getLayoutY() - 50);
        if (direction == Direction.left) label.setLayoutX(label.getLayoutX() + 50);
        if (direction == Direction.right) label.setLayoutX(label.getLayoutX() - 50);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (direction == Direction.up) label.setLayoutY(label.getLayoutY() - 1);
            if (direction == Direction.down) label.setLayoutY(label.getLayoutY() + 1);
            if (direction == Direction.left) label.setLayoutX(label.getLayoutX() - 1);
            if (direction == Direction.right) label.setLayoutX(label.getLayoutX() + 1);
            label.setOpacity(label.getOpacity() + 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }

    @Override
    public void fadeOut(Label label, Direction direction) {
        label.setOpacity(1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (direction == Direction.up) label.setLayoutY(label.getLayoutY() - 1);
            if (direction == Direction.down) label.setLayoutY(label.getLayoutY() + 1);
            if (direction == Direction.left) label.setLayoutX(label.getLayoutX() - 1);
            if (direction == Direction.right) label.setLayoutX(label.getLayoutX() + 1);
            label.setOpacity(label.getOpacity() - 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }
}
