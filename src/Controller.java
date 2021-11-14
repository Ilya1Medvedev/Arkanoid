import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller extends JPanel implements ActionListener, KeyListener {
    private int playerX = 200;
    private int ballX = 120;
    private int ballY = 350;
    private int directionX = 1;
    private int directionY = 2;
    private Timer timer;
    private int delay = 0;
    private int bricksInCol = 3;
    private int bricksInRow = 7;
    private int totalBricks = bricksInCol * bricksInRow;

    private Bricks bricks;

    public Controller() {
        bricks = new Bricks(bricksInCol, bricksInRow);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        requestFocusInWindow();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);

        bricks.draw((Graphics2D) g);

        g.setColor(Color.BLUE);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.RED);
        g.fillRect(0, 0, 3, 600);
        g.fillRect(0, 0, 600, 3);
        g.fillRect(583, 0, 3, 600);

        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 20, 20);

//        g.dispose();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 480) {
                playerX = 480;
            } else {
                playerX += 20;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                playerX -= 20;
            }
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ballX <= 0) {
            directionX = -directionX;
        }
        if (ballX >= 570) {
            directionX = -directionX;
        }
        if (ballY <= 0) {
            directionY = -directionY;
        }
        if(ballY>=570){
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game over");
        }

        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
        Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);

        if (ballRect.intersects(paddleRect)) {
            directionY = -directionY;
        }

        for (int i = 0; i < bricks.getMap().length; i++) {
            for (int j = 0; j < bricks.getMap()[0].length; j++) {
                if (bricks.map[i][j] > 0) {
                    int brickX = j * bricks.brickWidth + 80;
                    int brickY = i * bricks.brickHeight + 50;
                    int brickWidth = bricks.brickWidth;
                    int brickHeight = bricks.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle brickRect = rect;

                    if (ballRect.intersects(brickRect)) {
                        bricks.setBrickValue(0, i, j);
                        totalBricks--;


                        if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                            directionX = -directionX;
                        } else {
                            directionY = -directionY;
                        }
                    }
                }
            }
        }

        ballX += directionX;
        ballY += directionY;


        repaint();

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
