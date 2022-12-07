package chess;

/**
 * The class Knight checks the knight piece can move or not by inheriting the
 * piece class
 * 
 * @author Deep Parekh and Vraj Patel
 */

public class Knight extends Piece {
    /**
     * Constructor of the Knight Class
     * 
     * @param white true or false whether the piece is white or black
     */
    public Knight(boolean white) {
        super(white);
        if (white) {
            this.setPieceName("wN");
        } else {
            this.setPieceName("bN");
        }
    }

    /**
     * The method that checks the Knight can move from current tile to target tile
     * 
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return boolean true or false whether a knight piece can move accordindly the
     *         rule of chess
     */
    @Override
    public boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol) {
        // a knight moves in L-shape around the board...
        // ...so basically when there is a diff in current and target rows...
        // ...of 2 and diff in columns is 1 OR diff in rows is 1 and...
        // ...diff in columns is 1, then a knight can move there
        int diffInRow = (int) Math.abs(tarRow - currRow);
        int diffInCol = (int) Math.abs(tarCol - currCol);
 
        if((diffInRow == 2 && diffInCol == 1) || (diffInRow == 1 && diffInCol == 2)){
            return true;
        }

        return false;
    }
}