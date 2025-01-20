package tetris.model;

import java.io.Serializable;

public class Point implements Serializable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX() {
        this.x++;
    }

    public Point getCopy() {
        return  new Point(this.x, this.y);
    }

    public void setY(boolean operation) {
        if (operation) {
            this.y++;
        }
        else {
            this.y--;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


}
