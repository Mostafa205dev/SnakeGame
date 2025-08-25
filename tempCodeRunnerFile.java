import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import SnakeGame.snakegame;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting Snake Game...");
        SwingUtilities.invokeLater(() -> {
            int boardwidth = 600;
            int boardheight= 600;

            JFrame frame = new JFrame("Snake Game");
            frame.setVisible(true);
            frame.setSize(boardwidth,boardheight); //set the deimiontion of the window
            frame.setLocationRelativeTo(null); //open the window in senter of the screen
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when user click x the window close 
            snakegame snakegame =new snakegame(boardwidth, boardheight); //create object
            frame.add(snakegame);
            frame.pack(); // to make the board true 600 deimentions

            snakegame.requestFocus(); 
        });
        
        scanner.close();
    }


    
}
