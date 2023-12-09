package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import viewmodel.Default;
import static viewmodel.Default.gameOption.GAME_SPEED;

public class Obstacle extends GameObject {
    private final int standing; // Skor standing obstacle
    private boolean stat; // Status obstacle (aktif atau tidak)
    Random rand = new Random(); // Objek untuk menghasilkan bilangan acak
    Color color; // Warna obstacle

    public Obstacle(float x, float y, int width, int height, int standing, boolean stat) {
        super(x, y, width, height);
        this.standing = standing;
        this.stat = stat;
        setColorObstacle(); // Mengatur warna obstacle
    }

    private void setColorObstacle() {
        // mengatur nilai warna obstacle
        int r = ((Default.gameOption.GAME_HEIGHT / 255) * height) % 255; // Komputasi warna acak
        int g = rand.nextInt(255);
        int b = rand.nextInt(255 - 100) + 100;
        color = new Color(r, g, b); // Warna acak untuk tipe bukan lantai
        
    }

    public void update() {
        updatePos(); // Mengupdate posisi obstacle
        updateCollisionBox(); // Mengupdate collision box obstacle
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawString(Integer.toString(this.standing), (int) x + width + 20, (int) y + height - 20); // Menampilkan skor standing obstacle
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height); // Menggambar persegi panjang obstacle dengan warna yang telah ditentukan
    }

    private void updatePos() {
        //mengatur pergerakan obstacle
        y -= GAME_SPEED; // Mengupdate posisi obstacle berdasarkan kecepatan game
    }

    public float getX() {
        return x; // Mendapatkan nilai x obstacle
    }

    public float getY() {
        return y; // Mendapatkan nilai y obstacle
    }

    public int getStand() {
        return standing; // Mendapatkan skor standing obstacle
    }

    public boolean getStat() {
        return stat; // Mendapatkan status obstacle
    }

    public void setStat(boolean stat) {
        this.stat = stat; // Mengatur status obstacle
    }
}
