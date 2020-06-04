
package gameplay;

import interact.IO;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cleaning up for final submission
 * @author pthompso
 * @version 2019-12-02
 */
public class Move {

    private int evaluatedWorth;
    private int movePosition;
    private ArrayList<Integer> flippers;
    public static Move passMove = new Move("pass");
    public static final int PASS_POSITION = -1;
    public static Move commentMove = new Move("C");
    public static final int COMMENT_POSITION = -2;

    @Override
    public String toString() {
        if (this.isPass()) {
            return "Pass";
        }
        return ("Move to " + VisualBoard.getColumnTextValue(movePosition)
                 + VisualBoard.getRowTextValue(movePosition));
    }
    
//    public String toString_old() {
//        return "Move: from " + movePosition + " to " + endPosition;
//    }

// START constructors
    public Move(int boardPosition) {
        movePosition = boardPosition;
        flippers = new ArrayList<>();
            //we will find the flippers when updateBoardAfterMove sees it
    }
    

    public Move(int boardPosition, ArrayList<Integer> flippers) {
        evaluatedWorth = 0;
        movePosition = boardPosition;
//        endPosition = end;
        this.flippers = flippers;
    }

    public Move(String passOrComment) {
        boolean isComment = (passOrComment.equals("C"));
        movePosition = (isComment) ? COMMENT_POSITION : PASS_POSITION;
        flippers = new ArrayList<>();
    }
    
    public Move() {
        movePosition = PASS_POSITION;
        flippers = new ArrayList<>();
    }
// END constructors
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 

    public boolean isPass() {
        return movePosition == -1;
    }

    public boolean isComment() {
        return movePosition == -2;
    }

//    ===== Move (maybe Board) =====
    /**
     * fun 7. Get list of pieces that need flipping(board info, move, player?),
     * returns list of ints: // -- (best handled within Move class?) // -- if
     * not: // // I just think it should be in Move.... // ... return list ...
     * // ---
     */
    void setFlippers(ArrayList<Integer> flippers) {
        this.flippers = flippers;
    }

    void addFlipper(int spaceThatWillFlip) {
        flippers.add(spaceThatWillFlip);
    }

    ArrayList<Integer> getFlippers() {
        return flippers;
    }

    void setMovePosition(int movePosition) {
        this.movePosition = movePosition;
    }

    public int getMovePosition() {
        return movePosition;
    }
//
//    void setEndPosition(int endPosition) {
//        if (endPosition == Integer.MIN_VALUE) {
//        this.endPosition = endPosition;
//        }
//    }

    //This is not needed
//    public int getEndPosition() {
////        return endPosition;
//        return Integer.MIN_VALUE; // TODO endPosition should not be necessary
//    }

    //TODO revisit if this is needed
//    static Move parseInput(String userInput1, String userInput2) {
//        //for testing
//        return new Move(Integer.parseInt(userInput1), Integer.parseInt(userInput2));
//    }

    static Move parseInput(String opponentInput) {
        //maybe this belongs in IO?
        MoveParser parser = new MoveParser();
        Move opponentMove = parser.getMoveFromInput(opponentInput);
        return opponentMove;
    }

    
    

    private static class MoveParser {

        // legal commands
        String initCmd = "I"; // initialize as a certain color/player
        String blackCmd = "B"; // black player command
        String whiteCmd = "W"; // white player command
        String commentIgnore = "C"; // comment - ignore the rest of the line

        public MoveParser() {
        }

        private Move getMoveFromInput(String opponentInput) {
//      split the input using spaces as separation
            String[] commandTokens = opponentInput.split(" ");
            //option 1: comment - ignore
            if (commandTokens[0].equals("C")) {
                return Move.commentMove;
            }
            //option 2: must be either end game or a pass
            if (commandTokens.length == 1) {
                String cmd = commandTokens[0];
                // 2a: player passes
                if (cmd.equals(blackCmd) || cmd.equals(whiteCmd)) {
                    return Move.passMove;
                }
                // 2b: game is over
                try { //is cmd an int?
                    int c = Integer.parseInt(cmd);
                    return Move.passMove; //or return something else? 
                } catch (NumberFormatException ex) {
                    System.out.println("C Sorry, I don't understand your input\n\t"
                            + ex.getMessage());
                }
            }
            //option 3: the player is making a move
            assert (commandTokens.length == 3) : "C wrong length in MoveParser.getMove()!";
            int player, columnValue, rowValue, boardPosition;
            // get player
            player = parsePlayer(commandTokens[0]);
            // get column value
            columnValue = parseColumnValue(commandTokens[1]);
            // get row value
            rowValue = parseRowValue(commandTokens[2]);
            // convert col/row to board position
            boardPosition = columnValue +  (10 * rowValue);
            return new Move(boardPosition);
        }

        private int parsePlayer(String commandToken) {
            char player = commandToken.charAt(0);
            int playerInt = (player == VisualBoard.BLACK_VISUAL) ? Board.BLACKINT : Board.WHITEINT;
            return playerInt;
        }

        private int parseColumnValue(String commandToken) {
            String columnBase = "a";
            int columnInt = 1 + commandToken.compareToIgnoreCase(columnBase);
            return columnInt;
        }

        private int parseRowValue(String commandToken) {
            int rowInt = Integer.parseInt(commandToken);
            return rowInt;
        }

    }

// for testing only
//    public static void main(String[] args) {
//        System.out.println("C Move.main: TESTING");
//
//        Move move = parseInput("W e 5");
//        System.out.println("C " + move);
//    }

}
