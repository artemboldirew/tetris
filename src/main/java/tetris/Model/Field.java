package tetris.Model;

import tetris.Connector;
import tetris.View.Frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    public int[][] table = new int[20][10];
    public List<int[]> figure = new ArrayList<>();
    public Connector con;

    public void createNewFigure() {
        int[][] cube = {{1, 4}, {0, 4}, {1, 5}, {0, 5}};
        figure = Arrays.asList(cube);
        for (int i = 0; i < figure.size(); i++) {
            int[] cur = figure.get(i);
            table[cur[0]][cur[1]] = 1;
        }
        con.frame.review();
    }

    public boolean canMoveDown() {
        for (int i = 0; i < figure.size(); i++) {
            int[] curPoint = figure.get(i);
            int[] nextPoint = Arrays.copyOf(curPoint, curPoint.length);
            nextPoint[0]++;
            if (curPoint[0] == 19 || (table[curPoint[0] + 1][curPoint[1]] != 0 && !containPoint(nextPoint))) {
                return false;
            }
        }
        return true;
    }

    public void moveDown() {
        for (int i = 0; i < figure.size(); i++) {
            table[figure.get(i)[0]][figure.get(i)[1]] = 0;
            figure.get(i)[0]++;
            table[figure.get(i)[0]][figure.get(i)[1]] = 1;
        }
        con.frame.review();
    }

    public boolean containPoint(int[] point) {
        for (int i = 0; i < figure.size(); i++) {
            int[] d = figure.get(i);
            if (d[0] == point[0] && d[1] == point[1]) {
                return true;
            }
        }
        return false;
    }
}
