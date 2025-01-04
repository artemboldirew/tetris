package tetris;

import tetris.Model.Field;
import tetris.Model.Game;
import tetris.View.Frame;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Field field = new Field();
        Frame frame = new Frame();
        Game game = new Game();
        Connector con = new Connector(game, field, frame);
        game.con = con;
        field.con = con;
        frame.con = con;
        frame.FrameMain();
        frame.buttonPushed();
        //con.game.mainGame();
    }
}
