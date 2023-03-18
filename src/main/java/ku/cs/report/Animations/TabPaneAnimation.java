package ku.cs.report.Animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

public class TabPaneAnimation implements Animation<TabPane, Direction> {
    @Override
    public void move(TabPane tabPane, int range, Direction direction) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), event -> {
            if(direction == Direction.up) tabPane.setLayoutY(tabPane.getLayoutY() - 1);
            if(direction == Direction.down) tabPane.setLayoutY(tabPane.getLayoutY() + 1);
            if(direction == Direction.left) tabPane.setLayoutX(tabPane.getLayoutX() - 1);
            if(direction == Direction.right) tabPane.setLayoutX(tabPane.getLayoutX() + 1);
        }));
        timeline.setCycleCount(range);
        timeline.play();
    }

    @Override
    public void fadeIn(TabPane tabPane, Direction direction) {
        tabPane.setOpacity(0);
        if(direction == Direction.up) tabPane.setLayoutY(tabPane.getLayoutY() + 50);
        if(direction == Direction.down) tabPane.setLayoutY(tabPane.getLayoutY() - 50);
        if(direction == Direction.left) tabPane.setLayoutX(tabPane.getLayoutX() + 50);
        if(direction == Direction.right) tabPane.setLayoutX(tabPane.getLayoutX() - 50);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if(direction == Direction.up) tabPane.setLayoutY(tabPane.getLayoutY() - 1);
            if(direction == Direction.down) tabPane.setLayoutY(tabPane.getLayoutY() + 1);
            if(direction == Direction.left) tabPane.setLayoutX(tabPane.getLayoutX() - 1);
            if(direction == Direction.right) tabPane.setLayoutX(tabPane.getLayoutX() + 1);
            tabPane.setOpacity(tabPane.getOpacity() + 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }

    @Override
    public void fadeOut(TabPane tabPane, Direction direction) {
        tabPane.setOpacity(1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if(direction == Direction.up) tabPane.setLayoutY(tabPane.getLayoutY() - 1);
            if(direction == Direction.down) tabPane.setLayoutY(tabPane.getLayoutY() + 1);
            if(direction == Direction.left) tabPane.setLayoutX(tabPane.getLayoutX() - 1);
            if(direction == Direction.right) tabPane.setLayoutX(tabPane.getLayoutX() + 1);
            tabPane.setOpacity(tabPane.getOpacity() - 0.02);
        }));
        timeline.setCycleCount(50);
        timeline.play();
    }
}
