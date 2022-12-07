package chess;

/**
 * The class King checks the King piece can move or not by inheriting the piece
 * class
 * 
 * @author Deep Parekh and Vraj Patel
 */

public class King extends Piece {
    /**
     * Constructor of the King Class
     * 
     * @param white true or false whether the piece is white or black
     */
    public King(boolean white) {
        super(white);
        if (white) {
            this.setPieceName("wK");
        } else {
            this.setPieceName("bK");
        }
    }

    /**
     * The method that checks the King can move from current tile to target tile
     * 
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return boolean true or false whether a King piece can move accordindly the
     *         rule of chess
     */
    @Override
    public boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol) {

        // a king can move one square in any direction
        // for castling only, it can move 2 squares...
        // ...but I have a separate method for that so no need to check it here
        // ...HOWEVER, a king can never move into a check. HOW DO WE CHECK THAT?
        int diffRow = Math.abs(tarRow - currRow);
        int diffCol = Math.abs(tarCol - currCol);

        if ((diffRow == 0 && diffCol == 1) || (diffRow == 1 && diffCol == 0) || (diffRow == 1 && diffCol == 1)) {
            return true;
        }

        return false;
    }
}