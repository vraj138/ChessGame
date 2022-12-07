package chess;

/**
 * The class Board prints the whole chess board
 * @author Deep Parekh and Vraj Patel
 */
public class Board {
    Piece[][] board;

    /**
     * Board class constructor
     */
    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    /**
     * Method to initializes the chess board
     */
    // initializes the chess board
    public void initializeBoard() {

        // set the empty board to null
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        // initialize the white pieces
        // add 2 white rooks
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);

        // add 2 white knights
        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);

        // add 2 white bishops
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);

        // add white queen
        board[7][3] = new Queen(true);

        // add white king
        board[7][4] = new King(true);

        // add white pawns in 7th row through a for loop
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(true);
        }

        // now initialize all black pieces
        // add 2 black rooks
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);

        // add 2 black knights
        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);

        // add 2 black bishops
        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);

        // add black queen
        board[0][3] = new Queen(false);

        // add black king
        board[0][4] = new King(false);

        // add all black pawns in 2nd row through a for loop
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false);
        }

    }

    
    /** 
     * Piece present method
     * @param row the row that the current piece is on
     * @param col the column that the current piece is on
     * @return boolean true or false whether the piece is present at this row and and column in the board
     */
    // check if the piece is present at this row and column in the board
    public boolean isPiecePresent(int row, int col) {
        return board[row][col] != null;
    }

    
    /** 
     * Method to check is path blocked or not
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow the row that the current piece wants to move on
     * @param tarCol the column that the current piece wants to move on
     * @return boolean true or false whether the path is blocked or not
     */
    // check if the path between current and target tiles is blocked or not
    // in other words, is there a piece present on any of the boxes between the...
    // ...current and target tiles
    public boolean isPathBlocked(int currRow, int currCol, int tarRow, int tarCol) {
        // get the piece that is present at current tile
        Piece cp = board[currRow][currCol];

        boolean pathBlocked = false;

        // we do not need to check anything for the knight - it can jump over all..
        // ... the pieces in its path
        if (cp.getPieceName().equals("wN") || cp.getPieceName().equals("bN")) {
            return false;
        }

        // get the difference in current and target row and column coordinates
        int diffRow = tarRow - currRow;
        int diffCol = tarCol - currCol;

        // temp variables to later be used in the for loops
        int tempRow = currRow;
        int tempCol = currCol;

        // variables to detemine which direction does the piece want to move in
        int directionRow = 0;
        int directionCol = 0;

        // if the difference in rows is positive, that means...
        // ...player wants to move downwards row-wise on the board
        if (diffRow > 0) {
            directionRow = 1;
        }
        // if the difference in rows is negative, that means...
        // ...player wants to move the piece upwards row-wise
        else if (diffRow < 0) {
            directionRow = -1;
        }

        // same logic as above with columns
        // if the difference in columns is positive, that means...
        // ...the piece wants to move to the right on the board
        if (diffCol > 0) {
            directionCol = 1;
        }
        // otherwise the piece wants to move to its left
        else if (diffCol < 0) {
            directionCol = -1;
        }

        // if the difference in rows is 0...
        // ...then the piece wants to remain in the same row and only change the column
        if (diffRow == 0) {
            tempCol += directionCol;

            // iterate over all the tiles between the current and target tile
            for (int i = 0; i < Math.abs(diffCol) - 1; i++) {
                // if there is a piece present at this tile...
                // ...then set the pathBlocked variable to true
                if (board[tempRow][tempCol] != null) {
                    pathBlocked = true;
                    break;
                }

                tempCol += directionCol;
            }
        }

        // if the difference in columns is 0, then it means...
        // ...that the piece wants to stay in the same column and only change the row
        if (diffCol == 0) {
            tempRow += directionRow;

            // iterate over all the tiles between the current and target tile
            for (int i = 0; i < Math.abs(diffRow) - 1; i++) {
                // if there is a piece present at this tile...
                // ...then set the pathBlocked variable to true
                if (board[tempRow][tempCol] != null) {
                    pathBlocked = true;
                    break;
                }

                tempRow += directionRow;
            }
        }

        // if the piece wants to change both its row and its column then...
        if (diffRow != 0 && diffCol != 0) {
            tempRow += directionRow;
            tempCol += directionCol;

            // iterate over all the tiles between the current and target tile
            for (int i = 0; i < Math.abs(diffCol) - 1; i++) {

                // if there is a piece present at this tile...
                // ...then set the pathBlocked variable to true
                if (board[tempRow][tempCol] != null) {
                    pathBlocked = true;
                    break;
                }

                tempRow += directionRow;
                tempCol += directionCol;
            }

        }
        return pathBlocked;
    }

    
    /** 
     * Method to check is king under check or not
     * @param kingRow the row where the king piece is currently on
     * @param kingCol the column where the king piece is currently on
     * @param isWhiteTurn true or false whether it is white turn or not
     * @param checkForOpponentKing true or false 
     * @return boolean true or false whether the openent king is under check or not
     */
    // method to check if the opponent king is under check or not
    public boolean isKingUnderCheck(int kingRow, int kingCol, boolean isWhiteTurn, boolean checkForOpponentKing) {
        // pass in the opponent king's coordinates and which player's turn it is rn as
        // arguments

        // iterate over all squares on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                // if the square has a piece present on it...
                if (board[i][j] != null) {

                    // then check if the piece belongs to the player whose turn it is rn
                    if (checkForOpponentKing) {

                        if (board[i][j].isWhite() == isWhiteTurn) {
                            // if it does, then see if you can place this piece on this square on the
                            // opponent king's square...
                            if (board[i][j].canPlacePiece(i, j, kingRow, kingCol)) {

                                // if you can, then see if the path between the 2 pieces is clear...
                                if (!isPathBlocked(i, j, kingRow, kingCol)) {

                                    // if it is, that means the piece on current square is attacking opponent
                                    // ...king and opponent king is under check
                                    return true;
                                }
                            }
                        }
                    } else {
                        if (board[i][j].isWhite() != isWhiteTurn) {
                            // if it does, then see if you can place this piece on this square on the
                            // opponent king's square...
                            if (board[i][j].canPlacePiece(i, j, kingRow, kingCol)) {

                                // if you can, then see if the path between the 2 pieces is clear...
                                if (!isPathBlocked(i, j, kingRow, kingCol)) {

                                    // if it is, that means the piece on current square is attacking opponent
                                    // ...king and opponent king is under check
                                    return true;
                                }
                            }
                        }
                    }
                }

            }
        }

        return false;
    }
}
