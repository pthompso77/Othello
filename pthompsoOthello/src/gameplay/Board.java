
package gameplay;

import interact.IO;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Reformatting and Rewriting to streamline and simplify
 * @author pthompso
 * @version 2019-11-09
 */
public class Board {
    
    //for testing
    static String peekBoard(Board b) {
        int[] arr = b.getBoard();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i+=10) {
            for (int j= 0; j < 10; j++) {
                int value = arr[i+j]+2;
                char visual = VisualBoard.VALUE_VISUAL_ASSOC[value];
                sb.append(visual).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    boolean debug;
    {debug = OthelloDriver.ISDEBUG;}
    boolean board_debug = false;
    
    //end testing

    int[] board; //[100]
    protected final int BOARDSIZE = 100;
    private final int FIRSTLEGALSPACE = 11;
    private final int LASTLEGALSPACE = 88;
    public static final int BLACKINT = 1;
    public static int getBlackInt() {return BLACKINT;}
    public static final int WHITEINT = -1;

    final int ME = 1;
    final int OPPONENT = -1;
    final int BORDER = -2;
    final int EMPTYSPACE = 0;
    // Directions for moving across the board from a given space
    final int NORTHWEST = -11;
    final int NORTH = -10;
    final int NORTHEAST = -9;
    final int EAST = 1;
    final int SOUTHEAST = 11;
    final int SOUTH = 10;
    final int SOUTHWEST = 9;
    final int WEST = -1;
    final int[] DIRECTIONS = {NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST};

    public Board() {
        int black = 1;
        int white = -1;
        board = new int[BOARDSIZE];
        //top border
        for (int s = 0; s < 10; s++) {
            board[s] = BORDER;
        }
        for (int row = 10; row < BOARDSIZE - 10; row += 10) {
            board[row] = BORDER;
            board[row + 9] = BORDER;
            //            int square = row + 1;
            for (int square = 1; square < 9; square++) {
                board[row + square] = EMPTYSPACE;
            }
        }
        //bottom border
        for (int s = BOARDSIZE - 10; s < BOARDSIZE; s++) {
            board[s] = BORDER;
        }
        //initialize first four pieces
        board[44] = white;
        board[55] = white;
        board[45] = black;
        board[54] = black;
    }

    public Board(int[] arr) {
        board = arr;
        if (debug) 
        {System.out.println("C ===== Got this board! =====");
            printBoard();
        }
    }

    /**
     * Prints this gameboard to the output window
     */
    void printBoard() {
        //    ===== Board =====
        //    fun 0. Print board
        VisualBoard.printBoard(this);
        //    ---
    }

    /**
     * Returns the next move for the current player to play
     *
     * @param currentPlayer player with the current turn
     * @param isMe True if the current player is the "computer" agent (this game
     * player)
     * @return if opponent: gets move from standard input. else: returns best
     * move for this agent based on the evaluation
     */
    Move getNextMove(int currentPlayer, boolean isMe) {
        // if current player is opponent, ask for move
        if (!isMe) {
            String opponentInput = IO.getOpponentMove(currentPlayer);
            Move opponentMove = Move.parseInput(opponentInput);
            if (debug) System.out.println("C got opponentMove: " + opponentMove);
            return opponentMove;
        }
        // if current player is ME, we rely on Player to evaluate moves and return the best
        ArrayList movesList = Evaluation.populateMovesForPlayer(this, currentPlayer);
        return Player.getBestMove(this, movesList, currentPlayer);
        //    ---
    }

    /**
     * Populates the list of legal moves for the specified player
     *
     * @param currentPlayer Player who has the current turn
     * @return list of legal moves
     */
    ArrayList populateMovesForPlayer(int currentPlayer) { //TODO move this to Evaluation
        ArrayList<Move> movesList = new ArrayList<>();
        ArrayList<Integer> spacesList = getAllEmptySpaces();
        for (Integer space : spacesList) {
            Move legalMove = getLegalMove(space, currentPlayer);
            if (!legalMove.isPass()) {
                movesList.add(legalMove);
            }
        }
        //            return Moves list (heap, or max heap?)
        //---
        // TODO_LATER order the moves based on evaluated worth?
        return movesList;
//        movesList.get(0).getEndPosition() == -1;
    }
    public static ArrayList populateMovesForPlayer1(Board b, int currentPlayer) {
        return b.populateMovesForPlayer(currentPlayer);
    }

    /**
     * Examines the gameboard for empty spaces
     *
     * @return a list of locations that are empty (do not have pieces on them)
     */
    private ArrayList<Integer> getAllEmptySpaces() {
        //    fun 5. Get list of empty spaces(board info), returns list of ints:
        //            initialize list of empties
        ArrayList<Integer> spacesList = new ArrayList<>();
        int currentSpaceValue;
        for (int i = FIRSTLEGALSPACE; i <= LASTLEGALSPACE; i++) {
            currentSpaceValue = board[i];
            if (currentSpaceValue == EMPTYSPACE) {
                spacesList.add(i);
            }
        }
        //            return empties
        return spacesList;
        //    ---
    }

    /**
     * Determines if an empty space is a legal move This also collects all the
     * "flippers" for the move to avoid having to go through all of that again
     * in another method.
     *
     * @param spaceOnBoard the space to be evaluated
     * @return the Move if there is a legal move to this space. If not, it
     * returns the pass move
     */
    private Move getLegalMove(int spaceOnBoard, int currentPlayer) {
        ArrayList toBeFlipped = findFlippers(spaceOnBoard, currentPlayer);
//        int otherPlayer = (-1 * currentPlayer);
//        int currentSpace, startSpace = spaceOnBoard;
//        ArrayList<Integer> toBeFlipped = new ArrayList<>();
//        for (int dir : DIRECTIONS) {
        // find and add all the opponent pieces that will need to be flipped for this Move (if any)
//            toBeFlipped.addAll(exploreDirectionFromSpace(dir, spaceOnBoard, currentPlayer));
//            currentSpace = spaceOnBoard + dir; //advance one space in this direction
//            if (board[currentSpace] == otherPlayer) {
//                // start collecting opponent pieces that will need to be flipped
//                ArrayList<Integer> toBeFlipped = new ArrayList<>();
//                while (board[currentSpace] == otherPlayer) {
//                    toBeFlipped.add(currentSpace);
//                    currentSpace += dir;
//                }
//                if (board[currentSpace] == currentPlayer) //we found a move!
//                {
//                    return new Move(startSpace, currentSpace, toBeFlipped);
//                }
//            }
//        }
//        if (toBeFlipped.isEmpty()) { //then this is not a legal Move
//            return Move.passMove;
//        }
//        else return new Move(spaceOnBoard, toBeFlipped);
        if (toBeFlipped.isEmpty()) { //then this is not a legal Move
            return Move.passMove;
        } else {
            return new Move(spaceOnBoard, toBeFlipped);
        }
    }

    /**
     * Searches in the given direction searching for a legal move
     *
     * @param direction int representing cardinal direction
     * @param startingSpace the starting space on the board to evaluate from
     * @param player player for whom we are searching for a legal move
     * @return an ArrayList of integers representing the opponent spaces that
     * were found and need to be flipped (black to white, or vice versa)
     */
    private ArrayList exploreDirectionFromSpace(int direction, int startingSpace, int player) {
        ArrayList<Integer> opponentPiecesFound = new ArrayList<>();
        int opponent = (-1 * player);
        int currentSpace = startingSpace;
        currentSpace += direction;
        if (board[currentSpace] == opponent) {
            while (board[currentSpace] == opponent) {
                opponentPiecesFound.add(currentSpace);
                currentSpace += direction;
            }
            if (board[currentSpace] == player) { //this is a valid move!
                return opponentPiecesFound;
            } else {
                opponentPiecesFound.clear();
            }
        }
        return opponentPiecesFound;
    }

    ArrayList findFlippers(int spaceOnBoard, int player) {
        return findFlippers(spaceOnBoard, player, false);
    }

    // findFlippers version 10-15
    /**
     * We are given a position for a move, and a player The goal is to find the
     * pieces in every direction that belong to the opponent
     */
    ArrayList findFlippers(int spaceOnBoard, int player, boolean endEarly) {
        int otherPlayer = (-1 * player);
        int currentSpace;// = spaceOnBoard;
        ArrayList<Integer> toBeFlipped = new ArrayList<>();
        for (int dir : DIRECTIONS) {
            if (endEarly && !toBeFlipped.isEmpty()) {
//                System.out.println("C Game isn't over -- I checked");
                if (debug) System.out.println("C There is still at least one valid move -- I checked");
                return toBeFlipped;
            }
            ArrayList toAdd = exploreDirectionFromSpace(dir, spaceOnBoard, player);
            if (OthelloDriver.ISDEBUG) {
                if (board_debug) System.out.println("C flips toAdd: " + toAdd.toString());
            }
            toBeFlipped.addAll(toAdd);
//            currentSpace = spaceOnBoard + dir; //advance one space in this direction
//            System.out.println("C ");
//            if (board[currentSpace] == otherPlayer) {
//                // start collecting opponent pieces that will need to be flipped
//                ArrayList<Integer> tempFlips = new ArrayList<>();
//                while (board[currentSpace] == otherPlayer) {
//                    tempFlips.add(currentSpace);
//                    currentSpace += dir;
//                }
//                if (board[currentSpace] == player) {
//                    //we found a move!
//                    toBeFlipped.addAll(tempFlips);
//                }
//            }
        }
        if (!toBeFlipped.isEmpty()) {
            if (debug) System.out.println("C getting flippers for player: " + IO.playerInt_txt(player));
            String visualMove = VisualBoard.seeColRowFromLocation(spaceOnBoard);
            if (debug) System.out.println("C foundFlippers for move " + visualMove + ": " + toBeFlipped);
            String s;
            for (int i : toBeFlipped) {
                s = VisualBoard.seeColRowFromLocation(i);
                if (debug) {
                    System.out.print("C " + s + ", ");
                }
            }
//            System.out.println("\b\b");
        } else {
            if (debug) System.out.println("C No flippers found for this move");
        }

        return toBeFlipped;
    }

//    /**
//     * This is designed to replace hasLegalMove/getLegalMove (same) above
//     */
//    private boolean isLegalMove(int spaceOnBoard, int currentPlayer) {
//        ArrayList a = findFlippers(spaceOnBoard, currentPlayer);
//        return (! a.isEmpty()); //no pieces to flip? not a legal move.
//    }
    ArrayList<Integer> findFlippers_old(Move thisMove, int player) {
        System.out.println("C moving " + player + " to: " + thisMove);
        int otherPlayer = (-1 * player);
        int spaceOnBoard = thisMove.getMovePosition();
        int currentSpace;// = spaceOnBoard;
        ArrayList<Integer> toBeFlipped = new ArrayList<>();
        for (int dir : DIRECTIONS) {
            if (debug) System.out.println("C now going in direction: " + dir);
            currentSpace = spaceOnBoard + dir; //advance one space in this direction
            if (debug) System.out.println("C ");
            if (board[currentSpace] == otherPlayer) {
                // start collecting opponent pieces that will need to be flipped

                ArrayList<Integer> tempFlips = new ArrayList<>();
                while (board[currentSpace] == otherPlayer) {
                    if (debug) System.out.println("C nowSpace = " + currentSpace);
                    tempFlips.add(currentSpace);
                    currentSpace += dir;
                    if (debug) System.out.println("C now nowSpace = " + currentSpace);
                }
                if (board[currentSpace] == player) {
                    //we found a move!
                    if (debug) System.out.println("C found opponent at " + currentSpace);
//                    thisMove.setEndPosition(currentSpace); 
                    toBeFlipped.addAll(tempFlips);
//TODO rewrite this for opponent only (find flippers only, no setting end position)
                }
            }
        }
        if (debug) System.out.println("C Flippers = " + toBeFlipped);
        return toBeFlipped;
    }

    boolean isGameOver() {
        ArrayList<Integer> spaces = getAllEmptySpaces();
        ArrayList allFlipps, blackFlips, whiteFlips;
        allFlipps = new ArrayList<>();
        for (int space : spaces) {
            for (int dir : DIRECTIONS) {
                blackFlips = exploreDirectionFromSpace(dir, space, BLACKINT);
                allFlipps.addAll(blackFlips);
                //debug
                if (board_debug) {
                    System.out.println("C blackFlips: "
                            + blackFlips.toString());
                }
                whiteFlips = exploreDirectionFromSpace(dir, space, WHITEINT);
                allFlipps.addAll(whiteFlips);
                //debug
                if (board_debug) {
                    System.out.println("C whiteFlips: "
                            + whiteFlips.toString());
                }
//                allFlipps.addAll(exploreDirectionFromSpace(dir, space, -1));
                if (!allFlipps.isEmpty()) {
                    if (debug) System.out.println("C Game isn't over -- I checked");
                    return false;
                }
            }
        }
        return true;
        //this takes too much time
//        return (populateMovesForPlayer(OPPONENT).isEmpty()
//                && populateMovesForPlayer(ME).isEmpty());
    }

    /**
     *
     * @return integer array representing the board
     */
    int[] getBoard() {
        return board;
    }

    /**
     *
     * @param moveLocation
     * @param player
     * @return
     */
    boolean isMoveValid(int moveLocation, int player) {
        return !findFlippers(moveLocation, player).isEmpty();
    }

    public static boolean isMoveValid(Board gameboard, int location, int player) {
        return gameboard.isMoveValid(location, player);
    }

    /**
     * FOR TESTING ONLY
     *
     * @return the board that the program is hung up on
     */
    public static Board getDebugBoard() {
//        int[]  boardArry = {        -2, -2, -2, -2, -2, -2, -2, -2, -2, -2,        -2, 0, 1, 1, 1, 0, -1, -1, -1, -2,        -2, 0, 0, 1, 1, 1, 1, -1, 1, -2,        -2, 0, 0, 1, 1, 1, 1, 1, 1, -2,        -2, 0, 0, 1, -1, -1, -1, -1, 0, -2,        -2, 0, 0, 1, -1, -1, 0, 0, 0, -2,        -2, 0, 1, 0, 0, 0, 0, 0, 0, -2,        -2, 0, 0, 0, 0, 0, 0, 0, 0, -2,        -2, 0, 0, 0, 0, 0, 0, 0, 0, -2,        -2, -2, -2, -2, -2, -2, -2, -2, -2, -2        } ;
        //{-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 0, 0, 0, 0, 0, 0, 0, 0, -2, -2, 0, 0, 0, 0, 0, 0, 0, 0, -2, -2, 0, 0, 1, 1, 0, 0, 0, 0, -2, -2, 0, 0, -1, 1, -1, -1, 0, 0, -2, -2, 0, 0, 0, 1, 1, 0, 0, 0, -2, -2, 0, 0, 0, 0, 1, 0, 0, 0, -2, -2, 0, 0, 0, 0, 0, 0, 0, 0, -2, -2, 0, 0, 0, 0, 0, 0, 0, 0, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2};
//        int[] temp = {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -1, 1, -1, -1, -1, -1, -1, -1, -2, -2, -1, 1, 1, -1, -1, -1, -1, -1, -2, -2, -1, 0, 1, 1, 1, -1, -1, -1, -2, -2, -1, 0, 0, 0, 0, 0, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2};
        int[] temp2 = {-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1,1,1,1,1,1,1,1,-2,-2,-1,-1,-1,-1,1,1,1,1,-2,-2,-1,-1,-1,1,1,1,1,1,-2,-2,0,-1,-1,1,1,1,1,1,-2,-2,0,-1,-1,-1,1,1,1,1,-2,-2,-1,-1,-1,-1,1,1,1,1,-2,-2,0,0,0,0,1,1,1,1,-2,-2,0,0,0,-1,-1,-1,-1,1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2};
        int[] temp3 = {-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,0,0,0,0,0,0,0,0,-2,-2,0,0,0,0,0,1,0,0,-2,-2,0,0,0,1,1,0,0,0,-2,-2,0,0,-1,-1,-1,0,0,0,-2,-2,0,0,0,1,-1,0,0,0,-2,-2,0,0,0,0,0,0,0,0,-2,-2,0,0,0,0,0,0,0,0,-2,-2,0,0,0,0,0,0,0,0,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2};
        int[] boardArry = temp3;
        System.out.println("C ------- DEBUG BOARD ----------- ");
        Board b = new Board(boardArry);
//        b.printBoard();
        return b;
    }

    //for testing
    public static void main(String[] stuffs) {
        Board b = getDebugBoard();
        int moveLocation = 23;
        int player = -1;
        ArrayList arry = b.findFlippers(moveLocation, player);
    }

    /**
     * Copy this board
     *
     * @return a copy of this board
     */
    Board copyBoard() {
        int[] arr = this.getBoard();
        return new Board(Arrays.copyOf(arr, arr.length));
//        return new Board(arr);
    }

}

/**
 *    // CAN I GET RID OF THIS? public Board(int thisIsOld) { board = new
 * int[BOARDSIZE];
 *
 * for (int row = 0; row < BOARDSIZE; row += 10) { board[row] = BORDER;
 * board[row + 9] = BORDER; // int square = row + 1; for (int square = 1; square
 * < 9; square++) { board[row + square] = EMPTYSPACE; } } }
 */
