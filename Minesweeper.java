import java.lang.Math;
import java.lang.ArrayIndexOutOfBoundsException;
public class Minesweeper {
    private static Minesweeper instance;

    public static Minesweeper getInstance(int X, int Y, int bombs) {
        if (instance == null) {
            instance = new Minesweeper( X, Y, bombs);
        }
        return instance;
    }

    final private int X, Y;
    private int bombsInitial;
    private int bombsLeft;
    public char[][] board;

    final public char bomb = '*';

    public int getBombsLeft() {
        return bombsLeft;
    }
    public void setBombsLeft(int bombsLeft) {
        this.bombsLeft = bombsLeft;
    }

    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }

    public char[][] getBoard() {
        return board;
    }
    /*
    private void setCell(char input, int x, int y) {
        this.board[x][y] = input;
    }
    */
    public char getCellValue(int x, int y) {
        return calculateDisplay(x, y);
    }
    
    private Minesweeper(int X, int Y, int bombs){
        
        this.X = X;
        this.Y = Y;
        this.bombsInitial = (bombs < this.X * this.Y)? bombs : 3;
        this.board = new char[this.X][this.Y];
    }


    public void setBombsNaive(int firstMoveX, int firstMoveY){
        int bombsLeftToPlace = bombsInitial;
        
        while(bombsLeftToPlace > 0){
            int x = (int)(Math.random() * X);
            int y = (int)(Math.random() * Y);

            //
            if (board[x][y] == bomb) {continue;}
            //no unsolvable clusters of bombs
            if (adjacentBombs(x, y) > 7) {continue;}

            //first click must be empty
            if (x - firstMoveX <= 1 &&
                firstMoveX - x <= 1 &&
                y - firstMoveY <= 1 &&
                firstMoveY - y <= 1) {
                    continue;
            }

            board[x][y] = bomb;
            bombsLeftToPlace--;
        }
        
    }
    

    private char calculateDisplay(int inputX, int inputY){
        if(board[inputX][inputY] == bomb){    
            return bomb;
        }else{
            return (char)('0' + adjacentBombs(inputX, inputY));
        }
    }

    private int adjacentBombs(int inputX, int inputY) {

        int count = 0;
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                try{
                    if(board[inputX + i][inputY + j] == bomb){
                        System.out.print(i + " " + j + " " + count + "\n");
                        count++;
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }

        }
        return count;
        
    }

}
