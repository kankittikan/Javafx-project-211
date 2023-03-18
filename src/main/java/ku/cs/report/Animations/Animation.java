package ku.cs.report.Animations;

import javafx.scene.layout.AnchorPane;

public interface Animation<T, D> {
    void move(T t, int range, D d);
    void fadeIn(T t, D d);
    void fadeOut(T t, D d);
}
