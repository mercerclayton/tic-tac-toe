package tictactoe;

import java.util.Arrays;

/**
 * The board class represents the game board for TicTacToe.
 *
 * @version 1.10 01 Sep 2023
 * @author Clayton Mercer
 */
public class Board {

    private int xs;
    private int os;
    private int turn;

    // Tracks the state of each player's win conditions
    private boolean xxx;
    private boolean ooo;
    private final char[][] field;
    private GameState state;

    /**
     * Constructor method to initialize the game board.
     *
     * @param field 2D array representing the game board
     */
    public Board(char[][] field) {
        this.field = field;

        // '_' represents a blank field on the game board
        for (char[] rows : field) {
            Arrays.fill(rows, '_');
        }
        printBoard();
    }

    /**
     * Prints the game board to the console.
     */
    private void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.println(String.format("| %c %c %c |", field[i][0], field[i][1], field[i][2]));
        }
        System.out.println("---------");
        state = GameState.PLAYING;
    }

    /**
     * Checks if the game is operational.
     */
    public boolean isPlaying() {
        return state != GameState.OFF;
    }

    /**
     * Processes the moves made by each player depending on the state of the game board.
     *
     * @param userInput Input from the players
     */
    public void checkMove(String userInput) {
        switch (state) {
            case PLAYING -> {
                processMove(userInput);
                checkWin();
                break;
            }
            default -> {
                break;
            }
        }
    }

    /**
     * Processes the user's move and makes changes to the game board.
     *
     * @param move Coordinates on the game board
     */
    private void processMove(String move) {
        String[] coordinates = move.split(" ");
        try {
            if (coordinates[0].matches("[^1-3]") || coordinates[1].matches("[^1-3]")) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                int nRow = Integer.parseInt(coordinates[0]) - 1;
                int nColumn = Integer.parseInt(coordinates[1]) - 1;
                if (field[nRow][nColumn] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    field[nRow][nColumn] = turn % 2 == 0 ? 'X' : 'O';
                    turn++;
                    countXO();
                    printBoard();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input!");
        }
    }

    /**
     * Counts the number of X's and O's on the board.
     */
    private void countXO() {
        xs = 0;
        os = 0;
        for (int i = 0; i < 9; i++) {
            if (field[i/3][i%3] == 'X') {
                xs += 1;
            } else if (field[i/3][i%3] == 'O') {
                os += 1;
            }
        }
    }

    /**
     * Checks if row or column or any of the diagonals is a win for either player.
     */
    private void checkWin() {
        for (int i = 0; i < 3; i++) {
            int row = 0;
            int clm = 0;
            int mDiag = 0;
            int aDiag = 0;
            for (int j = 0; j < 3; j++) {
                row += field[i][j];
                clm += field[j][i];
                mDiag += field[j][j];
                aDiag += field[j][2-j];
            }

            // ASCII value for X is 88 (X+X+X is 264)
            // ASCII value for O is 79 (O+O+O is 237)
            xxx = xxx || row == 264 || clm == 264 || mDiag == 264 || aDiag == 264;
            ooo = ooo || row == 237 || clm == 237 || mDiag == 237 || aDiag == 237;

            if (Math.abs(xs - os) > 1 || xxx && ooo || xxx || ooo || xs + os == 9) {
                printResult();
            }
        }
    }

    /**
     * Prints the result of the game to the console.
     */
    private void printResult() {
        String result = null;
        if (Math.abs(xs-os) > 1 || xxx && ooo) {
            result = "Impossible";
        } else if (xxx) {
            result = "X wins";
        } else if (ooo) {
            result = "O wins";
        } else if (xs + os == 9) {
            result = "Draw";
        }
        System.out.println(result);
        state = GameState.OFF;
    }

}
