package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * The chess class with the main method which initialize the chess game
 * 
 * @author Deep Parekh and Vraj Patel
 */

public class Chess {
    public static Board chessBoard;
    public static boolean isWhitePlayerTurn = true;
    public static boolean playerAskedForDraw = false;
    public static boolean whiteKingUnderCheck = false;
    public static boolean blackKingUnderCheck = false;
    public static boolean whiteKingUnderCheckmate = false;
    public static boolean blackKingUnderCheckmate = false;
    public static String thirdArgument;
    public static String currFR;
    public static String targetFR;

    // variables to keep track of white and black king's coordinates on the board...
    // ...which will be passed in to the method that checks whether the king...
    // ...is under check or not
    public static int whiteKingRow = 7;
    public static int whiteKingCol = 4;
    public static int blackKingRow = 0;
    public static int blackKingCol = 4;

    /**
     * The main method
     * 
     * @param args a string array of arguements
     */
    public static void main(String[] args) {

        // create a buffered reader instance to read input from user
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        String userInput = null;

        // initialize the game
        initializeChessGame();

        // print the chess board on the terminal
        printChessBoard();

        // the game loop starts here and exits when a player wins, resigns or a draw is
        // offered
        while (true) {
            // reading the user input
            // adding try catch cuz the bufferedReader class throws an IOException that we
            // need to catch otherwise it won't compile
            try {

                // print which player's turn is it
                if (isWhitePlayerTurn) {
                    if (whiteKingUnderCheck) {
                        System.out.println("Check");
                    }
                    System.out.println("White's move: ");
                } else {
                    if (blackKingUnderCheck) {
                        System.out.println("Check");
                    }
                    System.out.println("Black's move: ");
                }

                userInput = b.readLine();

                // parse the user input to extract the fileranks and other keywords such as
                // resign and draw
                parseUserInput(userInput);

            } catch (IOException ex) {
                System.out.println("Illegal move, try again");
            }
        }

    }

    /**
     * Method to create a chess board instance
     */
    public static void initializeChessGame() {
        chessBoard = new Board();
    }

    /**
     * Method that prints the chess board to the terminal in the requested format
     */
    public static void printChessBoard() {
        // create a string 2D array to represent the 8x8 chess board's tiles
        String[][] finalBoard = new String[8][8];

        // variable to check if the box is white or black
        boolean whiteBox = true;

        // iterate over the finalBoard array and place white and black boxes
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // for white box, just store a string with blank space
                if (whiteBox) {
                    finalBoard[i][j] = "  ";

                    // make whiteBox false for the next box in same row
                    whiteBox = false;
                }
                // for black box, store 2 hashtags
                else {
                    finalBoard[i][j] = "##";

                    // make whiteBox true for the next box in same row
                    whiteBox = true;
                }
            }

            // once a row is complete, alter the value of whiteBox variable for next row
            whiteBox = !whiteBox;
        }

        // iterate over the boxes 2D array and place the pieces in finalBoard array
        // I can just say i<8 and j<8 because the boxes will be a 8x8 array
        for (int i = 0; i < chessBoard.board.length; i++) {
            for (int j = 0; j < chessBoard.board[i].length; j++) {
                // System.out.println(chessBoard.boxes[i][j]);
                if (chessBoard.board[i][j] != null) {
                    // if there is a piece on this box on the board
                    // then, edit this location in finalBoard array and add the piecename there
                    finalBoard[i][j] = chessBoard.board[i][j].getPieceName();
                }

                // if the box is null then we don't make any change in the finalBoard array
            }
        }

        // now we iterate over the finalBoard array and print its contents
        System.out.println();

        for (int i = 0; i < finalBoard.length; i++) {
            for (int j = 0; j < finalBoard[i].length; j++) {
                System.out.print(finalBoard[i][j] + " ");
            }
            // we need to print the row number at the end of each row
            System.out.print(" " + (8 - i));

            // go to new line after a row is completed
            System.out.println();
        }

        // we also need to print the column names as the last row
        System.out.println(" a  b  c  d  e  f  g  h");
        System.out.println();
    }

    /**
     * @param input the chess move entered by the user in terminal
     */
    public static void parseUserInput(String input) {
        // split the string using the blank space delimeter and StringTokenizer class
        StringTokenizer st = new StringTokenizer(input);
        int tokensCount = 0;

        // create a string array of length 10 to store all the tokens(words) from the
        // user inputted string
        // I'm choosing length 10 since there will never be more than 3 tokens in an
        // input string
        String[] tokens = new String[10];

        // convert the input string characters to lowercase and trim any leading or
        // trailing whitespace
        input = input.toLowerCase().trim();

        // fill the tokens array
        while (st.hasMoreTokens()) {
            tokens[tokensCount] = st.nextToken();
            tokensCount++;
        }

        // after the while loop has ended, the tokensCount will represent the number of
        // words in the input string
        // the max length of tokens array will be 3
        if (tokensCount > 0 && tokensCount < 4) {

            // check if draw is offered or pawn promotion is done
            if (tokensCount == 3) {
                currFR = tokens[0];
                targetFR = tokens[1];

                // if a player has already asked for draw, then the other player can not move
                // any piece, he must say "draw"
                if (playerAskedForDraw) {
                    // System.out.println("The other player has offered a draw. You must accept
                    // it!");
                    System.out.println("Illegal move, try again");
                } else {

                    // I don't actually need to do equalsIgnoreCase since I alread converted input
                    // string to lower case
                    if (tokens[2].equalsIgnoreCase("draw?")) {

                        // whichever player offers a draw, the other player must accept it and submit
                        // "draw" as their next move, nothing else is acceptable
                        playerAskedForDraw = true;

                        // go to the next player
                        isWhitePlayerTurn = !isWhitePlayerTurn;

                        return;
                    }

                    // if the third argument is a piece to make from a pawn (pawn promotion)
                    // a pawn can be promoted to a queen, a rook, a knight, or a bishop
                    else if (tokens[2].equalsIgnoreCase("n") || tokens[2].equalsIgnoreCase("q")
                            || tokens[2].equalsIgnoreCase("r") || tokens[2].equalsIgnoreCase("b")) {
                        thirdArgument = tokens[2];
                        runTheMove();
                    } else {
                        System.out.println("Illegal move, try again");
                    }
                }
            }
            // check if a player has resigned
            else if (tokensCount == 1) {
                if (tokens[0].equalsIgnoreCase("resign")) {

                    // if white resigns, black wins
                    if (isWhitePlayerTurn) {
                        System.out.println("Black wins");
                        System.exit(1);
                    }
                    // if black resigns, white wins
                    else {
                        System.out.println("White wins");
                        System.exit(1);
                    }
                } else if (tokens[0].equalsIgnoreCase("draw")) {
                    if (playerAskedForDraw) {
                        System.exit(1);
                    } else {
                        // System.out.println("You must first ask other player for draw");
                        System.out.println("Illegal move, try again");
                    }
                } else {
                    System.out.println("Illegal move, try again");
                }
            }
            // it's a move
            else if (tokensCount == 2) {
                currFR = tokens[0];
                targetFR = tokens[1];

                // if a player has already asked for draw, then the other player can not move
                // any piece, he must say "draw"
                if (playerAskedForDraw) {
                    // System.out.println("The other player has offered a draw. You must accept
                    // it!");
                    System.out.println("Illegal move, try again");
                } else {
                    runTheMove();
                }

            } else {
                System.out.println("Illegal move, try again");
            }
        } else {
            // if there are less than 1 or more than 3 tokens...
            // ...that is considered invalid input
            System.out.println("Illegal move, try again");
        }
    }

    /**
     * The method executed after each user input is parsed
     */
    public static void runTheMove() {
        // the user must input fileranks as first and second argument...
        // ...anything else is considered invalid input
        // for example, "deep vraj n" can't be accepted
        if (currFR.length() != 2 || targetFR.length() != 2) {
            System.out.println("Illegal move, try again");
            return;
        }

        int currRow = convertRankToRow(currFR);
        int currCol = convertFileToColumn(currFR);

        int targetRow = convertRankToRow(targetFR);
        int targetCol = convertFileToColumn(targetFR);

        // first check if the current box and target box are same or different
        if (currRow == targetRow && currCol == targetCol) {
            // a piece can not move from e2 to e2 - the target box has to be a different one
            // System.out.println("Your current and target boxes can not be the same!");
            System.out.println("Illegal move, try again");

            // exit the method here - do not move forward in execution
            return;
        }

        // check if the input is valid - row and column numbers must be within the range
        // of 0 to 7
        if (!isValidInput(currRow, currCol, targetRow, targetCol)) {
            // System.out.println("Your file and/or rank have to be between 0 and 7
            // inclusive!");
            System.out.println("Illegal move, try again");

            // exit the method here - do not move forward in execution
            return;
        }

        // check if there is a piece present on current box
        if (!chessBoard.isPiecePresent(currRow, currCol)) {
            // System.out.println("There is no piece on this tile!");
            System.out.println("Illegal move, try again");

            // exit the method here - do not move forward in execution
            return;
        }

        // the color of the piece and the player should match - in other words...
        // ...if it is white's turn and current tile has a black piece, or similarly...
        // ...if it is black's turn and current tile has a white piece, then...
        // ...can't execute the method ahead
        if ((isWhitePlayerTurn && !chessBoard.board[currRow][currCol].isWhite())
                || (!isWhitePlayerTurn && chessBoard.board[currRow][currCol].isWhite())) {
            // System.out.println("You can not move your opponent's piece!");
            System.out.println("Illegal move, try again");

            // exit the method here - do not move forward in execution
            return;
        }

        // if there is a piece on target box and if it is same color as the piece on...
        // ...current box, then you can't move the piece there
        if ((chessBoard.isPiecePresent(targetRow, targetCol))
                && ((chessBoard.board[currRow][currCol].isWhite()
                        && chessBoard.board[targetRow][targetCol].isWhite())
                        || (!chessBoard.board[currRow][currCol].isWhite()
                                && !chessBoard.board[targetRow][targetCol].isWhite()))) {

            // System.out.println("There exists a piece on the target tile that belongs to
            // you so you can't go there!");
            System.out.println("Illegal move, try again");

            // exit the method here - do not move forward in execution
            return;

        }
        // if it passes all of the 5 checks above, then it means the following...
        // 1: the input fileranks are valid
        // 2: the current and target boxes are also valid
        // 3: the target tile is empty or...
        // 4: the target tile has opponent player's piece
        // for either point 3 or point 4, check if the piece can be placed there and...
        // whether the path between current and target tiles is blocked or not...
        // ...if both methods return okay, then place the piece at target tile

        // Notice that we don't need to worry about whether target tile has an...
        // ...opponent's piece or not. Because even if it does, we simply...
        // ...update the box to have the current piece there.

        // System.out.println("The user asked for piece at row " + currRow + " and
        // column " + currCol + " to move to row "
        // + targetRow + " and column " + targetCol);

        // but before moving forward, we need to check...
        // ...if this move is a castling move or not
        if (testAndImplementCastling(currRow, currCol, targetRow, targetCol)) {
            // if this returns true, then it means the player wanted to castle his...
            // ...king and we did that through this method so there's no need...
            // ...to move forward in this runMove method.
            // Just print the board, go to next player and return back from this method

            // if the piece is a king, then update its coordinates in variables
            if ((isWhitePlayerTurn && chessBoard.board[currRow][currCol].getPieceName().equals("wK"))) {
                whiteKingRow = targetRow;
                whiteKingCol = targetCol;
            }

            if (!isWhitePlayerTurn && chessBoard.board[currRow][currCol].getPieceName().equals("bK")) {
                blackKingRow = targetRow;
                blackKingCol = targetCol;
            }

            isWhitePlayerTurn = !isWhitePlayerTurn;

            printChessBoard();
            return;
        }

        // need to add a special block for pawns
        // a pawn can move in diagonal path ONLY IF it is trying to capture...
        // ...an opponent's piece and the target tile actually has...
        // ...an opponent's piece. Well, technically, for en passant...
        // ...you don't need to have an opponent piece there...
        // ...so add a check for that too.
        if ((chessBoard.board[currRow][currCol].getPieceName().equals("wp") && (Math.abs(targetCol - currCol) == 1)
                && (targetRow - currRow == -1))
                || (chessBoard.board[currRow][currCol].getPieceName().equals("bp")
                        && (Math.abs(targetCol - currCol) == 1) && (targetRow - currRow == 1))) {
            // now check if there is a piece present on target tile
            if (chessBoard.isPiecePresent(targetRow, targetCol)) {

                // and if it is an opponent piece...
                if (chessBoard.board[currRow][currCol].isWhite() != chessBoard.board[targetRow][targetCol]
                        .isWhite()) {
                    // then the pawn can go there and capture the piece

                    // how do I check if moving this piece will put my king under check or not??????

                    if (targetRow == 0 && chessBoard.board[currRow][currCol].getPieceName().equals("wp")) {
                        // System.out.println("The user wants to promote pawn to a " + thirdArgument);
                        pawnPromotion(currRow, currCol, targetRow, targetCol);
                        // System.out.println(chessBoard.board[targetRow][targetCol].getPieceName());

                        chessBoard.board[currRow][currCol] = null;

                        // alter the value of isWhitePlayerTurn after processing each move
                        isWhitePlayerTurn = !isWhitePlayerTurn;

                        // print the chess board after processing each move
                        printChessBoard();

                        return;
                    }

                    if (targetRow == 7 && chessBoard.board[currRow][currCol].getPieceName().equals("bp")) {
                        // System.out.println("The user wants to promote pawn to a " + thirdArgument);
                        pawnPromotion(currRow, currCol, targetRow, targetCol);

                        chessBoard.board[currRow][currCol] = null;

                        // alter the value of isWhitePlayerTurn after processing each move
                        isWhitePlayerTurn = !isWhitePlayerTurn;

                        // print the chess board after processing each move
                        printChessBoard();

                        return;
                    }

                    Piece p = chessBoard.board[currRow][currCol];
                    chessBoard.board[currRow][currCol] = null;
                    chessBoard.board[targetRow][targetCol] = p;

                    // make the firstMove variable of this piece false
                    if (chessBoard.board[targetRow][targetCol].isFirstMove()) {
                        chessBoard.board[targetRow][targetCol].setFirstMove(false);
                    }

                    // make the secondMove variable of this piece false
                    if (chessBoard.board[targetRow][targetCol].getSecondMove()) {
                        chessBoard.board[targetRow][targetCol].setSecondMove(false);
                    }

                    // alter the value of isWhitePlayerTurn after processing each move
                    isWhitePlayerTurn = !isWhitePlayerTurn;

                    // print the chess board after processing each move
                    printChessBoard();

                    return;
                } else {
                    // if it is the player's own piece then pawn can't go there
                    // System.out.println("One of your own pieces is there on target tile. You can't
                    // move there!");
                    System.out.println("Illegal move, try again");
                    return;
                }

            } else {
                // if there is no piece present, then we need to check for en passant
                if (isEnPassantAllowed(currRow, currCol, targetRow, targetCol)) {
                    Piece p = chessBoard.board[currRow][currCol];
                    chessBoard.board[currRow][currCol] = null;
                    chessBoard.board[targetRow][targetCol] = p;

                    if (chessBoard.board[targetRow][targetCol].getPieceName().equals("wp")) {
                        chessBoard.board[targetRow + 1][targetCol] = null;
                    } else {
                        chessBoard.board[targetRow - 1][targetCol] = null;
                    }

                    chessBoard.board[targetRow][targetCol].setSecondMove(false);

                    // alter the value of isWhitePlayerTurn after processing each move
                    isWhitePlayerTurn = !isWhitePlayerTurn;

                    // print the chess board after processing each move
                    printChessBoard();

                    return;
                } else {
                    // System.out.println("You can not place the pawn there! En passant is not
                    // allowed!");
                    System.out.println("Illegal move, try again");

                    return;
                }
            }
        }

        // call canPlacePiece() and isPathBlocked() methods on given coordinates
        if (!chessBoard.board[currRow][currCol].canPlacePiece(currRow, currCol, targetRow, targetCol))

        {
            // this means the place can't be placed on the targe tile
            // System.out.println("You can not place this piece on the target tile!");
            System.out.println("Illegal move, try again");
            return;
        }

        if (chessBoard.isPathBlocked(currRow, currCol, targetRow, targetCol)) {
            // this means the path between current and target tiles is blocked
            // System.out
            // .println("The path between current and target tiles is blocked. You can't
            // place the piece there!");
            System.out.println("Illegal move, try again");
            return;
        }

        // if we come down here, that means the path is not blocked and piece can...
        // ...be placed on target tile

        // if the piece is a king, then update its coordinates in variables
        if ((isWhitePlayerTurn && chessBoard.board[currRow][currCol].getPieceName().equals("wK"))) {
            whiteKingRow = targetRow;
            whiteKingCol = targetCol;
        }

        if (!isWhitePlayerTurn && chessBoard.board[currRow][currCol].getPieceName().equals("bK")) {
            blackKingRow = targetRow;
            blackKingCol = targetCol;
        }

        if (isWhitePlayerTurn) {
            Piece currPiece = chessBoard.board[currRow][currCol];
            chessBoard.board[currRow][currCol] = null;
            chessBoard.board[targetRow][targetCol] = currPiece;

            if (chessBoard.isKingUnderCheck(whiteKingRow, whiteKingCol, isWhitePlayerTurn, false)) {
                // this means that moving the piece on current square will result in...
                // ...player's own king getting under check. So we can't allow this move;

                // System.out.println("inside white, king will be in check, move not allowed");
                System.out.println("Illegal move, try again");

                // put the piece back on its original square
                chessBoard.board[targetRow][targetCol] = null;
                chessBoard.board[currRow][currCol] = currPiece;

                return;
            } else {
                whiteKingUnderCheck = false;
            }
            // put the piece back on its original square
            chessBoard.board[targetRow][targetCol] = null;
            chessBoard.board[currRow][currCol] = currPiece;

        } else {
            Piece currPiece = chessBoard.board[currRow][currCol];
            chessBoard.board[currRow][currCol] = null;
            chessBoard.board[targetRow][targetCol] = currPiece;

            if (chessBoard.isKingUnderCheck(blackKingRow, blackKingCol, isWhitePlayerTurn, false)) {
                // this means that moving the piece on current square will result in...
                // ...player's own king getting under check. So we can't allow this move;

                // System.out.println("inside black, king will be in check, move not allowed");
                // System.out.println("Illegal move, try again");

                // put the piece back on its original square
                chessBoard.board[targetRow][targetCol] = null;
                chessBoard.board[currRow][currCol] = currPiece;

                return;
            }
            // put the piece back on its original square
            chessBoard.board[targetRow][targetCol] = null;
            chessBoard.board[currRow][currCol] = currPiece;
        }

        System.out.println("The target row is: " + targetRow + "the piece is: "
                + chessBoard.board[currRow][currCol].getPieceName());
        // check if the player wants to do pawn promotion
        if (targetRow == 0 && chessBoard.board[currRow][currCol].getPieceName().equals("wp")) {
            System.out.println("The user wants to promote pawn to a " + thirdArgument);
            pawnPromotion(currRow, currCol, targetRow, targetCol);

            // alter the value of isWhitePlayerTurn after processing each move
            isWhitePlayerTurn = !isWhitePlayerTurn;

            // print the chess board after processing each move
            printChessBoard();

            return;
        }

        if (targetRow == 7 && chessBoard.board[currRow][currCol].getPieceName().equals("bp")) {
            System.out.println("The user wants to promote pawn to a " + thirdArgument);
            pawnPromotion(currRow, currCol, targetRow, targetCol);

            // alter the value of isWhitePlayerTurn after processing each move
            isWhitePlayerTurn = !isWhitePlayerTurn;

            // print the chess board after processing each move
            printChessBoard();

            return;
        }

        // save the piece on current tile before setting it to null
        Piece p = chessBoard.board[currRow][currCol];

        // set the current piece position in the 2D array to null
        chessBoard.board[currRow][currCol] = null;

        // update the target position in 2D board array to add a piece there
        chessBoard.board[targetRow][targetCol] = p;

        // make the firstMove variable of this piece false
        if (chessBoard.board[targetRow][targetCol].isFirstMove()) {
            chessBoard.board[targetRow][targetCol].setFirstMove(false);
        }

        // make the secondMove variable of this piece false
        if (chessBoard.board[targetRow][targetCol].getSecondMove()) {
            chessBoard.board[targetRow][targetCol].setSecondMove(false);
        }

        // here, after updating the piece position, we need to call...
        // ...detectCheck() and detectCheckmate() methods
        if (isWhitePlayerTurn) {
            if (chessBoard.isKingUnderCheck(blackKingRow, blackKingCol, isWhitePlayerTurn, true)) {
                blackKingUnderCheck = true;
            }
        } else {
            if (chessBoard.isKingUnderCheck(whiteKingRow, whiteKingCol, isWhitePlayerTurn, true)) {
                whiteKingUnderCheck = true;
            }
        }

        // alter the value of isWhitePlayerTurn after processing each move
        isWhitePlayerTurn = !isWhitePlayerTurn;

        // print the chess board after processing each move
        printChessBoard();

    }

    /**
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return boolean true or false whether the user is attempting the castling and
     *         whether or not the castling was successful
     */
    // check if castling is allowed and if it is, then implement it
    public static boolean testAndImplementCastling(int currRow, int currCol, int tarRow, int tarCol) {
        String currPiece = chessBoard.board[currRow][currCol].getPieceName();

        // if the piece is a white king...
        if (currPiece.equals("wK")) {

            // and if the king has not moved already....
            if (chessBoard.board[currRow][currCol].isFirstMove()) {

                // is the target tile 2 squares to the right or 2 squares to the left of king?
                if ((tarCol - currCol == 2) && (tarRow == 7) && (currRow == 7)) {

                    // this means the player wants to do right/short castling
                    // now, check if the right rook has moved already or not
                    if (chessBoard.isPiecePresent(7, 7) && chessBoard.board[7][7].getPieceName().equals("wR")
                            && chessBoard.board[7][7].isFirstMove()) {

                        // this means the right rook is there and it hasn't moved before
                        // now, we need to check if the path between king and rook is clear
                        if (!chessBoard.isPathBlocked(currRow, currCol, 7, 7)) {

                            // this means the path is clear
                            // now, we can finally update the piece positions
                            chessBoard.board[currRow][currCol] = null;
                            chessBoard.board[tarRow][tarCol] = new King(true);

                            // set the first move variable to false because now the king has moved
                            chessBoard.board[tarRow][tarCol].setFirstMove(false);

                            // update the rook's position
                            chessBoard.board[7][7] = null;
                            chessBoard.board[tarRow][tarCol - 1] = new Rook(true);

                            // set the first move variable to false because now the rook has moved
                            chessBoard.board[tarRow][tarCol - 1].setFirstMove(false);

                            return true;
                        } else {
                            // if the path is not clear, then castling can't be done
                            // System.out.println("Castling can't be done! The path between king and rook is
                            // not clear!");
                            System.out.println("Illegal move, try again");
                            return false;
                        }
                    } else {
                        // if the right rook has already moved or if the rook is not there...
                        // ...then castling can't be done
                        // System.out.println(
                        // "Castling can't be done! Either the rook has already moved or it's not
                        // there!");
                        System.out.println("Illegal move, try again");
                        return false;
                    }

                } else if ((tarCol - currCol == -2) && (tarRow == 7) && (currRow == 7)) {
                    // this means the player wants to do left/long castling
                    // now, check if the left rook has moved already or not
                    if (chessBoard.isPiecePresent(7, 0) && chessBoard.board[7][0].getPieceName().equals("wR")
                            && chessBoard.board[7][0].isFirstMove()) {

                        // this means the left rook is there and it hasn't moved before
                        // now, we need to check if the path between king and rook is clear
                        if (!chessBoard.isPathBlocked(currRow, currCol, 7, 0)) {

                            // this means the path is clear
                            // now, we can finally update the piece positions
                            chessBoard.board[currRow][currCol] = null;
                            chessBoard.board[tarRow][tarCol] = new King(true);

                            // set the first move variable to false because now the king has moved
                            chessBoard.board[tarRow][tarCol].setFirstMove(false);

                            // update the rook's position
                            chessBoard.board[7][0] = null;
                            chessBoard.board[tarRow][tarCol + 1] = new Rook(true);

                            // set the first move variable to false because now the rook has moved
                            chessBoard.board[tarRow][tarCol + 1].setFirstMove(false);

                            return true;
                        } else {
                            // if the path is not clear, then castling can not be done
                            // System.out.println("Castling can't be done! The path between king and rook is
                            // not clear!");
                            System.out.println("Illegal move, try again");
                            return false;
                        }
                    } else {
                        // if the left rook has already moved or if the rook is not there...
                        // ...then castling can't be done
                        // System.out.println(
                        // "Castling can't be done! Either the rook has already moved or it's not
                        // there!");
                        System.out.println("Illegal move, try again");
                        return false;
                    }

                } else {
                    // System.out.println("Castling can't be done! Incorrect row and/or column
                    // coordinates provided!");
                    return false;
                }
            } else {
                // System.out.println("Castling can't be done! The king has already moved!");
                System.out.println("Illegal move, try again");
                return false;
            }
        }
        // if the piece is a black king...
        else if (currPiece.equals("bK")) {
            // and if the king has not moved already....
            if (chessBoard.board[currRow][currCol].isFirstMove()) {

                // is the target tile 2 squares to the right or 2 squares to the left of king?
                if ((tarCol - currCol == 2) && (tarRow == 0) && (currRow == 0)) {

                    // this means the player wants to do right/short castling
                    // now, check if the right rook has moved already or not
                    if (chessBoard.isPiecePresent(0, 7) && chessBoard.board[0][7].getPieceName().equals("bR")
                            && chessBoard.board[0][7].isFirstMove()) {

                        // this means the right rook is there and it hasn't moved before
                        // now, we need to check if the path between king and rook is clear
                        if (!chessBoard.isPathBlocked(currRow, currCol, 0, 7)) {

                            // this means the path is clear
                            // now, we can finally update the piece positions
                            chessBoard.board[currRow][currCol] = null;
                            chessBoard.board[tarRow][tarCol] = new King(false);

                            // set the first move variable to false because now the king has moved
                            chessBoard.board[tarRow][tarCol].setFirstMove(false);

                            // update the rook's position
                            chessBoard.board[0][7] = null;
                            chessBoard.board[tarRow][tarCol - 1] = new Rook(false);

                            // set the first move variable to false because now the rook has moved
                            chessBoard.board[tarRow][tarCol - 1].setFirstMove(false);

                            return true;
                        } else {
                            // if the path is not clear, then castling can't be done
                            // System.out.println("Castling can't be done! The path between king and rook is
                            // not clear!");
                            System.out.println("Illegal move, try again");
                            return false;
                        }
                    } else {
                        // if the right rook has already moved or if the rook is not there...
                        // ...then castling can't be done
                        // System.out.println(
                        // "Castling can't be done! Either the rook has already moved or it's not
                        // there!");
                        System.out.println("Illegal move, try again");
                        return false;
                    }

                } else if ((tarCol - currCol == -2) && (tarRow == 0) && (currRow == 0)) {
                    // this means the player wants to do left/long castling
                    // now, check if the left rook has moved already or not
                    if (chessBoard.isPiecePresent(0, 0) && chessBoard.board[0][0].getPieceName().equals("bR")
                            && chessBoard.board[0][0].isFirstMove()) {

                        // this means the left rook is there and it hasn't moved before
                        // now, we need to check if the path between king and rook is clear
                        if (!chessBoard.isPathBlocked(currRow, currCol, 0, 0)) {

                            // this means the path is clear
                            // now, we can finally update the piece positions
                            chessBoard.board[currRow][currCol] = null;
                            chessBoard.board[tarRow][tarCol] = new King(false);

                            // set the first move variable to false because now the king has moved
                            chessBoard.board[tarRow][tarCol].setFirstMove(false);

                            // update the rook's position
                            chessBoard.board[0][0] = null;
                            chessBoard.board[tarRow][tarCol + 1] = new Rook(false);

                            // set the first move variable to false because now the rook has moved
                            chessBoard.board[tarRow][tarCol + 1].setFirstMove(false);

                            return true;
                        } else {
                            // if the path is not clear, then castling can't be done
                            // System.out.println("Castling can't be done! The path between king and rook is
                            // not clear!");
                            System.out.println("Illegal move, try again");
                            return false;
                        }
                    } else {
                        // if the left rook has already moved or if the rook is not there...
                        // ...then castling can't be done
                        // System.out.println(
                        // "Castling can't be done! Either the rook has already moved or it's not
                        // there!");
                        System.out.println("Illegal move, try again");
                        return false;
                    }

                } else {
                    // System.out.println("Castling can't be done! Incorrect row and/or column
                    // coordinates provided!");
                    return false;
                }
            } else {
                // System.out.println("Castling can't be done! The king has already moved!");
                System.out.println("Illegal move, try again");
                return false;
            }
        }

        return false;
    }

    /**
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     * @return boolean true or false whether the enpassant by the pawn is allowed or
     *         not
     */
    // given the current and target coordinates, check if a pawn is attempting...
    // ...to do en passant or not
    public static boolean isEnPassantAllowed(int currRow, int currCol, int tarRow, int tarCol) {
        // find out if the pawn is white or black
        String pieceName = chessBoard.board[currRow][currCol].getPieceName();

        if (pieceName.equals("wp")) {
            if (chessBoard.isPiecePresent(tarRow + 1, tarCol)) {
                // check if the piece is black's piece
                if (!chessBoard.board[tarRow + 1][tarCol].isWhite()) {
                    String pieceName2 = chessBoard.board[tarRow + 1][tarCol].getPieceName();

                    if (pieceName2.equals("bp")) {
                        if (!chessBoard.board[tarRow + 1][tarCol].isFirstMove()
                                && chessBoard.board[tarRow + 1][tarCol].getSecondMove()) {
                            return true;
                        }
                    }
                }
            }
        } else if (pieceName.equals("bp")) {
            if (chessBoard.isPiecePresent(tarRow - 1, tarCol)) {
                // check if the piece is white's piece
                if (chessBoard.board[tarRow - 1][tarCol].isWhite()) {
                    String pieceName2 = chessBoard.board[tarRow - 1][tarCol].getPieceName();

                    if (pieceName2.equals("wp")) {
                        if (!chessBoard.board[tarRow - 1][tarCol].isFirstMove()
                                && chessBoard.board[tarRow - 1][tarCol].getSecondMove()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param currRow the row that the current piece is on
     * @param currCol the column that the current piece is on
     * @param tarRow  the row that the current piece wants to move on
     * @param tarCol  the column that the current piece wants to move on
     */
    // given the row and column coordinates, implement pawn promotion
    public static void pawnPromotion(int currRow, int currCol, int tarRow, int tarCol) {

        // if the piece is white pawn and it is in the row 0 (which is the first row for
        // black)...
        if (chessBoard.board[currRow][currCol].getPieceName().equalsIgnoreCase("wp") && tarRow == 0) {
            // System.out.println("Inside white pawn promotion");
            // if the player has not explicitly mentioned which piece to promote to...
            // ...then make it a queen by default. Or if the player said "q"...
            // ...then also replace the pawn with a queen.
            if (thirdArgument == null || thirdArgument.equals("q")) {
                chessBoard.board[tarRow][tarCol] = new Queen(true);

            }
            // if the player said "r", make the new piece a rook
            else if (thirdArgument.equals("r")) {
                chessBoard.board[tarRow][tarCol] = new Rook(true);

            }
            // if the player said "b", make the new piece a bishop
            else if (thirdArgument.equals("b")) {
                chessBoard.board[tarRow][tarCol] = new Bishop(true);

            }
            // if the player said "n", make the new piece a knight
            else if (thirdArgument.equals("n")) {
                chessBoard.board[tarRow][tarCol] = new Knight(true);

            }
        }
        // if the piece is black pawn and it is in the row 7 (which is the first row for
        // white)...
        else if (chessBoard.board[currRow][currCol].getPieceName().equalsIgnoreCase("bp") && tarRow == 7) {
            // System.out.println("inside black pawn promotion");
            if (thirdArgument == null || thirdArgument.equals("q")) {
                chessBoard.board[tarRow][tarCol] = new Queen(false);

            }
            // if the player said "r", make the new piece a rook
            else if (thirdArgument.equals("r")) {
                chessBoard.board[tarRow][tarCol] = new Rook(false);

            }
            // if the player said "b", make the new piece a bishop
            else if (thirdArgument.equals("b")) {
                chessBoard.board[tarRow][tarCol] = new Bishop(false);

            }
            // if the player said "n", make the new piece a knight
            else if (thirdArgument.equals("n")) {
                chessBoard.board[tarRow][tarCol] = new Knight(false);
            }
        }
    }

    /**
     * @param fileRank the combination of column and row on the chess board
     * @return int the column number ranging from 0 to 7
     */
    // given a fileRank (ex: e2), extract the file (ex: e) and convert to the column
    // number
    // for example: c is 2, a is 0, f is 5 etc.
    public static int convertFileToColumn(String fileRank) {
        // this is a cool one liner hehe
        return (int) fileRank.charAt(0) - (int) 'a';
    }

    /**
     * @param fileRank the combination of column and row on the chess board
     * @return int the row number ranging from 0 to 7
     */
    // given a fileRank (ex: e2), extract the rank (ex: 2) and convert to the row
    // number
    public static int convertRankToRow(String fileRank) {
        // this is a cool one liner hehe
        return 7 - ((int) fileRank.charAt(1) - (int) '1');
    }

    /**
     * @param cr the row that the current piece is on
     * @param cc the column that the current piece is on
     * @param tr the row that the current piece wants to move on
     * @param tc the column that the current piece wants to move on
     * @return boolean is the file rank within the range of 0 to 7 or not
     */
    // check if row and column values are within 0 to 7
    public static boolean isValidInput(int cr, int cc, int tr, int tc) {
        return (cr >= 0 && cr <= 7) && (cc >= 0 && cc <= 7) && (tr >= 0 && tr <= 7) && (tc >= 0 && tc <= 7);
    }
}