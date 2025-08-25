package SnakeGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //storing the body of the snake

import java.util.Random; // for random food location
import javax.swing.*;

// inheritance form JPanel class
// keylistener for take the postion from the user
public class snakegame extends JPanel implements ActionListener, KeyListener {
    // private class to keep track the postion
    private class tile {
        int x;
        int y;

        tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardheight;
    int boardwidth;
    int tilesize = 25; // the samll boxes in board

    tile snakehead; // object from tile class
    ArrayList<tile> snakebody;

    tile food; // object for the food
    Random random;

    // game logic
    Timer gameloop;
    boolean gameover = false;

    // for moveing
    int velocityx;
    int velocityy;

    public snakegame(int bw, int bh) {
        this.boardheight = bh;
        this.boardwidth = bw;
        setPreferredSize(new Dimension(bw, bh)); // make the game with these dimension
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true); // make snake game listen to the kaypressed

        snakehead = new tile(5, 5); // default starting place
        snakebody = new ArrayList<tile>();

        food = new tile(10, 10);
        random = new Random();
        placefood();

        velocityx = 0; // deffult values for snake
        velocityy = 0;

        gameloop = new Timer(150, this);
        gameloop.start();

    }

    // paint the game
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // grid lines
        for (int i = 0; i < boardheight / tilesize; i++) {
            // (x1,y1,x2,y2)
            g.drawLine(i * tilesize, 0, i * tilesize, boardheight);
            g.drawLine(0, i * tilesize, boardwidth, i * tilesize);
        }

        // food color and dimensions
        g.setColor(Color.red);
        g.fillRect(food.x * tilesize, food.y * tilesize, tilesize, tilesize);

        // snakes color and Dimensions
        g.setColor(Color.green);
        g.fillRect(snakehead.x * tilesize, snakehead.y * tilesize, tilesize, tilesize);

        // make snake body when eat the food
        for (int i = 0; i < snakebody.size(); i++) {
            tile snakepart = snakebody.get(i);
            g.fillRect(snakepart.x * tilesize, snakepart.y * tilesize, tilesize, tilesize);
        }

        if (gameover) {
            // g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 50)); // change size here
            g.drawString("GAME OVER", boardwidth / 2 - 150, boardheight / 2 - 100);
        }

    }

    // class for random place every time for the food
    public void placefood() {
        food.x = random.nextInt(boardwidth / tilesize); // 600/25 = 24
        food.y = random.nextInt(boardheight / tilesize); // from 0 to 24
    }

    // when snake eat food ,food becomes snake body
    public boolean collision(tile tile1, tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // eat food
        if (collision(snakehead, food)) {
            snakebody.add(new tile(food.x, food.y));
            placefood();
        }

        // snakebody follow snake head
        for (int i = snakebody.size() - 1; i >= 0; i--) {
            tile snakepart = snakebody.get(i);
            if (i == 0) {
                snakepart.x = snakehead.x;
                snakepart.y = snakehead.y;
            } else {
                tile prevsnakepart = snakebody.get(i - 1);
                snakepart.x = prevsnakepart.x;
                snakepart.y = prevsnakepart.y;
            }
        }

        // snake head
        snakehead.x += velocityx;
        snakehead.y += velocityy;

        // game ovar conditions
        for (int i = 0; i < snakebody.size(); i++) {
            tile snakepart = snakebody.get(i);
            if (collision(snakehead, snakepart)) {
                gameover = true;
            }
        }

        if (snakehead.x * tilesize < 0 || snakehead.y * tilesize < 0 ||
                snakehead.x * tilesize > 600 || snakehead.y * tilesize > 600) {
            gameover = true;
        }

    }

    public void actionPerformed(ActionEvent e) {
        // move functoin to update x and y postion of the snake
        move();
        // calls function draw multiples of times
        repaint();

        if (gameover) {
            gameloop.stop();

            // Ask the player if they want to play again
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to play again?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                restartGame(); // restart if YES
            } else {
                System.exit(0); // exit game if NO
            }
        }
    }

    // restart game method
    private void restartGame() {
        snakehead = new tile(5, 5);
        snakebody.clear();
        velocityx = 0;
        velocityy = 0;
        placefood();
        gameover = false;
        gameloop.start();
    }

    // these are 3 methods from keylistener we will use just keypressed
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityy != 1) // snake moves in one direction, we make it unable to
                                                                // move in the opposite direction.
        {
            velocityx = 0;
            velocityy = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityy != -1) {
            velocityx = 0;
            velocityy = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityx != -1) {
            velocityx = 1;
            velocityy = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityx != 1) {
            velocityx = -1;
            velocityy = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
