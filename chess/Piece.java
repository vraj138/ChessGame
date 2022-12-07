package chess;

/**
 * The abstract class Piece defines the common properties of a chess piece
 * 
 * @author Deep Parekh and Vraj Patel
 */

public abstract class Piece {

    // Piece class properties
    private boolean white = false;
    private boolean firstMove = true;
    private boolean secondMove = true;
    private String pieceName;

    // constructor of the class
    /**
     * Piece class Constructor
     * 
     * @param white true or false whether the piece is white or black
     */
    public Piece(boolean white) {
        this.setWhite(white);
    }

    /**
     * It sets the white variable true or false
     * 
     * @param white true or false whether the piece is white or black
     */
    public void setWhite(boolean white) {
        this.white = white;
    }

    /**
     * Method retrun whether a piece white or black
     * 
     * @return boolean true or false whether the piece is white or black
     */
    public boolean isWhite() {
        return this.white == true;
    }

    /**
     * Method sets the piece name
     * 
     * @param name piece name
     */
    public void setPieceName(String name) {
        this.pieceName = name;
    }

    /**
     * method returns the piece name
     * 
     * @return String piece name
     */
    public String getPieceName() {
        return this.pieceName;
    }

    /**
     * Custom implementation of two string method
     * 
     * @return String custom message with piece name
     */
    public String toString() {
        return "I am a " + getPieceName();
    }

    /**
     * method returns whether the piece is moved once or not
     * 
     * @return boolean true or false whether the piece is moved once or not
     */
    public boolean isFirstMove() {
        return this.firstMove;
    }

    /**
     * method sets the value of first move variable true or false
     * 
     * @param firstMove true or false whether the piece is moved once or not
     */
    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    /**
     * method returns whether the piece is moved second time or not
     * 
     * @return boolean true or false whether the piece is moved osecond time or not
     */
    public boolean getSecondMove() {
        return this.secondMove;
    }

    /**
     * method sets the value of second move variable true or false
     * 
     * @param firstMove true or false whether the piece is moved second time or not
     */
    public void setSecondMove(boolean secondMove) {
        this.secondMove = secondMove;
    }

    /**
     * The abstract method to implement how a piece moves
     * 
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return true or false whether a piece can move accordindly the rule of chess
     */
    public abstract boolean canPlacePiece(int currRow, int currCol, int tarRow, int tarCol);
}
