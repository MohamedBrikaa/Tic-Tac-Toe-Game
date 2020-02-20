/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */

/**
 *
 * @author pc
 */
public class Winning {
   
  
    enum boxState {

        Blank, X, O
    }; //New concept for me: An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables).
   
    static int cells = 3; //number of boxes per column or   row
//
//    static boxState[][] gameBoard = {{boxState.X, boxState.X, boxState.O},
//                                    {boxState.O, boxState.X, boxState.X},
//                                    {boxState.X, boxState.O, boxState.O}};
    
    static String[][] gameBoard = {{"X","X","O"},
                                    {"X","X","O"},
                                    {"O","X","O"}};

   //static String[][] gameBoard = new String[cells][cells];
     
    static int movesNumber;

    public static String Move(int row, int column, String mark) {

        //Check If the cell is empty
        if (gameBoard[row][column] == "X") {
            gameBoard[row][column] = mark;
        }
        movesNumber++;

        //check end conditions:
        //check col
        for (int i = 0; i < cells; i++) {
            if (gameBoard[row][i] != mark) {
                break;
            }
            if (i == cells - 1) {
                System.out.println("The winner is" + mark);

            }
        }

        //check row
        for (int i = 0; i < cells; i++) {
            if (gameBoard[i][column] != mark) {
                break;
            }
            if (i == cells - 1) {
                System.out.println("The winner is" + mark);
                return"The winner is" + mark;
            }
        }

        //check diagonal
        if (row == column) {
            //we're on a diagonal
            for (int i = 0; i < cells; i++) {
                if (gameBoard[i][i] != mark ){
                    break;
                }
                if (i == cells - 1) {
                    System.out.println("The winner is" + mark);
                }
                return"The winner is" + mark;
            }
        }

        //check anti diagonal (thanks rampion)
        if (row + column == cells - 1) {
            for (int i = 0; i < cells; i++) {
                if (gameBoard[i][(cells - 1) - i] != mark) {
                    break;
                }
                if (i == cells - 1) {
                    System.out.println("The winner is" + mark);
                    return"The winner is" + mark;
                }
            }
           
        }
      
        //check draw //(Math.pow(cells, 2) - 1)
        if (movesNumber == (Math.pow(cells, 2) - 1)) {
            System.out.println("There's NO WINNER!");
            return mark+"There's NO WINNER!";
        }
        return null;
    }

    public static void main(String[] args) {
          
        
      
      
        String str=Move(2, 2, gameBoard[2][2]);
         
        System.out.println(str);
    }
}
