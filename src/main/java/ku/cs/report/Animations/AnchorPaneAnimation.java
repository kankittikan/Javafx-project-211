package ku.cs.report.Animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AnchorPaneAnimation implements Animation<AnchorPane, Direction> {
    @Override
    public void move(AnchorPane anchorPane, int range, Direction direction) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), event -> {
            if(direction == Direction.up) anchorPane.setLayoutY(anchorPane.getLayoutY() - 1);
            if(direction == Direction.down) anchorPane.setLayoutY(anchorPane.getLayoutY() + 1);
            if(direction == Direction.left) anchorPane.setLayoutX(anchorPane.getLayoutX() - 1);
            if(direction == Direction.right) anchorPane.setLayoutX(anchorPane.getLayoutX() + 1);
        }));
        timeline.setCycleCount(range);
        timeline.play();
    }

    @Override
    public void fadeIn(AnchorPane anchorPane, Direction direction) {
        anchorPane.setOpacity(0);
        if(direction == Direction.up) anchorPane.setLayoutY(anchorPane.getLayoutY() + 50);
        if(direction == Direction.down) anchorPane.setLayoutY(anchorPane.getLayoutY() - 50);
        if(direction == Direction.left) anchorPane.setLayoutX(anchorPane.getLayoutX() + 50);
        if(direction == Direction.right) anchorPane.setLayoutX(anchorPane.getLayoutX() - 50);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if(direction == Direction.up) anchorPane.setLayoutY(anchorPane.getLayoutY() - 1);
            if(direction == Direction.down) anchorPane.setLayoutY(anchorPane.getLayoutY() + 1);
            if(direction == Direction.left) anchorPane.setLayoutX(anchorPane.getLayoutX() - 1);
            if(direction == Direction.right) anchorPane.setLayoutX(anchorPane.getLayoutX() + 1);
            anchorPane.setOpacity(anchorPane.getOpacity() + 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }

    @Override
    public void fadeOut(AnchorPane anchorPane, Direction direction) {
        anchorPane.setOpacity(1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if(direction == Direction.up) anchorPane.setLayoutY(anchorPane.getLayoutY() - 1);
            if(direction == Direction.down) anchorPane.setLayoutY(anchorPane.getLayoutY() + 1);
            if(direction == Direction.left) anchorPane.setLayoutX(anchorPane.getLayoutX() - 1);
            if(direction == Direction.right) anchorPane.setLayoutX(anchorPane.getLayoutX() + 1);
            anchorPane.setOpacity(anchorPane.getOpacity() - 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }
}
