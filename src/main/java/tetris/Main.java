package tetris;

import tetris.model.Field;
import tetris.model.Game;
import tetris.view.Frame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Field field = new Field();
            Frame frame = new Frame();
            Game game = new Game();
            Connector con = new Connector(game, field, frame);
            game.con = con;
            field.con = con;
            frame.con = con;
            frame.FrameMain();
        });
    }
}
