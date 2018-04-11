package sud;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * A dialog template for playing simple Sudoku games. newClicked(int),
 * numberClicked(int) and boardClicked(int,int).
 *
 * @author Yoonsik Cheon, Emmanuel Jacquez, Hector Dozal
 * @version 4.1
 * @since 3/1/2017
 * 
 */
@SuppressWarnings("serial")
public class SudokuDialog extends JFrame {
	private static int sz;
	private static int xX;
	private static int yY;
	private static int vV;

	/** Default dimension of the dialog. */
	private final static Dimension DEFAULT_SIZE = new Dimension(310, 430);

	private final static String IMAGE_DIR = "/image/";

	/** Sudoku board. */
	private Board board;
	/** Special panel to display a Sudoku board. */
	private BoardPanel boardPanel;

	/** Message bar to display various messages. */
	private JLabel msgBar = new JLabel("");

	/**
	 * Create a new dialog.
	 * 
	 * @throws Exception
	 */
	public SudokuDialog() throws Exception {
		this(DEFAULT_SIZE);
	}

	/**
	 * Create a new dialog of the given screen dimension.
	 * 
	 * @throws Exception
	 * @param Dimension
	 *            dim
	 */
	public SudokuDialog(Dimension dim) throws Exception {
		super("Sudoku");
		setSize(dim);
		board = new Board(9);
		
		boardPanel = new BoardPanel(board, this::boardClicked);
		configureUI();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Callback to be invoked when a square of the board is clicked.
	 * 
	 * @param x
	 *            0-based row index of the clicked square.
	 * @param y
	 *            0-based column index of the clicked square.
	 */
	private void boardClicked(int x, int y) {
		showMessage(String.format("Board clicked: x = %d, y = %d", x, y));
		xX = x;
		yY = y;
		board.insert(x, y, vV);
		if (board.isSolved()) {
			boardPanel.repaint();
			JOptionPane.showMessageDialog(null, "        GAME WINNER!");
			showMessage("Congratulations,Sudoku has been solved!!");

		}
		boardPanel.repaint();
		System.out.println("Grid is Clicked " + vV);
	}

	/**
	 * Callback to be invoked when a number button is clicked.
	 * 
	 * @param number
	 *            Clicked number (1-9), or 0 for "X".
	 */
	private void numberClicked(int number) {
		vV = number;
		showMessage("Number clicked: " + number);
	}

	/**
	 * Callback to be invoked when a new button is clicked. If the current game
	 * is over, start a new game of the given size; otherwise, prompt the user
	 * for a confirmation and then proceed accordingly.
	 * 
	 * @param size
	 *            Requested puzzle size, either 4 or 9.
	 */
	private void newClicked(int size) {

		int reply = JOptionPane.showConfirmDialog(null, "Start New " + size + "x" + size + " Game?", "SUDOKU",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Lets Play!");
		} else {
			JOptionPane.showMessageDialog(null, "Game Over, Goodbye");
			System.exit(0);
		}
		int sud[][] = new int[size][size];
		board.changeBoard(sud);
		repaint();
		showMessage("New clicked: board" + size);
	}

	/**
	 * Display the given string in the message bar.
	 * 
	 * @param msg
	 *            Message to be displayed.
	 */
	private void showMessage(String msg) {
		msgBar.setText(msg);
	}

	/**
	 * Configure the UI.
	 * 
	 * @param JPanel
	 *            displayed with image from library
	 * @see JPanel and Image
	 * 
	 */
	JPanel p1, p2,p3;
	private void configureUI() throws Exception {
		
		setIconImage(ImageIO.read(new File("sudoku.png")));
		
//
		 p1 = new JPanel();
	     p1.setLayout(new BorderLayout());
		JMenuBar menu=createMenuBar();
		menu.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
		p1.add(menu,BorderLayout.PAGE_START);
		
		JToolBar tools=makeToolBar();
		tools.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
		p1.add(tools,BorderLayout.CENTER);

		
		JPanel buttons = makeControlPanel();
		// boarder: top, left, bottom, right
		buttons.setBorder(BorderFactory.createEmptyBorder(10, 16, 0, 16));
		p1.add(buttons,BorderLayout.SOUTH);
	
		
//
		p2 = new JPanel();
        p2.setLayout(new BorderLayout());
		JPanel board = new JPanel();
		board.setBorder(BorderFactory.createEmptyBorder(10, 16, 0, 16));
		board.setLayout(new GridLayout(1, 1));
		board.add(boardPanel);
		p2.add(board,BorderLayout.CENTER);

		p3=new JPanel();
		p3.setLayout(new BorderLayout());
		msgBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 0));
		p3.add(msgBar,BorderLayout.SOUTH);
		
		 add(p1, BorderLayout.PAGE_START);
	     add(p2, BorderLayout.CENTER);
		 add(p3,BorderLayout.SOUTH);
		
	}

	 private JMenuBar createMenuBar() {

	        JMenuBar menubar = new JMenuBar();
	        ImageIcon exitIcon = new ImageIcon("icon/exit.png");
	        JMenu file = new JMenu("Game");
	        file.setMnemonic(KeyEvent.VK_F);

	        JMenuItem eMenuItem = new JMenuItem("Exit", exitIcon);
	        eMenuItem.setMnemonic(KeyEvent.VK_E);
	        eMenuItem.setToolTipText("Exit Game");
	        eMenuItem.addActionListener((ActionEvent event) -> {
	            System.exit(0);
	        });
	        file.add(eMenuItem);
	        
	        JMenuItem newMenuItem = new JMenuItem("New Game", exitIcon);
	        newMenuItem.setMnemonic(KeyEvent.VK_E);
	        newMenuItem.setToolTipText("New Game");
	        newMenuItem.addActionListener((ActionEvent event) -> {
	        	 Integer[] size = {4,9};
	                int num = (Integer)JOptionPane.showInputDialog(null, "Enter new Sudoku Puzzle size:", 
	                        "New Game", JOptionPane.QUESTION_MESSAGE, null, size, size[1]);
	                newClicked(num);
	                
	          
	        }); 
	        file.add(newMenuItem);
	        
	        menubar.add(file);
	        setJMenuBar(menubar);
	        return menubar;
	    }

	
	
	private JToolBar makeToolBar(){
		
		JToolBar toolbar = new JToolBar("Sudoku");
        String newGame="icons/game.png";
        String key="icons/key.png";
        String check="icons/check.png";
        String solve="icons/solve.png";
        String information="icons/information.png";
        
        JButton gameBtn = new JButton(resizeIcon(newGame));
        gameBtn.setToolTipText("New Game");
        gameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer[] options = {4,9};
                int n = (Integer)JOptionPane.showInputDialog(null, "Enter new Sudoku Puzzle size:", 
                        "New Gamw", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                newClicked(n);
                
            }
        });
 
        JButton keyBtn = new JButton(resizeIcon(key));
        keyBtn.setToolTipText("View Key");
        keyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "View Key");
            }
        });
 
        JButton checkBtn = new JButton(resizeIcon(check)); 
        checkBtn.setToolTipText("Check");
        checkBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Check Solvability");
            }
        });
 
        JButton solveBtn = new JButton(resizeIcon(solve));
        solveBtn.setToolTipText("Solve");
        solveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Solve Puzzle");
            }
        });
   
 
        JButton infoBtn = new JButton(resizeIcon(information));
        infoBtn.setToolTipText("About");
        infoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "About");
            }
        });
 
        toolbar.add(gameBtn);
        toolbar.add(keyBtn);
        toolbar.add(checkBtn);
        toolbar.add(solveBtn);
        toolbar.add(infoBtn);

		return toolbar;
	}
	
	private ImageIcon resizeIcon(String img){
		ImageIcon imageIcon = new ImageIcon(img); // load the image to a imageIcon  
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
        imageIcon = new ImageIcon(newimg);	
		
		
		return imageIcon;
	}
	
	
	
	/** Create a control panel consisting of new and number buttons. */
	private JPanel makeControlPanel() {
		/*JPanel newButtons = new JPanel(new FlowLayout());
		JButton new4Button = new JButton("New (4x4)");
		for (JButton button : new JButton[] { new4Button, new JButton("New (9x9)") }) {
			button.setFocusPainted(false);
			button.addActionListener(e -> {
				newClicked(e.getSource() == new4Button ? 4 : 9);
			});
			newButtons.add(button);
		}
		newButtons.setAlignmentX(LEFT_ALIGNMENT);*/

		// buttons labeled 1, 2, ..., 9, and X.
		
		
		JPanel numberButtons = new JPanel(new FlowLayout());
		int maxNumber = board.size + 1;
		System.out.println("board size " + board.size);
		for (int i = 1; i <= maxNumber; i++) {
			int number = i % maxNumber;
			JButton button = new JButton(number == 0 ? "X" : String.valueOf(number));
			button.setFocusPainted(false);
			button.setMargin(new Insets(0, 2, 0, 2));
			button.addActionListener(e -> numberClicked(number));
			numberButtons.add(button);
		}
		numberButtons.setAlignmentX(CENTER_ALIGNMENT);

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
	//	content.add(newButtons);
		content.add(numberButtons);
		return content;
	}

	/**
	 * Create an image icon from the given image file.
	 * 
	 * @param image
	 *            string file
	 */
	private ImageIcon createImageIcon(String filename) {
		URL imageUrl = getClass().getResource(IMAGE_DIR + filename);
		if (imageUrl != null) {
			return new ImageIcon(imageUrl);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		new SudokuDialog();
	}
}