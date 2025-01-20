package tetris.model;

import tetris.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Figure implements Serializable {
    private List<Point> figure = new ArrayList<>();
    public int indexOfFigure = -1;

    Random random = new Random();

    int[][] O = {{1, 4}, {0, 4}, {1, 5}, {0, 5}};
    int[][] I = {{0, 3}, {0, 4}, {0, 5}, {0, 6}};
    int[][] J = {{2, 4}, {2, 5}, {1, 5}, {0, 5}};
    int[][] L = {{2, 4}, {1, 4}, {0, 4}, {2, 5}};
    int[][] S = {{1, 4}, {1, 5}, {0, 5}, {0, 6}};
    int[][] T = {{0, 4}, {1, 5}, {0, 5}, {0, 6}};
    int[][] Z = {{0, 4}, {1, 5}, {0, 5}, {1, 6}};

    int[][][] figures = {O, I, J, L, S, T, Z};

    public Figure() {
        int index = random.nextInt(figures.length);
        indexOfFigure = index;
        int[][] fig = Utils.deepCopy(figures[index]);
        for (int i = 0; i < fig.length; i++) {
            Point point = new Point(fig[i][0], fig[i][1]);
            figure.add(point);
        }
    }

    public Figure(Figure figure) {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            Point newPoint = new Point(figure.getPoint(i).getX(), figure.getPoint(i).getY());
            this.figure.add(newPoint);
        }
    }

    public Point getPoint(int index) {
        return figure.get(index);
    }

    public void setPoint(int index, Point point) {
        figure.set(index, point);
    }

    public List<Point> getFigure() {
        return figure;
    }
}
