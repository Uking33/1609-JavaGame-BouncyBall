
import java.awt.*;
import java.awt.event.*;

public class GameFrame {

    public GameFrame() {
        Frame app = new Frame("GameFrame");

        app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        app.setLocation(300, 100);
        drawBall drawB = new drawBall();
        app.add(drawB, BorderLayout.CENTER);
      
        app.pack();
        app.setVisible(true);
        drawB.gameStart();
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
