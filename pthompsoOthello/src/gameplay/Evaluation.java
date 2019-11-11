package gameplay;

import java.util.ArrayList;

/**
 * Reformatting and Rewriting to streamline and simplify
 * Holds methods for evaluating boards for best moves
 * @author pthompso
 * @version 2019-11-09
 */

public class Evaluation {

    static Move ab_getMyMove(Board gameboard, /*ArrayList<Move> movesList,*/ int currentPlayer) {
        // evaluate the gametree of possible boards for each move in MovesList
        //TODO maybe I don't need the other method to generate the movesList :-O
        //return the best move given whatever constraints (timer, etc)
        ArrayList<Move> movesList = populateMovesForPlayer(gameboard, currentPlayer);
        if (movesList.isEmpty()) return Move.passMove;
        else return movesList.get(0);
        }

    static ArrayList populateMovesForPlayer(Board b, int currentPlayer) { 
    //TODO move this to Evaluation
    return Board.populateMovesForPlayer1(b, currentPlayer);
    /* This needs to be finished
        ArrayList<Move> movesList = new ArrayList<>();
        ArrayList<Integer> spacesList = getAllEmptySpaces();
        //            iterate through spaces:
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
*/
    }
    
} //end Evaluation class
