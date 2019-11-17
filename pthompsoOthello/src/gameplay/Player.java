
package gameplay;

import interact.IO;
import java.util.ArrayList;

/**
 * Reformatting and Rewriting to streamline and simplify
 * @author pthompso
 * @version 2019-11-09
 */
class Player {

    final static boolean isDebug = OthelloDriver.ISDEBUG;

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
                if (isDebug) System.out.println("C Player.getBestMove has " + bestMove);
                assert bestMove instanceof Move : "Object is not a Move type";
                return (Move) bestMove;
            }
        } catch (NullPointerException e) {
            System.out.println("C NullPointerException " + e.getMessage());
        }
        //            initialize data structure (max heap?)
        //            iterate through Moves list:
        //                    9. set move.value <- Evaluate move
        //                    10. place Move in data structure, in correct order
        //            return best Move
        //    ---
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


        /* Maybe use an ArrayList?
        //            initialize data structure (max heap?)
        ArrayList<MoveWithValue> bestMoves = new ArrayList<>(); //to keep track of best moves!
        //            iterate through Moves list:
        int moveValue;
         */

 /* or maybe use a MaxHeap? */
//        MaxHeap bestMoves = new MaxHeap();
//        for (Move m : movesList) {
////            moveValue = evaluateMove(m);
//            bestMoves.addAfterEvaluating(gameboard, m);
//        }
//        return bestMoves.getBest();

        /* or maybe use AlphaBeta itself? */
        
        
        
        
        
        // + + + + + + + CURRENT WORK + + + + + ++ + + ++ ++ + + + + + + + + + + + + 
        //TODO does currentPlayer match color??? (it should be...)
        return Evaluation.ab_getMyMove(gameboard, currentPlayer);

        // + + + + + + + CURRENT WORK + + + + + ++ + + ++ ++ + + + + + + + + + + + + 
        
        
        
        
    }

    //    ===== Player =====
    //fun 9, Evaluate move(move), returns int?:
    //	--for now?
    //	return a million
    //---
    //===== Player? =====
    //fun 10. Place Move in data structure:
    //	(can skip this for now)
    private static class MaxHeap //implements java.util.Collection
    {

        final boolean isDebug = OthelloDriver.ISDEBUG;

        ArrayList<MoveWithValue> heap;

        private MaxHeap() {
            //TODO?
        }

        private boolean add(MoveWithValue m) {
            heap.add(m);
            //TODO arrange MaxHeap properly
            return true;
        }


        private MoveWithValue get(int index) {
            return heap.get(index);
        }

        private Move getMoveAtIndex(int index) {
            MoveWithValue mv = heap.get(index);
            return mv.getMove();
        }

        private Move getBest() {
            //TODO re-write this with actual MaxHeap implementation
            try {
                if (heap.isEmpty()) {
                    return Move.passMove;
                }
            } catch (NullPointerException e) {
                System.out.println("C\tWoops...at Move.getBest()" + e.getMessage());
                if (isDebug) {
                    if (IO.getYesFromUser("C\tContinue?")) {
                        return Move.passMove;
                    } else {
                        System.out.println("End here.");
                        System.exit(0);
                    }
                }
                return Move.passMove;
            }
            MoveWithValue best = heap.get(0);
            for (MoveWithValue mv : heap) {
                if (mv.isBetterThan(best)) {
                    best = mv;
                }
            }
            return best.getMove();
        }

        private static class MoveWithValue {

            Move move;
            int value;

            MoveWithValue(Move setMove, int setValue) {
                move = setMove;
                value = setValue;
            }

            Move getMove() {
                return move;
            }

            int getValue() {
                return value;
            }

            private boolean isBetterThan(MoveWithValue best) {
                return (this.value > best.getValue());
            }

        }
    }

}
