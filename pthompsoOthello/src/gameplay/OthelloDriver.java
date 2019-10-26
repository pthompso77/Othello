/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import interact.IO;

/**
 *
 * @author pthompso
 * @version 2019-10-26
 */
public class OthelloDriver {

    private static int currentPlayer;
    private static int MYVALUE;
    private static int OPPONENTVALUE; //to keep track of who is whom
    public static final boolean isdebug = false;

    public static void main(String[] args) {
        @SuppressWarnings("UnusedAssignment")
        Board gameboard = new Board();
        currentPlayer = Board.BLACKINT;
            // for testing only
        if (isdebug) {
            gameboard = Board.getDebugBoard();
//            gameboard.printBoard();
            MYVALUE = IO.getCurrentPlayer();
            System.out.println("C What color goes next?");
            currentPlayer = IO.getCurrentPlayer(false);
        }
        else {
            MYVALUE = IO.getCurrentPlayer(); //Please initiate color (I W or I B)
        } //Please initiate color (I W or I B)
        OPPONENTVALUE = (-1 * MYVALUE);
        gameboard.printBoard();
        while (!gameboard.isGameOver()) {
            //newstuff
            Move currentMove = getMoveFromPlayer(gameboard, currentPlayer);
            if (currentPlayer == MYVALUE) {
                //print my move to std.out
                IO.printMoveOutput(currentPlayer, currentMove);
            }
//            // Update the board
            BoardHandler.updateBoardAfterMove(currentMove, gameboard, currentPlayer);
            gameboard.printBoard();
            currentPlayer *= -1;
        }
    }

    private static Move getMoveFromPlayer(Board gameboard, int player) {
        System.out.println("C getMoveFromPlayer() has: player="+player+" and MYVALUE="+MYVALUE);
        if (player == MYVALUE) {
            // "computer" makes a move
            Move m = gameboard.getNextMove(MYVALUE, true);
            
            //TODO SOMETHING BREAKS RIGHT HERE
            
            System.out.println("C getNextMove(mine) is " + m);
            return m;
        } else {
            // opponent makes a move
            Move o = gameboard.getNextMove(OPPONENTVALUE, false);
            System.out.println("C getNextMove(other) is " + o);
            return o;
        }
    }

    //        DR MEC: =============================================================
    //        Main Function:
    //                Initialize Board:
    //                        color <- Interact:: Get starting color from opponent
    //                        gameboard <- Board:: New Board(color)
    //                        Print "Playing as [color]"
    //                        if (color is Black):
    //                                currentPlayer = me
    //                        else:
    //                                currentPlayer = opponent
    //                Print gameboard
    //                while (Board: check if legal moves exist):
    //                OR: while (?? : game is not over):
    //                        currentMove <- Board:: Get Move (currentPlayer)
    //                        Board:: Make this move (currentMove)
    private static void oldstuff() {
        //old shit
        // "computer" makes a move
//            Move currentMove = gameboard.getNextMove(gameboard.ME);
//            IO.printMoveOutput(currentPlayer, currentMove);
//            // Update the board
//            BoardHandler.updateBoardAfterMove(currentMove, gameboard, gameboard.ME);
//            // print board
//            gameboard.printBoard();
//
//            // opponent makes a move
//            // ask for user input
//            String userInput = IO.getOpponentMove();
//            Move otherMove = Move.parseInput(userInput);
//            // Update the board
//            BoardHandler.updateBoardAfterMove(otherMove, gameboard, gameboard.OPPONENT);
//            // print board            
//            gameboard.printBoard();
    }

}
