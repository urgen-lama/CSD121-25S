package lab3.ui;

import java.util.Scanner;
import java.util.InputMismatchException;
import lab3.game.*;

public class Console {
    private Board gameBoard;
    private char currentPlayer;
    private int moves;
    private Scanner scanner;

    /**
     * Construct a new 'Console' instance for the TicTacToe game
     * gameBoard = new Board(); represents the current state of the game
     * currentPlayer = 'X'; represents the first player to 'X'
     * moves = 0; represents the total moves at the beginning
     */
    public Console() {
        gameBoard = new Board();
        currentPlayer = 'X';
        moves = 0;
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        boolean gameEnded = false;

        gameBoard.printBoard();

        while (!gameEnded) {
            System.out.println("Player " + currentPlayer + ", enter your move (row then enter and column then enter, e.g., 1 2):");

            int row = -1;
            int col = -1;
            boolean moveMade = false;

            while (!moveMade) {
                try {
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;

                    if (gameBoard.makeMove(row, col, currentPlayer)) {
                        moveMade = true;
                        moves++;
                        gameBoard.printBoard();
                    } else {
                        System.out.println("Invalid move. Please choose an empty cell within the board (1-3 for row/column).");
                    }
                } catch (InputMismatchException e) { //To check that the users does not input other value types
                    System.out.println("Invalid input. Please enter two numbers for row and column.");
                    scanner.next();
                    scanner.next();
                }
            }

            if (checkWin()) {
                System.out.println("Player " + currentPlayer + " wins!");
                gameEnded = true;
            } else if (checkDraw()) {
                System.out.println("It's a draw!");
                gameEnded = true;
            } else {
                switchPlayer();
            }
        }
        scanner.close();
        System.out.println("Game Over!");
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (gameBoard.getCell(i, 0) == currentPlayer &&
                    gameBoard.getCell(i, 1) == currentPlayer &&
                    gameBoard.getCell(i, 2) == currentPlayer) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (gameBoard.getCell(0, j) == currentPlayer &&
                    gameBoard.getCell(1, j) == currentPlayer &&
                    gameBoard.getCell(2, j) == currentPlayer) {
                return true;
            }
        }
        if ((gameBoard.getCell(0, 0) == currentPlayer &&
                gameBoard.getCell(1, 1) == currentPlayer &&
                gameBoard.getCell(2, 2) == currentPlayer) ||
                (gameBoard.getCell(0, 2) == currentPlayer &&
                        gameBoard.getCell(1, 1) == currentPlayer &&
                        gameBoard.getCell(2, 0) == currentPlayer)) {
            return true;
        }
        return false;
    }

    private boolean checkDraw() {
        return moves == 9 && !checkWin();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public static void main(String[] args) {
        Console game = new Console();
        game.startGame();
    }
}