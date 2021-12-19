import java.awt.*;

public class Bricks {

    private final int ALL_BRICKS_WIDTH = 440;
    private final int ALL_BRICKS_LENGTH = 100;

    public int map[][];
    public int brickWidth;
    public int brickLength;

    public Bricks(int row, int col) {
        map = new int[row][col];
        for (int[] map1 : map) {
            for (int j = 0; j < col; j++) {
                map1[j] = 1;
            }
        }

        //set bricks size
        brickWidth = ALL_BRICKS_WIDTH / col;
        brickLength = ALL_BRICKS_LENGTH / row;
    }

    public int[][] getMap() {
        return map;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {

                    //draw white bricks
                    g.setColor(Color.WHITE);
                    g.fillRect(j * brickWidth + Controller.BRICK_X, i * brickLength + Controller.BRICK_Y, brickWidth, brickLength);

                    //draw grid
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + Controller.BRICK_X, i * brickLength + Controller.BRICK_Y, brickWidth, brickLength);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
