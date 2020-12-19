package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    static boolean gameOver = false;
    static String player = "O", opponent = "X";
    static String gameLevel = "unknown";
    static Label lvl;

    static class Move {

        int row, col;
    };
    static Stage primarystage;
    @FXML

    Button L1;
    @FXML
    Button L2;
    @FXML
    Button L3;
    @FXML
    Button C1;
    @FXML
    Button C2;
    @FXML
    Button C3;
    @FXML
    Button R1;
    @FXML
    Button R2;
    @FXML
    Button R3;
    @FXML
    Label level;
    @FXML
    Label winLabel;

    Button[][] board;

    public void setL1() {
        playerPlay(board[0][0]);
    }

    public void setL2() {
        playerPlay(board[0][1]);

    }

    public void setL3() {
        playerPlay(board[0][2]);

    }

    public void setC1() {
        playerPlay(board[1][0]);
    }

    public void setC2() {
        playerPlay(board[1][1]);

    }

    public void setC3() {
        playerPlay(board[1][2]);

    }

    public void setR1() {
        playerPlay(board[2][0]);
    }

    public void setR2() {
        playerPlay(board[2][1]);
    }

    public void setR3() {
        playerPlay(board[2][2]);

    }

    public void resetGame(ActionEvent actionEvent) {
        for (Button[] board1 : board) {
            for (Button btn : board1) {
                btn.setText("");
            }
        }
        gameOver = false;
        gameLevel = "unknown";
        lvl.setText("Please Choose Level");
        winLabel.setText("");
    }

    public void playerPlay(Button btnPressed) {
        if (gameLevel.equals("unknown")) {
            System.out.println("you have to choose the level");
        }
        if (!(btnPressed.getText().equals("O") || btnPressed.getText().equals("X")) && !gameOver && !gameLevel.equals("unknown")) {
            {
                btnPressed.setText("X");
                checkGameOver();
                if (!gameOver) {
                    if (gameLevel.equals("easy")) {
                        easyAiPlay();
                    } else {
                        hardAiPlay();
                    }
                    checkGameOver();
                }
            }
        }
    }

    public void easyAiPlay() {
        boolean aiTurn = true;
        for (int i = 0; i < 3 && aiTurn; i++) {
            for (int j = 0; j < 3 && aiTurn; j++) {
                if (!(board[i][j].getText().equals("O") || board[i][j].getText().equals("X"))) {
                    board[i][j].setText("O");
                    aiTurn = false;
                }
            }
        }

    }

    public void hardAiPlay() {
        boolean aiTurn = true;
        for (int i = 0; i < 3 && aiTurn; i++) {
            for (int j = 0; j < 3 && aiTurn; j++) {
                if (!(board[i][j].getText().equals("O") || board[i][j].getText().equals("X")) && !gameOver) {
                    Move bestMove = findBestMove(board);

                    board[bestMove.row][bestMove.col].setText("O");

                    aiTurn = false;
                }
            }
        }
    }

    public void back(MouseEvent mouseEvent) {

        primarystage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Button[3][3];
        board[0][0] = L1;
        board[0][1] = L2;
        board[0][2] = L3;
        board[1][0] = C1;
        board[1][1] = C2;
        board[1][2] = C3;
        board[2][0] = R1;
        board[2][1] = R2;
        board[2][2] = R3;
        gameLevel = "unknown";
        gameOver = false;
        lvl = level;
        lvl.setText("Please Choose Level");
    }

    public void chooseEasyLevel(ActionEvent actionEvent) {

        if (gameLevel.equals("unknown")) {
            System.out.println("You choose Easy");
            gameLevel = "easy";
            lvl.setText(gameLevel.toUpperCase());
        }
    }

    public void chooseHardLevel(ActionEvent actionEvent) {
        if (gameLevel.equals("unknown")) {
            System.out.println("You choose Hard");
            gameLevel = "hard";
            lvl.setText(gameLevel.toUpperCase());
        }
    }

    public void checkGameOver() {
        int result = evaluate(board);
        if (result == 10) {
            System.out.println("Player O Wins");
            winLabel.setText("Player O Wins");
            gameOver = true;
        } else if (result == -10) {
            System.out.println("Player X Wins");
            gameOver = true;
            winLabel.setText("Player X Wins");
        }

        if (!isMovesLeft(board)) {
            System.out.println("Tie!");
            gameOver = true;
        }
    }

    static Boolean isMovesLeft(Button board[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    static int evaluate(Button board[][]) {
        // Checking for Rows for X or O victory. 
        for (int row = 0; row < 3; row++) {
            if (board[row][0].getText().equals(board[row][1].getText())
                    && board[row][1].getText().equals(board[row][2].getText())) {
                if (board[row][0].getText().equals(player)) {
                    return +10;
                } else if (board[row][0].getText().equals(opponent)) {
                    return -10;
                }
            }
        }

        // Checking for Columns for X or O victory. 
        for (int col = 0; col < 3; col++) {
            if (board[0][col].getText().equals(board[1][col].getText())
                    && board[1][col].getText().equals(board[2][col].getText())) {
                if (board[0][col].getText().equals(player)) {
                    return +10;
                } else if (board[0][col].getText().equals(opponent)) {
                    return -10;
                }
            }
        }

        // Checking for Diagonals for X or O victory. 
        if (board[0][0].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][2].getText())) {
            if (board[0][0].getText().equals(player)) {
                return +10;
            } else if (board[0][0].getText().equals(opponent)) {
                return -10;
            }
        }

        if (board[0][2].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][0].getText())) {
            if (board[0][2].getText().equals(player)) {
                return +10;
            } else if (board[0][2].getText().equals(opponent)) {
                return -10;
            }
        }
        // Else if none of them have won then return 0 
        return 0;
    }

    static int minimax(Button board[][], int depth, Boolean isMax) {
        int score = evaluate(board);

        // If Maximizer has won the game  
        // return his/her evaluated score 
        if (score == 10) {
            return score;
        }

        // If Minimizer has won the game  
        // return his/her evaluated score 
        if (score == -10) {
            return score;
        }

        // If there are no more moves and  
        // no winner then it is a tie 
        if (isMovesLeft(board) == false) {
            return 0;
        }

        // If this maximizer's move 
        if (isMax) {
            int best = -1000;

            // Traverse all cells 
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty 
                    if (board[i][j].getText().isEmpty()) {
                        // Make the move 
                        board[i][j].setText(player);

                        // Call minimax recursively and choose 
                        // the maximum value 
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move 
                        board[i][j].setText("");
                    }
                }
            }
            return best;
        } // If this minimizer's move 
        else {
            int best = 1000;

            // Traverse all cells 
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty 
                    if (board[i][j].getText().isEmpty()) {
                        // Make the move 
                        board[i][j].setText(opponent);

                        // Call minimax recursively and choose 
                        // the minimum value 
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move 
                        board[i][j].setText("");
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(Button board[][]) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function  
        // for all empty cells. And return the cell  
        // with optimal value. 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty 
                if (board[i][j].getText().isEmpty()) {
                    // Make the move 
                    board[i][j].setText(player);

                    // compute evaluation function for this 
                    // move. 
                    int moveVal = minimax(board, 0, false);

                    // Undo the move 
                    board[i][j].setText("");

                    // If the value of the current move is 
                    // more than the best value, then update 
                    // best/ 
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        System.out.printf("The value of the best Move "
                + "is : %d\n\n", bestVal);
        return bestMove;
    }
}
