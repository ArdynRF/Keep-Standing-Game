package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import static viewmodel.Default.gameOption.*;

public class Player extends GameObject {
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean inAir = false;

    private float playerSpeed = 3.0f;
    private float jumpStrength = 10.0f;
    private float airSpeed = 0;
    private float gravity = 0.3f;
    private float xSpeed = 0;

    private int standing = 0;
    private int score = 0;
    private int count = 0;
    private int tempY = 0;

    public Player(int x, int y) {
        super(x, y, 50, 50); // Inisialisasi properti parent
    }

    public void update(ArrayList<Obstacle> ob) {
        updatePos(ob); // Mengupdate posisi obstacle
        updateCollisionBox(); // Mengupdate collision box obstacle
    }

    @Override
    public void render(Graphics g) {
        // Mengoverride method render pada parent

        // Background
        Image bg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/view.jpg"));
        g.drawImage(bg, 0, 0, null);

        // Main character
        Image Mainchar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/poke.png"));
        g.drawImage(Mainchar, (int) x, (int) y, 50, 50, null);

        // Skor adapt dan standing
        g.setFont(new java.awt.Font("Segoe UI", 1, 13));
        g.setColor(Color.WHITE);
        g.fillRoundRect(15, 5, 100, 20, 15, 15);
        g.fillRoundRect(15, 35, 100, 20, 15, 15);
        g.setColor(Color.decode("#3A1070"));
        g.drawString("Score : " + Integer.toString(this.score), 20, 20);
        g.drawString("Standing : " + Integer.toString(this.standing), 20, 50);
    }

    public void updatePos(ArrayList<Obstacle> AOb) {
        if (left) {
            xSpeed -= playerSpeed - 2; // Mengurangi kecepatan player saat tombol kiri ditekan
        } else if (right) {
            xSpeed += playerSpeed; // Menambah kecepatan player saat tombol kanan ditekan
        }

        if (xSpeed > 4) {
            xSpeed = 4; // Batasi kecepatan maksimum menjadi 4
        } else if (xSpeed < -4) {
            xSpeed = -6; // Batasi kecepatan minimum menjadi -6
        }

        if (up && !inAir) {
            // Melakukan lompat jika tombol atas ditekan dan tidak sedang dalam udara
            inAir = true;
            airSpeed -= jumpStrength;
        }

        if (!inAir) {
            inAir = true; // Player berada di lantai
        }

        if (inAir) {
            airSpeed += gravity; // Player sedang dalam udara
        }

        for (Obstacle ob : AOb) {
            if (getBoundBottom().intersects(ob.getCollisionBox())) {
                inAir = false; // Player tidak berada di udara
                airSpeed = 0; // Set kecepatan udara menjadi 0
                y = ob.getCollisionBox().y - height; // Atur posisi player agar tepat berada di atas obstacle

                if (standing == 0 && count == 0) {
                    tempY = (int) y; // Menyimpan posisi awal saat game baru dimulai
                    count++;
                }
                if (ob.getStat() == false) {
                    score = score + ob.getStand(); // Menambahkan skor berdasarkan standing obstacle
                    standing++;
                    ob.setStat(true); // Mengatur status obstacle menjadi aktif
                }
                if (up) {
                    inAir = true;
                    airSpeed -= jumpStrength; // Melakukan lompat jika tombol atas ditekan saat berada di atas obstacle
                }
            }

            // Mengembalikan kecepatan normal jika player bertabrakan dengan obstacle
            if (getBoundRight().intersects(ob.getCollisionBox())) {
                x = ob.getCollisionBox().x - width - 1; // Mengatur posisi player agar tidak bertabrakan dengan obstacle di sisi kanan
            }
            if (getBoundLeft().intersects(ob.getCollisionBox())) {
                x = ob.getCollisionBox().x + ob.getCollisionBox().width + 1; // Mengatur posisi player agar tidak bertabrakan dengan obstacle di sisi kiri
            }
            if (getBoundTop().intersects(ob.getCollisionBox())) {
                y = ob.getCollisionBox().y + height + 1; // Mengatur posisi player agar tidak bertabrakan dengan obstacle di bagian atas
                airSpeed = 0; // Set kecepatan udara menjadi 0
            }
            if (getBoundLeft().x < 0) {
                x = 0; // Mengatur posisi player agar tidak keluar dari batas kiri frame game
            }
            if (getBoundRight().x < 0) {
                x = GAME_WIDTH; // Mengatur posisi player agar tidak keluar dari batas kanan frame game
            }
        }
        x += xSpeed; // Mengupdate posisi player pada sumbu x
        y += airSpeed; // Mengupdate posisi player pada sumbu y
    }

    public Rectangle getBoundBottom() {
        // Membuat batas bawah player
        return new Rectangle((int) (x + (width / 2) - (width / 4)), (int) (y + (height / 2)), width / 2, height / 2);
    }

    public Rectangle getBoundTop() {
        // Membuat batas atas player
        return new Rectangle((int) (x + (width / 2) - (width / 4)), (int) (y), width / 2, height / 2);
    }

    public Rectangle getBoundRight() {
        // Membuat batas kanan player
        return new Rectangle((int) x + width - 5, (int) y + 5, 5, height - 10);
    }

    public Rectangle getBoundLeft() {
        // Membuat batas kiri player
        return new Rectangle((int) x, (int) y + 5, 5, height - 10);
    }

    public void setLeft(boolean left) {
        this.left = left; // Mengatur player ke arah kiri
    }

    public void setUp(boolean up) {
        this.up = up; // Mengatur player ke arah atas
    }

    public void setRight(boolean right) {
        this.right = right; // Mengatur player ke arah kanan
    }

    public void setDown(boolean down) {
        this.down = down; // Mengatur player ke arah bawah
    }

    public void setScore(int score) {
        this.score = score; // Mengatur skor total
    }

    public void setStanding(int standing) {
        this.standing = standing; // Mengatur skor standing
    }

    public int getScore() {
        return this.score; // Mengembalikan skor total
    }

    public int getStanding() {
        return this.standing; // Mengembalikan skor standing
    }
}
