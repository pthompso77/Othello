
package gameplay;
import java.util.ArrayList;

/**
 * Reformatting and Rewriting to streamline and simplify
 * @author pthompso
 * @version 2019-11-09
 */
class BoardActor {
    
    //for testing
    


    static void updateBoardAfterMove(Move thisMove, Board gameboard, int color) {
        if (thisMove.isPass()) {
            // player passes
            return;
        }
        int movePosition = thisMove.getMovePosition();
        if (! Board.isMoveValid(gameboard, movePosition, color)) {
//        if (! gameboard.isMoveValid(thisMove.getMovePosition(), color)) {
            //TODO check to see if the move is actually VALID for the player first
            return;
        }
        //TODO use a setter for this and check that we aren't changing a border space!
            //eg. between FIRSTLEGALSPACE and LASTLEGALSPACE
        gameboard.board[thisMove.getMovePosition()] = color;
        ArrayList<Integer> flippers;
        flippers = thisMove.getFlippers();
        if (flippers.isEmpty()) {
            //find the flippers!
            flippers = gameboard.findFlippers(thisMove.getMovePosition(), color);
            thisMove.setFlippers(flippers);
        }
//        Flip the pieces
        for (Integer piece : flippers) {
            gameboard.board[piece] = (-1 * gameboard.board[piece]);
        }
        //            (return board?)
    }
    
    static int countPiecesWithColor(int color, Board b) {
        int count = 0;
        for (int space : b.getBoard()) {
            if (space == color) count++;
        }
        return count;
    }

    

    //    ===== BoardActor =====
    //fun 8. Flip the pieces(board, flippers list):
    //	for each flipper:
    //		set board location at flipper <- (-1 * flipper)
    //---

}
