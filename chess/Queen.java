package chess;

/**
 * The class Queen checks the queen piece can move or not by inheriting the
 * piece class
 * 
 * @author Deep Parekh and Vraj Patel
 */

public class Queen extends Piece {
    /**
     * Constructor of the Queen Class
     * 
     * @param white true or false whether the piece is white or black
     */
    public Queen(boolean white) {
        super(white);
        if (white) {
            this.setPieceName("wQ");
        } else {
            this.setPieceName("bQ");
        }
    }

    /**
     * The method that checks the Queen can move from current tile to target tile
     * 
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return boolean true or false whether a Queen piece can move accordindly the
     *         rule of chess
     */
    @Override
    public boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol) {
        // a queen's movement is equal to rook + bishop movement...
        // ...it can move forward, backward, and sideways just like a rook...
        // ...and it can also move in diagonal path just like a bishop
        int diffInRow = (int) Math.abs(tarRow - currRow);
        int diffInCol = (int) Math.abs(tarCol - currCol);

        if (currRow == tarRow || currCol == tarCol) {
            return true;
        } else if (diffInRow == diffInCol) {
            return true;
        }

        return false;
    }
}
