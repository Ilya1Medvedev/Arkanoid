import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller extends JPanel implements ActionListener, KeyListener {

    // Constants
    private final int BALL_RADIUS = 20;
    private final int PADDLE_START_POINT_X = 200;
    private final int PADDLE_POINT_Y = 550;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 8;
    private final int BALL_START_POINT_X = 120;
    private final int BALL_START_POINT_Y = 350;
    private final int BRICKS_IN_COL = 3;
    private final int BRICKS_IN_ROW = 7;
    private final int MOVING_POINTS = 20;
    private final int BORDER_LENGTH = Game.PAGE_SIZE;
    private final int BORDER_WIDTH = 3;
    // SMESHENIE BRICKS V CENTR
    public static final int BRICK_X = 80;
    public static final int BRICK_Y = 50;


    private int playerX = PADDLE_START_POINT_X;
    private int ballX = BALL_START_POINT_X;
    private int ballY = BALL_START_POINT_Y;
    private int directionX = 1;
    private int directionY = 2;
    private Timer timer;
    private int delay = 0;

    private int totalBricks = BRICKS_IN_COL * BRICKS_IN_ROW;

    private Bricks bricks;

    public Controller() {

        bricks = new Bricks(BRICKS_IN_COL, BRICKS_IN_ROW);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        requestFocusInWindow();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.PAGE_SIZE, Game.PAGE_SIZE);

        //draw bricks
        bricks.draw((Graphics2D) g);

        //draw paddle
        g.setColor(Color.BLUE);
        g.fillRect(playerX, PADDLE_POINT_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        //draw borders
        g.setColor(Color.RED);

        // left border
        g.fillRect(0, 0, BORDER_WIDTH, BORDER_LENGTH);

        // top border
        g.fillRect(0, 0, BORDER_LENGTH, BORDER_WIDTH);

        // right border
        g.fillRect(583, 0, BORDER_WIDTH, BORDER_LENGTH);

        //draw ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_RADIUS, BALL_RADIUS);

    }


    @Override
    public void keyPressed(KeyEvent e) {

        //paddle moving
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 480) {
                playerX = 480;
            } else {
                playerX += MOVING_POINTS;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                playerX -= MOVING_POINTS;
            }
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // interactions with borders
        if (ballX <= 0) {
            directionX = -directionX;
        }
        if (ballX >= 570) {
            directionX = -directionX;
        }
        if (ballY <= 0) {
            directionY = -directionY;
        }
        //lose condition
        if (ballY >= 570) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game over");
        }
//        Assert.assertTrue();

        Rectangle ballRect = new Rectangle(ballX, ballY, BALL_RADIUS, BALL_RADIUS);
        Rectangle paddleRect = new Rectangle(playerX, PADDLE_POINT_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        if (ballRect.intersects(paddleRect)) {
            directionY = -directionY;
        }

        //magic
        for (int i = 0; i < bricks.getMap().length; i++) {
            for (int j = 0; j < bricks.getMap()[0].length; j++) {
                if (bricks.map[i][j] > 0) {
                    // sozdanie bricks objects
                    int brickX = j * bricks.brickWidth + BRICK_X;
                    int brickY = i * bricks.brickLength + BRICK_Y;
                    int brickWidth = bricks.brickWidth;
                    int brickHeight = bricks.brickLength;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
//                    Rectangle brickRect = rect;

                    if (ballRect.intersects(rect)) {
                        bricks.setBrickValue(0, i, j);
                        totalBricks--;


//                        if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                        if (ballX + BALL_RADIUS < rect.x || ballX > rect.x + rect.width) {
                            directionX = -directionX;
                        } else {
                            directionY = -directionY;
                        }
                    }
                }
            }
        }

        //ball moving
        ballX += directionX;
        ballY += directionY;


        repaint();

        //win condition
        if (totalBricks == 0) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "You win!");
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
