package viewmodel;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Random;
import javax.sound.sampled.Clip;
import model.Player;
import model.TableUser;
import static viewmodel.Default.gameOption.GAME_WIDTH;
import view.GameWindow;
import view.Menu;

/**
 * Kelas Game merupakan JPanel yang digunakan untuk menggambar elemen permainan dan mengatur logika permainan.
 */
public class Game extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean running = false;
    private GameWindow window;
    private Clip audio;
    private final Player player;
    private final ObstacleVM obs_handler;
    private String username;
    private int score;
    private int standing;

    /**
     * Enum STATE berfungsi untuk menentukan status permainan, apakah dalam keadaan bermain atau game over.
     */
    public enum STATE{
        Game,
        GameOver
    }

    public Game(){
        Random rand = new Random();
        int playerPos = rand.nextInt(1200 - 800) + 800;
        this.player = new Player(GAME_WIDTH - playerPos, 0);
        this.obs_handler = new ObstacleVM();
        Sound bgm = new Sound();
        audio = bgm.playSound(this.audio, "game_song (2).wav");
    }

    public STATE gameState = STATE.Game;

    /**
     * Memulai permainan dengan menginisialisasi thread dan menjalankan perulangan permainan.
     * @param gw Objek GameWindow yang menampung panel Game.
     */
    public synchronized void StartGame(GameWindow gw){
        gameThread = new Thread(this);
        gameThread.start();
        this.window = gw;
        running = true;
    }

    /**
     * Menggambar elemen permainan pada panel.
     * @param g Objek Graphics untuk menggambar.
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        player.render(g);
        obs_handler.renderObstacle(g);
    }

    /**
     * Perulangan utama permainan yang berjalan dalam thread.
     * Mengupdate logika permainan, melakukan repaint panel, dan mengecek kondisi game over.
     */
    @Override
    public void run() {
    while(true){
        try {
            updateGame(); // Memperbarui logika permainan
            repaint(); // Menggambar ulang elemen permainan pada panel
            Thread.sleep(1000L/60L); // Memberikan jeda selama 1/60 detik (60 frame per detik)
            this.score = player.getScore(); // Mengambil nilai score pemain
            this.standing = player.getStanding(); // Mengambil nilai standing pemain
            if(this.player.getBoundBottom().y > 1000) {
                this.gameState = STATE.GameOver; // Memeriksa jika pemain melewati batas bawah
            }

            if(this.player.getBoundTop().y < 0) {
                this.gameState = STATE.GameOver; // Memeriksa jika pemain melewati batas atas
            }

            if(gameState == STATE.GameOver) {
                Sound bgm = new Sound(); 
                bgm.stopSound(this.audio); // Menghentikan pemutaran suara latar belakang
                saveScore(); // Menyimpan skor pemain
                close(); // Menutup jendela permainan
                new Menu().setVisible(true); // Membuat objek menu dan menampilkannya
                stopGame(); // Menghentikan permainan
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    /**
     * Memperbarui logika permainan dengan menambahkan dan memperbarui rintangan serta memperbarui pemain.
     */
    public void updateGame(){
        obs_handler.addObstacle();
        obs_handler.updateObstacle();
        player.update(obs_handler.getObstacles());
    }

    /**
     * Menghentikan permainan dengan menggabungkan thread dan mengubah status running menjadi false.
     */
    public synchronized void stopGame() {
        try{
            gameThread.join();
            running = false;
        }catch(InterruptedException e){
        }
    }

    /**
     * Menutup jendela permainan.
     */
    void close() {
        window.CloseWindow();
    }

    /**
     * Mengembalikan objek Player yang digunakan dalam permainan.
     * @return Objek Player.
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Mengatur username pemain.
     * @param username Username pemain.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Mengatur nilai score dan standing pemain.
     * @param score Nilai score.
     * @param standing Nilai standing.
     */
    public void setScore(int score, int standing) {
        this.player.setScore(score);
        this.player.setStanding(standing);
    }

    /**
     * Menyimpan skor pemain ke dalam database.
     */
    public void saveScore() {
        Sound gobgm = new Sound();
        audio = gobgm.playSound(this.audio, "game_song (3).wav");

        try {
            TableUser tuser = new TableUser();
            tuser.insertData(this.username, this.score, this.standing);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Username : " + this.username + "\nAdapt : " + this.score + "\nStanding : " + this.standing, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        gobgm.stopSound(this.audio);
    }
}
