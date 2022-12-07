package chess;

/**
 * The class Bishop checks the Bishop piece can move or not by inheriting the piece class
 * @author Deep Parekh and Vraj Patel
 */

public class Bishop extends Piece {
    /**
     * Constructor of the Bishop Class
     * @param white true or false whether the piece is white or black
     */
    public Bishop(boolean white) {
        super(white);
        if (white) {
            this.setPieceName("wB");
        } else {
            this.setPieceName("bB");
        }
    }

    
    /** 
     * The method that checks the Bishop can move from current tile to target tile
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow the row that the current piece wants to move on
     * @param tarCol the column that the current piece wants to move on
     * @return boolean true or false whether a Bishop piece can move accordindly the rule of chess
     */
    @Override
    public boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol) {

        // a bishop moves in diagonal path - forward and backward...
        // ...and right and left. Given current and target row and columns...
        // ...we have to compare the differences between current row and target
        // ...row and do the same with current and target column...
        // ...if both differences are equal, then the bishop can be placed there...
        // ...otherwise it can't be placed there.

        // to understand better, consider this example: c1 to g5
        // convert this to row and column, we get: 2,0 to 6,4
        // row difference: 6-2 = 4
        // column difference: 4-0 = 4
        // if these 2 values are equal, only then you can place the bishop there.
        int diffInRow = (int) Math.abs(tarRow - currRow);
        int diffInCol = (int) Math.abs(tarCol - currCol);

        if (diffInRow == diffInCol) {
            return true;
        }

        return false;
    }
}
