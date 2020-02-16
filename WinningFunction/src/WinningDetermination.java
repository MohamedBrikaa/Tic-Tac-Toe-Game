/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
public class WinningDetermination {

    enum boxState {

        Blank, X, O
    }; //New concept for me: An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables).

    static int cells = 3; //number of boxes per column or   row

    static boxState[][] gameBoard = {{boxState.X, boxState.X, boxState.O},
                                    {boxState.O, boxState.X, boxState.X},
                                    {boxState.X, boxState.O, boxState.O}};

    static int movesNumber;

    public static void Move(int row, int column, boxState s) {

        //Check If the cell is empty
        if (gameBoard[row][column] == boxState.Blank) {
            gameBoard[row][column] = s;
        }
        movesNumber++;

        //check end conditions:
        //check col
        for (int i = 0; i < cells; i++) {
            if (gameBoard[row][i] != s) {
                break;
            }
            if (i == cells - 1) {
                System.out.println("The winner is" + s);

            }
        }

        //check row
        for (int i = 0; i < cells; i++) {
            if (gameBoard[i][column] != s) {
                break;
            }
            if (i == cells - 1) {
                System.out.println("The winner is" + s);
            }
        }

        //check diagonal
        if (row == column) {
            //we're on a diagonal
            for (int i = 0; i < cells; i++) {
                if (gameBoard[i][i] != s) {
                    break;
                }
                if (i == cells - 1) {
                    System.out.println("The winner is" + s);
                }
            }
        }

        //check anti diagonal (thanks rampion)
        if (row + column == cells - 1) {
            for (int i = 0; i < cells; i++) {
                if (gameBoard[i][(cells - 1) - i] != s) {
                    break;
                }
                if (i == cells - 1) {
                    System.out.println("The winner is" + s);
                }
            }
        }

        //check draw
        if (movesNumber == (Math.pow(cells, 2) - 1)) {
            System.out.println("Draw");
        }
    }

    public static void main(String[] args) {


        Move(1, 1, gameBoard[1][1]);
         

    }
}
