package tetris.Model;

import tetris.Connector;
import tetris.Config.*;

import static tetris.Config.speed;

public class Game {
    public Connector con;
    public boolean isRunning = false;
    public int score = 0;


    public void mainGame() throws InterruptedException {
        restartGame();
        con.field.createNewFigure();
        while (isRunning) {
            if (con.field.canMoveDown()) {
                Thread.sleep(speed);
                con.field.moveDown();
            }
            else {
                con.field.createNewFigure();
            }
        }
        cleanGame();
        con.frame.setScore(false);
    }

    public void restartGame() {
        score = 0;
        con.frame.setScore(true);
    }

    public void cleanGame() {
        con.field.cleanField();
        isRunning = true;
        con.frame.review();
    }
}
