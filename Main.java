package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Main class represents a program for a simple command-line game of Tic-Tac-Toe.
 *
 * It allows two players to play the game by entering the state of the game board.
 *
 * @version 1.10 01 Sep 2023
 * @author Clayton Mercer
 */
public class Main {

    /**
     * The main method serves as an entry point to the program.
     *
     * @param args Command-line arguments (not required)
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 3x3 character array to represent the game board.
        char[][] field = new char[3][3];
        Board board = new Board(field);
        while (board.isPlaying()) {
            board.checkMove(reader.readLine());
        }
    }

}