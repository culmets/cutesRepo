
public class Board {

    private String[][] board;

    public Board() {
        this.board = new String[8][8];
        initBoard();
    }

    private void initBoard() {
        board[0] = new String[] {"r", "n", "b", "q", "k", "b", "n", "r"};
        board[1] = new String[] {"p", "p", "p", "p", "p", "p", "p", "p"};
        board[6] = new String[] {"P", "P", "P", "P", "P", "P", "P", "P"};
        board[7] = new String[] {"R", "N", "B", "Q", "K", "B", "N", "R"};

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = ".";
            }
        }
    }

    public void printBoard(){
        
    }

}
