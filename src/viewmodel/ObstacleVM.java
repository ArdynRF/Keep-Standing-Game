package viewmodel;

import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
// mengakses konstanta
import static viewmodel.Default.gameOption.GAME_HEIGHT;
import static viewmodel.Default.gameOption.GAME_WIDTH;
// mengakses model
import model.Obstacle;

/**
 *
 * @author adin
 */
public class ObstacleVM {
    private static final int MAX_X = GAME_WIDTH - 50;
    private static final int MIN_X = 250;
    private static final Random rand = new Random(); // inisialisasi library random
    
    private static final int MAX_OBSTACLE = 15; // jumlah maksimum obstacle dalam satu frame
    private static final int MIN_GAP = 150; // lebar gap minimum antara obstacle
    private static final int obstacleHeight = 50; // tinggi obstacle
    private int obstacleNumber = 0; // jumlah obstacle
    private final ArrayList<Obstacle> obstacles = new ArrayList<>(); // list obstacle
    
    public ObstacleVM() {
        // konstruktor
    }
        
    public void updateObstacle(){
        // mengupdate kondisi obstacle
        Iterator<Obstacle> iterator = obstacles.iterator(); // iterator untuk setiap obstacle
        while(iterator.hasNext()) {
            // selama obstacle masih ada
            Obstacle obstacle = iterator.next();
            if(obstacle.getY() < -50){
                // jika posisi y obstacle melebihi batas y frame
                iterator.remove(); // menghapus obstacle
                obstacleNumber--; // decrement jumlah obstacle
            } else {
                // jika tidak, update posisi obstacle
                obstacle.update();
            }
        }
    }
    
    public void renderObstacle(Graphics g){
        // merender obstacle
        for (Obstacle obstacle : obstacles) {
            // untuk setiap obstacle
            obstacle.render(g); // gambar objeknya
        }
    }
    
    public void addObstacle(){
        // menambah jumlah obstacle
        if(obstacleNumber < MAX_OBSTACLE){
            // jika jumlah obstacle dalam frame masih kurang dari batas maksimum obstacle
            float x = 0; // posisi x di paling kiri
            float width = rand.nextInt((MAX_X - 50) - MIN_X) + MIN_X;
            float y = GAME_HEIGHT; // posisi y di paling bawah
            int range = (100 - 20) / 10;  // Menentukan rentang angka yang akan dihasilkan (dalam kelipatan 10)
            int stand = rand.nextInt(range + 1) * 10 + 20;  // Menghasilkan angka acak dalam kelipatan 10 dari 20 hingga 100
            boolean stat = false;
            if(obstacleNumber >= 1){
                // jika jumlah obstacle lebih dari 1
                // maka ambil obstacle, simpan data y obstacle
                y = obstacles.get(obstacles.size() - 1).getY() + ((rand.nextInt(3 - 1) + 1) * MIN_GAP);
            }
            // buat obstacle baru
            Obstacle obstacle = new Obstacle(x, y, (int) (MAX_X - width), obstacleHeight, stand, stat);
            obstacles.add(obstacle); // tambahkan ke dalam list
            obstacleNumber++; // increment jumlah obstacle
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        // mengambil obstacle
        return obstacles;
    }
}
