package tetris;

public class Utils {
    public static int[][] deepCopy(int[][] inp) {
        int[][] out = new int[inp.length][inp[0].length];
        for (int i = 0; i < inp.length; i++) {
            out[i] = inp[i].clone();
        }
        return out;
    }
}
