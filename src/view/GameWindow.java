package view;

import java.awt.Canvas;
import javax.swing.JFrame;
// mengakses package viewmodel
import viewmodel.Game;
import viewmodel.KeyInputs;
import static viewmodel.Default.gameOption.GAME_HEIGHT;
import static viewmodel.Default.gameOption.GAME_WIDTH;

/**
 *
 * @author adin
 */
public class GameWindow extends Canvas {
    // mewarisi kelas Canvas agar bisa digambar dalamnya
    
    // properti JFrame
    JFrame frame;
    
    public GameWindow(Game game){
        // konstruktor
        String title = "Keep Standing"; // set title
        frame = new JFrame(title); // buat frame
        frame.addKeyListener(new KeyInputs(game)); // menambah key listener
        frame.add(game); // menambah game ke frame
        frame.setSize(GAME_WIDTH, GAME_HEIGHT); // set ukuran frame
        frame.setLocationRelativeTo(null); // set lokasi frame dibuat
        frame.setResizable(false); // set resizeable frame
        frame.setVisible(true); // set frame agar visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close frame
    }
    
    public void CloseWindow() {
        // menutup window/frame
        frame.setVisible(false); // membuat frame invisible
        frame.dispose(); // membersihkan frame
    }
}
