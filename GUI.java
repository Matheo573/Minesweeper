import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

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
    

    public GUI(int X, int Y, int bombs){
        guiX = X;
        guiY = Y;
        guiBombs = bombs;
        Minesweeper ms = Minesweeper.getInstance(guiX, guiY, guiBombs);
        frame = new JFrame("Minesweeper");

        final int sizeX = ms.getX();
        final int sizeY = ms.getY();

        info = new JPanel();
        game = new JPanel();
        bombCounter = ms.getBombsLeft();
        counter = new JLabel(Integer.toString(bombCounter));
        time = new JLabel("time");
        board = new JButton[sizeX][sizeY];

        info.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
        info.setLayout(new GridLayout(1, 2,0 ,0));
        info.add(counter);
        info.add(time);

        game.setBorder(BorderFactory.createLineBorder(Color.black));
        game.setLayout(new GridLayout(sizeX, sizeY, 5, 5));


        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                String text = (
                    Integer.toString(i)
                    + " " + 
                    Integer.toString(j)
                    );
				final int x = i;
				final int y = j;

                board[i][j] = new JButton(text);
                board[i][j].addActionListener(e -> {
                    onButtonClick(x, y);
                });
                game.add(board[i][j]);
            }
        }

        frame.add(info, BorderLayout.NORTH);
        frame.add(game, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.pack();
        frame.setVisible(true);

    }/* 
    private void clickButton(String text){
        List<String> coords = Arrays.asList(text.split(" "));
        int x = Integer.parseInt(coords.get(0));
        int y =  Integer.parseInt(coords.get(1));
    }*/
    private void onButtonClick(int row, int column) {
		Minesweeper ms = Minesweeper.getInstance(guiX, guiY, guiBombs);
		
		if(untouched){
			ms.setBombsNaive(row, column);
			untouched = false;
		}

		char cell = ms.getCellValue(row, column);

		if(cell == ms.bomb){
			board[row][column].setText("" + cell);
			//System.exit(0);
		}else{
			board[row][column].setText("" + cell);

		}

	}
}
