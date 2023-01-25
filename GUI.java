import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Date;


public class GUI{
    JFrame frame;
    JPanel info;
    JPanel game;
    int bombCounter;
    JLabel time, counter;
    JButton[][] board;
	boolean untouched = true;
    final int guiX, guiY, guiBombs;
    int windowSizeX, windowSizeY;
    

    public GUI(int windowSizeX, int windowSizeY, int X, int Y, int bombs){
        guiX = X;
        guiY = Y;
        guiBombs = bombs;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;

        Minesweeper ms = Minesweeper.getInstance(guiX, guiY, guiBombs);
        frame = new JFrame("Minesweeper");

        final int sizeX = ms.getX();
        final int sizeY = ms.getY();

        info = new JPanel();
        game = new JPanel();
        bombCounter = ms.getBombsLeft();
        counter = new JLabel(Integer.toString(bombCounter));
        time = new JLabel("time: 0");
        board = new JButton[sizeX][sizeY];

        info.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        info.setLayout(new GridLayout(1, 2,0 ,0));
        info.add(counter);
        info.add(time);

        game.setBorder(BorderFactory.createLineBorder(Color.black));
        game.setLayout(new GridLayout(sizeX, sizeY, 5, 5));


        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
				final int x = i;
				final int y = j;

                board[i][j] = new JButton();
                board[i][j].setPreferredSize(new Dimension(windowSizeX / X - 5, windowSizeY / Y - 5));
                board[i][j].addActionListener(e -> {
                    onButtonClick(x, y);
                });
                game.add(board[i][j]);
            }
        }

        frame.add(info, BorderLayout.NORTH);
        frame.add(game, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(this.windowSizeX, this.windowSizeY);
        frame.pack();
        frame.setVisible(true);
    }


    private void onButtonClick(int row, int column) {
		Minesweeper ms = Minesweeper.getInstance(guiX, guiY, guiBombs);
		
		if(untouched){
			ms.setBombsNaive(row, column);
			untouched = false;
		}

		char cell = ms.getCellValue(row, column);

		if(cell == ms.bomb){
			board[row][column].setText("" + cell);
            board[row][column].setBackground(Color.red);
			//System.exit(0);
		}else{
			board[row][column].setText("" + cell);

		}
        if (cell == '0') {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i != 0 || j != 0) {
                        try {
                            board[row + i][column + j].setText("" + ms.getCellValue(row + i, column + j));
                            
                        }catch(ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }

	}
}
