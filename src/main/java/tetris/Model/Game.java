package tetris.Model;

import tetris.Connector;

public class Game {
    public Connector con;
    public boolean isRunning = true;
    public int score;


    public void mainGame() throws InterruptedException {
        con.field.createNewFigure();
        while (isRunning) {
            if (con.field.canMoveDown()) {
                Thread.sleep(400);
                con.field.moveDown();
            }
            else {
                con.field.createNewFigure();
            }
        }
    }
}
