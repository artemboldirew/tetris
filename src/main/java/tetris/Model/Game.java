package tetris.Model;

import tetris.Connector;

public class Game {
    public Connector con;
    public boolean isRunning = true;
    public int score;


    public void mainGame() throws InterruptedException {
        con.field.createNewFigure();
        System.out.println("mainGame");
        while (isRunning) {
            if (con.field.canMoveDown()) {
                System.out.println("if");
                Thread.sleep(500);
                con.field.moveDown();
            }
            else {
                System.out.println("else");
                con.field.createNewFigure();
            }
        }
    }
}
