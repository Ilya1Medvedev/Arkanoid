import javax.swing.*;

public class Game {
    public static final int PAGE_SIZE = 600;
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Controller controller = new Controller();
        obj.setSize(PAGE_SIZE, PAGE_SIZE);
        obj.setTitle("Arkanoid");
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setLocationRelativeTo(null); //frame appears in the center of screen
        obj.add(controller);
    }
}
