package tetris.Model;

import tetris.Connector;
import tetris.View.Frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Field {
    public int[][] table = new int[20][10];
    public List<int[]> figure = new ArrayList<>();
    public List<int[]> shadow = new ArrayList<>();
    public Connector con;


    int color = 0;
    int center = 2;
    int indexOfFigure = -1;
    Random random = new Random();


    public boolean canCreateFigure(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (table[arr[i][0]][arr[i][1]] > 0) {
                return false;
            }
        }
        return true;
    }

    public void createNewFigure() {
        color = (color + 1) % 6;
        if (color == 0) {
            color++;
        }
        int[][] O = {{1, 4}, {0, 4}, {1, 5}, {0, 5}};
        int[][] I = {{0, 3}, {0, 4}, {0, 5}, {0, 6}};
        int[][] J = {{2, 4}, {2, 5}, {1, 5}, {0, 5}};
        int[][] L = {{2, 4}, {1, 4}, {0, 4}, {2, 5}};
        int[][] S = {{1, 4}, {1, 5}, {0, 5}, {0, 6}};
        int[][] T = {{0, 4}, {1, 5}, {0, 5}, {0, 6}};
        int[][] Z = {{0, 4}, {1, 5}, {0, 5}, {1, 6}};

        int[][][] figures = {O, I, J, L, S, T, Z};
        int index = random.nextInt(figures.length);
        indexOfFigure = index;
        int[][] figureArr = figures[index];
        if (canCreateFigure(figureArr)) {
            figure = Arrays.asList(figureArr);
            for (int i = 0; i < figure.size(); i++) {
                int[] cur = figure.get(i);
                table[cur[0]][cur[1]] = color;
            }
            showShadow(true);
        }
        else {
            con.game.isRunning = false;
        }
        con.frame.review();
    }

    public boolean canMoveDown() {
        for (int i = 0; i < figure.size(); i++) {
            int[] curPoint = figure.get(i);
            int[] nextPoint = Arrays.copyOf(curPoint, curPoint.length);
            nextPoint[0]++;
            if (curPoint[0] == 19 || (table[curPoint[0] + 1][curPoint[1]] > 0 && !containPoint(nextPoint, figure))) {
                return false;
            }
        }
        return true;
    }

    public boolean shadowCanMoveDown(List<int[]> figure) {
        for (int i = 0; i < figure.size(); i++) {
            int[] curPoint = figure.get(i);
            int[] nextPoint = Arrays.copyOf(curPoint, curPoint.length);
            nextPoint[0]++;
            if (curPoint[0] == 19 || (table[curPoint[0] + 1][curPoint[1]] > 0 && !containPoint(nextPoint, figure))) {
                return false;
            }
        }
        return true;
    }

    public void moveDown() {
        for (int i = 0; i < figure.size(); i++) {
            table[figure.get(i)[0]][figure.get(i)[1]] = 0;
            figure.get(i)[0]++;
        }
        for (int i = 0; i < figure.size(); i++) {
            table[figure.get(i)[0]][figure.get(i)[1]] = color;
        }
        deleteRow();
        con.frame.review();
    }

    public boolean canMoveRight() {
        for (int i = 0; i < figure.size(); i++) {
            int[] curPoint = figure.get(i);
            int[] nextPoint = Arrays.copyOf(curPoint, curPoint.length);
            nextPoint[1]++;
            if (curPoint[1] == 9 || (table[curPoint[0]][curPoint[1] + 1] != 0 && !containPoint(nextPoint, figure))) {
                return false;
            }
        }
        return true;
    }

    public void moveRight() {
        if (canMoveRight()) {
            for (int i = figure.size() - 1; i > -1; i--) {
                table[figure.get(i)[0]][figure.get(i)[1]] = 0;
                figure.get(i)[1]++;
            }
            for (int i = 0; i < figure.size(); i++) {
                table[figure.get(i)[0]][figure.get(i)[1]] = color;
            }
            showShadow(false);
            con.frame.review();
        }
    }

    public boolean canMoveLeft() {
        for (int i = 0; i < figure.size(); i++) {
            int[] curPoint = figure.get(i);
            int[] nextPoint = Arrays.copyOf(curPoint, curPoint.length);
            nextPoint[1]--;
            if (curPoint[1] == 0 || (table[curPoint[0]][curPoint[1] - 1] != 0 && !containPoint(nextPoint, figure))) {
                return false;
            }
        }
        return true;
    }

    public void moveLeft() {
        if (canMoveLeft()) {
            for (int i = 0; i < figure.size(); i++) {
                table[figure.get(i)[0]][figure.get(i)[1]] = 0;
                figure.get(i)[1]--;
            }
            for (int i = 0; i < figure.size(); i++) {
                table[figure.get(i)[0]][figure.get(i)[1]] = color;
            }
            showShadow(false);
            con.frame.review();
        }
    }

    public boolean containPoint(int[] point, List<int[]> figure) {
        for (int i = 0; i < figure.size(); i++) {
            int[] d = figure.get(i);
            if (d[0] == point[0] && d[1] == point[1]) {
                return true;
            }
        }
        return false;
    }

    public void showShadow(boolean isDown) {
        if (!isDown) {
            cleanShadow();
        }
        List<int[]> copyFigure = new ArrayList<>();
        for (int[] cur : figure) {
            copyFigure.add(cur.clone());
        }
        while (shadowCanMoveDown(copyFigure)) {
            for (int i = 0; i < copyFigure.size(); i++) {
                copyFigure.get(i)[0]++;
            }
        }
        for (int j = 0; j < copyFigure.size(); j++) {
            table[copyFigure.get(j)[0]][copyFigure.get(j)[1]] = -1;
        }
        shadow = copyFigure;
    }

    private void cleanShadow(){
        for (int i = 0; i < shadow.size(); i++) {
            if (!containPoint(shadow.get(i), figure)) {
                table[shadow.get(i)[0]][shadow.get(i)[1]] = 0;
            }
        }
    }

    public void rapidFall(){
        for (int i = 0; i < figure.size(); i++) {
            table[figure.get(i)[0]][figure.get(i)[1]] = 0;
        }
        for (int i = 0; i < shadow.size(); i++) {
            table[shadow.get(i)[0]][shadow.get(i)[1]] = color;
        }
        createNewFigure();
    }

    public void rotateFigure() {
        int x = figure.get(center)[0] - 1;
        int y = figure.get(center)[1] - 1;
        boolean flag = true;
        List<int[]> rotatedFigure = new ArrayList<>();
        for (int i = 0; i < figure.size(); i++) {
            int[] cur = figure.get(i).clone();
            int div;
            div = 3 - (cur[1] - y) - 1;
            cur[1] = cur[0] - x;
            cur[0] = div;
            cur[0] = cur[0] + x;
            cur[1] = cur[1] + y;
            if (cur[0] < 0 || cur[0] > 19 || cur[1] < 0 || cur[1] > 9 || (table[cur[0]][cur[1]] > 0 && !containPoint(cur, figure))) {
                flag = false;
            }
            rotatedFigure.add(cur);
        }
        if (flag) {
            for (int i = 0; i < figure.size(); i++) {
                int[] cud = figure.get(i);
                table[cud[0]][cud[1]] = 0;
            }
            figure = rotatedFigure;
            for (int i = 0; i < figure.size(); i++) {
                int[] cud = figure.get(i);
                table[cud[0]][cud[1]] = color;
            }
            showShadow(false);
            con.frame.review();
        }
    }

    public void rotateI(){
        int x = figure.get(center)[0] - 2;
        int y = figure.get(center)[1] - 2;
        boolean flag = true;
        int color = table[figure.get(0)[0]][figure.get(0)[1]];
        List<int[]> rotatedFigure = new ArrayList<>();
        for (int i = 0; i < figure.size(); i++) {
            int[] cur = figure.get(i).clone();
            int div;
            div = 5 - (cur[1] - y) - 1;
            cur[1] = cur[0] - x;
            cur[0] = div;
            cur[0] = cur[0] + x;
            cur[1] = cur[1] + y;
            if (cur[0] < 0 || cur[0] > 19 || cur[1] < 0 || cur[1] > 9 || (table[cur[0]][cur[1]] > 0 && !containPoint(cur, figure))) {
                flag = false;
            }
            rotatedFigure.add(cur);
        }
        if (flag) {
            for (int i = 0; i < figure.size(); i++) {
                int[] cud = figure.get(i);
                table[cud[0]][cud[1]] = 0;
            }
            figure = rotatedFigure;
            for (int i = 0; i < figure.size(); i++) {
                int[] cud = figure.get(i);
                table[cud[0]][cud[1]] = color;
            }
            showShadow(false);
            con.frame.review();
        }
    }

    public void mainRotation() {
        if (indexOfFigure == 1){
            rotateI();
        } else if (indexOfFigure > 1) {
            rotateFigure();
        }
    }

    public void deleteRow() {
        boolean flag = false;
        for (int i = 0; i < table.length; i++) {
            int amount = 0;
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j] > 0) {
                    amount++;
                }
            }
            if (amount == 10) {
                flag = true;
            }
        }
        if (flag) {
            con.game.score++;
            con.frame.setScore(con.game.score);
            for (int i = 0; i < figure.size(); i++) {
                table[figure.get(i)[0]][figure.get(i)[1]] = 0;
            }
            table = removeRows(table);
            for (int i = 0; i < figure.size(); i++) {
                table[figure.get(i)[0]][figure.get(i)[1]] = color;
            }
            showShadow(false);
        }
    }

    public int[][] removeRows(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        int index = newMatrix.length - 1;
        for (int i = matrix.length - 1; i > -1; i--) {
            int amount = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] > 0) {
                    amount++;
                }
            }
            if (amount < matrix[0].length) {
                newMatrix[index] = matrix[i].clone();
                index--;
            }
        }
        for (int i = index; i > -1; i--) {
            newMatrix[index] = new int[matrix[0].length];
            index--;
        }
        return newMatrix;
    }



    public void printFigure(List<int[]> figure){
        for (int i = 0; i < figure.size(); i++) {
            System.out.print(Arrays.toString(figure.get(i)) + " ");
        }
        System.out.println();
    }
}
