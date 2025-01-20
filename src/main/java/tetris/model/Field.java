package tetris.model;

import tetris.Connector;

public class Field {
    public int[][] table = new int[20][10];
    public Figure figure;
    public Shadow shadow;
    public Connector con;


    int color = 0;
    final int center = 2;


    public boolean canCreateFigure(Figure fig) {
        for (int i = 0; i < fig.getFigure().size(); i++) {
            if (table[fig.getPoint(i).getX()][fig.getPoint(i).getY()] > 0) {
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
        Figure fig = new Figure();
        if (canCreateFigure(fig)) {
            figure = fig;
            for (int i = 0; i < figure.getFigure().size() ; i++) {
                table[fig.getPoint(i).getX()][fig.getPoint(i).getY()] = color;
            }
            showShadow(true);
        }
        else {
            con.game.isRunning = false;
        }
    }

    public boolean canMoveDown() {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            Point curPoint = figure.getPoint(i);
            Point nextPoint = figure.getPoint(i).getCopy();
            nextPoint.setX();
            if (curPoint.getX() == table.length - 1 || (table[curPoint.getX() + 1][curPoint.getY()] > 0 && !containPoint(nextPoint))) {
                return false;
            }
        }
        return true;
    }

    public boolean shadowCanMoveDown(Shadow shadowCopy) {
        for (int i = 0; i < shadowCopy.getFigure().size(); i++) {
            Point curPoint = shadowCopy.getPoint(i);
            Point nextPoint = shadowCopy.getPoint(i).getCopy();
            nextPoint.setX();
            if (curPoint.getX() == table.length - 1 || (table[curPoint.getX() + 1][curPoint.getY()] > 0 && !containPoint(nextPoint))) {
                return false;
            }
        }
        return true;
    }

    public void moveDown() {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            table[figure.getPoint(i).getX()][figure.getPoint(i).getY()] = 0;
            figure.getPoint(i).setX();
        }
        drawFigure(color, true);
        deleteRow();
    }

    public boolean canMoveRight() {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            Point curPoint = figure.getPoint(i);
            Point nextPoint = figure.getPoint(i).getCopy();
            nextPoint.setY(true);
            if (curPoint.getY() == table[0].length || (table[curPoint.getX()][curPoint.getY() + 1] != 0 && !containPoint(nextPoint))) {
                return false;
            }
        }
        return true;
    }

    public void moveRight() {
        if (canMoveRight()) {
            for (int i = figure.getFigure().size() - 1; i > -1; i--) {
                table[figure.getPoint(i).getX()][figure.getPoint(i).getY()] = 0;
                figure.getPoint(i).setY(true);
            }
            drawFigure(color, true);
            showShadow(false);
        }
    }

    public boolean canMoveLeft() {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            Point curPoint = figure.getPoint(i);
            Point nextPoint = figure.getPoint(i).getCopy();
            nextPoint.setY(false);
            if (curPoint.getY() == 0 || (table[curPoint.getX()][curPoint.getY() - 1] != 0 && !containPoint(nextPoint))) {
                return false;
            }
        }
        return true;
    }

    public void moveLeft() {
        if (canMoveLeft()) {
            for (int i = 0; i < figure.getFigure().size(); i++) {
                table[figure.getPoint(i).getX()][figure.getPoint(i).getY()] = 0;
                figure.getPoint(i).setY(false);
            }
            drawFigure(color, true);
            showShadow(false);
        }
    }

    public boolean containPoint(Point point) {
        for (int i = 0; i < figure.getFigure().size(); i++) {
            Point d = figure.getPoint(i);
            if (d.getX() == point.getX() && d.getY() == point.getY()) {
                return true;
            }
        }
        return false;
    }

    public void showShadow(boolean isDown) {
        if (!isDown) {
            cleanShadow();
        }
        Shadow shadowBeforeDown = new Shadow(figure);
        while (shadowCanMoveDown(shadowBeforeDown)) {
            for (int i = 0; i < shadowBeforeDown.getFigure().size(); i++) {
                shadowBeforeDown.getPoint(i).setX();
            }
        }
        shadow = shadowBeforeDown;
        drawFigure(-1, false);
    }

    private void cleanShadow(){
        for (int i = 0; i < shadow.getFigure().size(); i++) {
            if (!containPoint(shadow.getPoint(i))) {
                table[shadow.getPoint(i).getX()][shadow.getPoint(i).getY()] = 0;
            }
        }
    }

    public void rapidFall(){
        drawFigure(0, true);
        drawFigure(color, false);
        createNewFigure();
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
            con.frame.setScore(true);
            drawFigure(0, true);
            table = removeRows(table);
            drawFigure(color, true);
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

    public void cleanField() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = 0;
            }
        }
        figure = null;
        shadow = null;
        color = 0;
    }

    private void drawFigure(int col, boolean isFigure) {
        if (isFigure) {
            for (int i = 0; i < figure.getFigure().size(); i++) {
                table[figure.getPoint(i).getX()][figure.getPoint(i).getY()] = col;
            }
        }
        else {
            for (int i = 0; i < shadow.getFigure().size(); i++) {
                table[shadow.getPoint(i).getX()][shadow.getPoint(i).getY()] = col;
            }
        }
    }


    public void rotation() {
        int firstParameter = 1;
        int secondParameter = 3;
        if (figure.indexOfFigure == 1) {
            firstParameter = 2;
            secondParameter = 5;
        } else if (figure.indexOfFigure == 0) {
            return;
        }
        int x = figure.getPoint(center).getX() - firstParameter;
        int y = figure.getPoint(center).getY() - firstParameter;
        boolean flag = true;
        Figure rotatedFigure = new Figure(figure);
        rotatedFigure.indexOfFigure = figure.indexOfFigure;
        for (int i = 0; i < rotatedFigure.getFigure().size(); i++) {
            Point cur = rotatedFigure.getPoint(i);
            int div;
            div = secondParameter - (cur.getY() - y) - 1;
            cur.setY(cur.getX() - x);
            cur.setX(div);
            cur.setX(cur.getX() + x);
            cur.setY(cur.getY() + y);
            if (cur.getX() < 0 || cur.getX() > table.length - 1 || cur.getY() < 0 || cur.getY() > table[0].length || (table[cur.getX()][cur.getY()] > 0 && !containPoint(cur))) {
                flag = false;
            }
        }
        if (flag) {
            drawFigure(0, true);
            figure = rotatedFigure;
            drawFigure(color, true);
            showShadow(false);
        }
    }
}
