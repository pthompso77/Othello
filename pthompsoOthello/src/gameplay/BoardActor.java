
package gameplay;
import java.util.ArrayList;

/**
 * Cleaning up for final submission
 * @author pthompso
 * @version 2019-12-02
 */
class BoardActor {
    
    //for testing
    


    static void updateBoardAfterMove(Move thisMove, Board gameboard, int color) {
        if (thisMove.isPass()) {
            return; // player passes
        }
        int movePosition = thisMove.getMovePosition();
        if (! Board.isMoveValid(gameboard, movePosition, color)) {
            return;
        }
        //TODO use a setter for this and check that we aren't changing a border space!
            //eg. between FIRSTLEGALSPACE and LASTLEGALSPACE
        gameboard.board[movePosition] = color; //place the player's piece
        ArrayList<Integer> flippers = thisMove.getFlippers();
        if (flippers.isEmpty()) { // flippers are empty only if this is the opponent's move
            //find the flippers and set the Move's flippers attribute
            flippers = gameboard.findFlippers(movePosition, color);
            thisMove.setFlippers(flippers);
        }
//        Flip the pieces
        for (Integer piece : flippers) {
            gameboard.board[piece] = (-1 * gameboard.board[piece]);
        }
    }
    
    /**
     * Moved to BoardAnalyzer
     * @param color
     * @param b
     * @return 
     */
    static int countPiecesWithColor(int color, Board b) {
        System.out.println("C !! used wrong method in BoardActor. Use BoardAnalyzer");
        int count = 0;
        for (int space : b.getBoard()) {
            if (space == color) count++;
        }
        return count;
    }


}
