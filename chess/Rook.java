package chess;

/**
 * The class Rook checks the rook piece can move or not by inheriting the piece class
 * @author Deep Parekh and Vraj Patel
 */

public class Rook extends Piece {
    /**
     * Constructor of the Rook Class
     * @param white true or false whether the piece is white or black
     */
    public Rook(boolean white) {
        super(white);
        if (white) {
            this.setPieceName("wR");
        } else {
            this.setPieceName("bR");
        }
    }

    
    /** 
     * The method that checks the rook can move from current tile to target tile
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow the row that the current piece wants to move on
     * @param tarCol the column that the current piece wants to move on
     * @return boolean true or false whether a rook piece can move accordindly the rule of chess
     */
    @Override
    public boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol) {
        // a rook can move forward, backward, and sideways but not diagonally
        // so basically if current and target row or current row and target column are
        // same...
        // ...then a rook can move there
        if (currRow == tarRow) {
            return true;
        } else if (currCol == tarCol) {
            return true;
        } else {
            return false;
        }
    }
}
