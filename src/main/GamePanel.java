package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTING
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48 tile


    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //760 pixel
    final int screenHeight = tileSize * maxScreenRow; // 576 pixel
    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);


    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS; // 0.01666 seconds

        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {



            // System.out.println("The game loop is running");
            // 1 UPDATE: UPDATE information such as character position
            update();
            // 2 DRAW: draw the screen with the updated information
            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();

            remainingTime = remainingTime / 1_000_000;

            if(remainingTime < 0){
                remainingTime = 0;
            }

            try {
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }

    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;

        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
