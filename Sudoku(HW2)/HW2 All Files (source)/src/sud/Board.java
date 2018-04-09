package sud;

//Emmanuel Jacquez & Hector Dozal
//Homework 1 (Sudoku)
//Cheon
/**
 * A  class to that initializes the sudoku Board that is used by the BoardPanel Class
 *
 * @see sudoku.model.BoardPanel
 * @author Emmanuel Jacquez & Hector Dozal
 * @ version 1.0
 * @since 03/03/2018
 */
import java.util.*;

//board class creates and updates a sudoku board using the consoleUI inputs from user with regards to
// size and X,Y,V;
import java.util.Arrays;

public class Board {

    public int size;
    private int[][] board;

    public Board(int size){
        this.size = size;
        this.board = new int[size][size];
        for (int[] row: board)
            Arrays.fill(row, 0);
    }

    public Board(int[][] board){
        size = board.length;
        this.board = board;
    }
/**change size of board
 * @param int [][] b
 */
    public void changeBoard(int[][] b){
        size = b.length;
        board = b;
    }
//checks sudoku if the board is complete
    public boolean isSolved(){

        for(int i = 0; i < size; i++){
            if(!search(board[i], 0))
                return false;
        }
        return true;
    }

/**method inserts integer value to sudoku using X Y coordinates 
 * 
 * @param int x
 * @param int y
 * @param num
 */
    public void insert(int x, int y, int num){
        System.out.println(x +  " " + y);
        int size3 = (int)Math.sqrt(size);
        int size2 = size;

        int[] row = board[y].clone();
        int[] column = new int[size2];
        int[] square = new int[size2];

        for (int i = 0; i < size2; i ++)
            column[i] = board[i][x];

        int k = 0;
        for (int i = (x/size3) * size3; (i/size3) * size3 == (x/size3) * size3; i++) {
            for (int j = (y/size3) * size3; (j/size3) * size3 == (y/size3) * size3; j++){
                //System.out.println(i + " " + j + " " + k + " " + (x/size) * size + " " + (y/size) * size);
                square[k] = board[j][i];
                k++;
            }
        }

        if (!(search(column, num) && search(row, num) && search(square, num)) || num > size2){
            System.out.println("Invalid input");
            return;
        }

        board[y][x] = num;
        print();
        System.out.println("Insertion succesful");
    }
/**search for value in integer array, return boolean
 * @param  int [] a
 * @param x
 * @return boolean
 */
    private boolean search (int[] a, int x){
        for(int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println("");
        for(int i : a)
            if(i == x)
                return false;
        return true;
    }

    public String getNum(int x, int y){
        if(board[y][x] != 0)
            return board[y][x] + "";
        else
            return "";
    }


/**output of entire sudoku board
 * @see console
 */
    private void print (){
        System.out.println();
        for (int i = 0; i < board.length; i++) { // prints out entire sudoku
            if (i != 0) {
                System.out.println();
            }
            System.out.print((i + 1) + "| "); //
            for (int j = 0; j < board.length; j++) {
                System.out.print("[" + board[i][j] + "] ");

            }

        }
        System.out.println();
    }
}
