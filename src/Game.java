import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Controller controller = new Controller();
        obj.setSize(600, 600);
        obj.setTitle("Arkanoid");
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setLocationRelativeTo(null); //frame appears in the center of screen
        obj.add(controller);
    }
}
