package gameplay;

//import interact.IO;

import java.util.ArrayList;

//import java.util.Timer;
//import java.util.TimerTask;

/**
 * Contains all methods that analyze the board
 * Cleaning up for final submission
 * @author pthompso
 * @version 2019-12-02
 */
public class BoardAnalyzer {

    static int countBoardMovesForPlayer(Board b, int player) {
        ArrayList movesList = Board.populateMovesForPlayer1(b,player);
        return movesList.size();
    }
        /**
     * Examines the gameboard for empty spaces
     *
     * @return a list of locations that are empty (do not have pieces on them)
     */
    private ArrayList<Integer> getAllEmptySpaces() {
        //    fun 5. Get list of empty spaces(board info), returns list of ints:
        //            initialize list of empties
        /*
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
*/
        return new ArrayList<>();
    }

        
    static int countPiecesWithColor(int color, Board b) {
        int count = 0;
        for (int space : b.getBoard()) {
            if (space == color) count++;
        }
        return count;
    }

}
