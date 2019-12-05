
package gameplay;

import interact.IO;
import java.util.ArrayList;

/**
 * Cleaning up for final submission
 * @author pthompso
 * @version 2019-12-02
 */
class Player {

    /**
     * (old) Uses an evaluation function to score each move in a list to find
     * the best move
     *
     * @param gameboard the current gameboard
     * @param movesList list of current legal moves (of type: Move)
     * @param currentPlayer player for whom the list of moves is for
     * @return the Move that receives the highest score from the evaluation
     * function
     */
    static Move getBestMove_old(Board gameboard, ArrayList<Move> movesList, int currentPlayer) {
        try {
            if (!movesList.isEmpty()) {
                Object bestMove = movesList.get(0);
                assert bestMove instanceof Move : "Object is not a Move type";
                return (Move) bestMove;
            }
        } catch (NullPointerException e) {
            System.out.println("C NullPointerException " + e.getMessage());
        }
        return new Move("pass");
    }

    public static void main(String[] ughs) {
        System.out.println("\n\t=== Player.main()... ===\n");
        Board b = new Board();
        Move m = new Move(45);
        System.out.println(m);
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(m);
        System.out.println(moves);
        System.out.println("best move:" + getBestMove(b, moves, 1));
    }

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

        return Evaluation_orig.ab_getMyMove(gameboard, movesList, currentPlayer);

    }


}
