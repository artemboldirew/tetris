package tetris.model;

import java.util.ArrayList;
import java.util.List;

public class Shadow {
    private List<Point> shadow = new ArrayList<>();
    public int indexOfFigure = -1;

    public Shadow(Figure figure) {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            Point newPoint = new Point(figure.getPoint(i).getX(), figure.getPoint(i).getY());
            shadow.add(newPoint);
        }
    }

    public Point getPoint(int index) {
        return shadow.get(index);
    }

    public List<Point> getFigure() {
        return shadow;
    }
}
