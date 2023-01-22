import java.lang.Math;
public class Minesweeper {
    private static Minesweeper instance;

    public static Minesweeper getInstance() {
        if (instance == null) {
            instance = new Minesweeper( 3, 3, 2);
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
    
    public Minesweeper(int X, int Y, int bombs){
        
        this.X = (X > 3)? X : 3;
        this.Y = (Y > 3)? Y : 3;
        bombsInitial = (bombs < this.X * this.Y)? bombs : 3;
        this.board = new char[this.X][this.Y];
    }
    /*
    //randomly fills the board with bombs. Taking into account the first clicked place must be empty.
    public void setBombs(int firstMoveX, int firstMoveY){
        int spacesToFill = this.X * this.Y - 9;
        int parser = 0;
        int bombsPlaced = 0;
        //Needs to fill more spaces if first click is on the boundary
        if(firstMoveX % (this.X-1) == 0){
            spacesToFill += 3;
            if(firstMoveY % (this.Y-1) == 0){
                spacesToFill += 2;
            }
        }else{ 
            if(firstMoveY % (this.Y-1) == 0){
                spacesToFill += 3;
            }
        }

        //should work
        while (spacesToFill > 0 && bombsPlaced < bombsLeft){ 
            if(Math.abs(this.X - firstMoveX) > 1 && Math.abs(this.Y - firstMoveY) > 1){
                /* I've checked the math, and this should give an even distribution
                 * might tend to make the last few spaces either fully empty or fully bombed 
                 * TODO check if bomb generation works properly
                 \* /
                if(Math.random() < 1.0 / spacesToFill || spacesToFill == bombsLeft-bombsPlaced){
                    this.board[parser / this.X][parser % this.Y] = bomb;
                    bombsPlaced++;
                }
                spacesToFill--;
            }
            parser++;
        }

    }
    */
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

        int count = (board[inputX][inputY] == bomb)? -1 : 0;
        for(int i = inputX - 1; i <= inputX + 1; i++){
            for(int j = inputY - 1; j <= inputY + 1; j++){
                try{
                    if(board[i][j] == bomb){
                        count++;
                    }
                }catch(Exception e){
                    System.out.println("border encountered");
                }
            }

        }
        return count;
        
    }

}
