package sud;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import sud.Board;

/**
 * A special panel class to display a Sudoku board modeled by the
 * {@link sudoku.model.Board} class. You need to write code for
 * the paint() method.
 *
 * @see sudoku.model.Board
 * @author Yoonsik Cheon, Emmanuel Jacquez, Hector Dozal
 * @version 1.0
 * @since 3/04/2018
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

    public interface ClickListener {

        /**
         * Callback to notify clicking of a square.
         *
         * @param x 0-based column index of the clicked square
         * @param y 0-based row index of the clicked square
         */
        void clicked(int x, int y);
    }

    /**
     * Background color of the board.
     */
    private static final Color boardColor = new Color(20, 50, 100);

    /**
     * Board to be displayed.
     */
    private Board board;

    /**
     * Width and height of a square in pixels.
     */
    private int squareSize;

    /**
     * Create a new board panel to display the given board.
     * @param Board board
     * @param Click listener
     */
    public BoardPanel(Board board, ClickListener listener) {
        this.board = board;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int xy = locateSquaree(e.getX(), e.getY());
                if (xy >= 0) {
                    listener.clicked(xy / 100, xy % 100);
                }
            }
        });
    }

    /**
     * Set the board to be displayed.
     * @param Board board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Given a screen coordinate, return the indexes of the corresponding square
     * or -1 if there is no square.
     * The indexes are encoded and returned as x*100 + y,
     * where x and y are 0-based column/row indexes.
     * @param int x
     * @param int y
     * @return int xx
     */
    private int locateSquaree(int x, int y) {
        System.out.println("board size print " + board.size);
        if (x < 0 || x > board.size * squareSize
                || y < 0 || y > board.size * squareSize) {
            return -1;
        }
        int xx = x / squareSize;
        int yy = y / squareSize;
        return xx * 100 + yy;
    }

    /**
     * Draw the associated board.
     * @param Graphics g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // determine the square size
        Dimension dim = getSize();
        squareSize = Math.min(dim.width, dim.height) / board.size;

        // draw background
        final Color oldColor = g.getColor();
        g.setColor(boardColor);
        g.fillRect(0, 0, squareSize * board.size, squareSize * board.size);

        g.setColor(Color.lightGray);
        Graphics2D g2 = (Graphics2D) g;

        //vertical and horizontal grid lines
        for (int k = 0; k < squareSize * board.size + 1; k += squareSize) {
            if (k % (int) Math.sqrt(board.size) != 0){
                g2.drawLine(k, 0, k, squareSize * board.size);
            	g2.drawLine(0, k, squareSize * board.size, k);
            }
            else {
                g2.setColor(Color.black);
                g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(k, 0, k, squareSize * board.size);
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.white);
                
                g.setColor(Color.black);
                g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(0, k, squareSize * board.size, k);
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.white);
                
            }
        }
        
        g.setColor(boardColor.GRAY);

        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        double adj=1;
        if(board.size==9){
        	adj=1.15;				//center integers on grid depending on size input
        }   
        
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                g.drawString(board.getNum(i, j), (int) ((squareSize*(i+.95))-(squareSize/2)), (int) ((squareSize*(j+adj))-(squareSize/2)));
            }
        }
    }
}

