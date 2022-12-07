package chess;

/**
 * The class Pawn checks the Pawn piece can move or not by inheriting the piece
 * class
 * 
 * @author Deep Parekh and Vraj Patel
 */

public class Pawn extends Piece {
    /**
     * Constructor of the Pawn Class
     * 
     * @param white true or false whether the piece is white or black
     */
    public Pawn(boolean white) {
        super(white);
        if (white) {
            this.setPieceName("wp");
        } else {
            this.setPieceName("bp");
        }
    }

    /**
     * The method that checks the Pawn can move from current tile to target tile
     * 
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return boolean true or false whether a Pawn piece can move accordindly the
     *         rule of chess
     */
    @Override
    public boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol) {

        // the white pawn can only move straight upwards, not any other direction
        // and it can only move one tile at a time...
        // ...only exception being if the pawn hasn't moved before then...
        // ...it can jump 2 tiles on the board
        // similarly, a black pawn can only move straight downwards
        if ((tarCol - currCol == 0)) {
            // if the pawn is white, it can only move up
            if (this.isWhite()) {
                if ((tarRow - currRow == -1) || (tarRow - currRow == -2 && this.isFirstMove())) {
                    return true;
                }
            }

            // if the pawn is black, it can only move downards
            else {
                if ((tarRow - currRow == 1) || (tarRow - currRow == 2 && this.isFirstMove())) {
                    return true;
                }
            }
        }
        return false;
    }
}
