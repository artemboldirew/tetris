package tetris.model;

import tetris.Connector;

import static tetris.Config.speed;

public class Game {
    public Connector con;
    public boolean isRunning = false;
    public int score = 0;


    public void mainGame() throws InterruptedException {
        restartGame();
        con.field.createNewFigure();
        con.frame.review();
        while (isRunning) {
            if (con.field.canMoveDown()) {
                Thread.sleep(speed);
                con.field.moveDown();
                con.frame.review();
            }
            else {
                con.field.createNewFigure();
                con.frame.review();
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
        con.frame.review();
    }
}
