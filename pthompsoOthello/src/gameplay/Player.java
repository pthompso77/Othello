/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import java.util.ArrayList;

/**
 *
 * @author pthompso
 * @version 2019-10-26
 */
class Player {

    /**
     * Uses an evaluation function to score each move in a list to find the best
     * move
     *
     * @param gameboard the current gameboard
     * @param movesList list of current legal moves (of type: Move)
     * @param currentPlayer player for whom the list of moves is for
     * @return the Move that receives the highest score from the evaluation
     * function
     */
    static Move getBestMove(Board gameboard, ArrayList<Move> movesList, int currentPlayer) {
        //    ===== Player =====
        //    fun 4. Evaluate moves for player(board info, Moves list, player), returns Move:
        //            -- (just return first move for now)
        try {
            if (!movesList.isEmpty()) {
                System.out.println("C Player.getBestMove has " + movesList.get(0));
                Object bestMove = movesList.get(0);
                assert bestMove instanceof Move : "Object is not a Move type";
                return (Move) bestMove;
            }
        }
        catch (NullPointerException e) {
            System.out.println("C NullPointerException "+e.getMessage());
        }
        //            initialize data structure (max heap?)
        //            iterate through Moves list:
        //                    9. set move.value <- Evaluate move
        //                    10. place Move in data structure, in correct order
        //            return best Move
        //    ---
        return new Move("pass");
    }

    //    ===== Player =====
    //fun 9, Evaluate move(move), returns int?:
    //	--for now?
    //	return a million
    //---
    //===== Player? =====
    //fun 10. Place Move in data structure:
    //	(can skip this for now)
}
