package gameplay;

import java.util.ArrayList;

/**
 * Reformatting and Rewriting to streamline and simplify
 * Holds methods for evaluating boards for best moves
 * @author pthompso
 * @version 2019-11-09
 */

public class Evaluation_orig {

    static final boolean evalPrint = false;
    static double evalCount = 0;
    private static final int PLY_START = 0; //starting value at ply=0
    private static final int MAXDEPTH = 8; // maximum depth of gametree to evaluate
    static final int TOTALMOVES = 30; //moves per player
    //just testing
    static StringBuilder path = new StringBuilder("Path = ");
    static int plyVal = 0;
    static int depth;

    /**
     * Evaluates a move, given the player and the board
     *
     * @param b
     * @param player
     * @return
     */
    static double evaluateBoardStateForPlayer(Board b, int player) {
        evalCount++;
        if (b.isGameOver()) {
            //TODO?
        }

        /* + + + + + + + CURRENT WORK + + + + + ++ + + ++ ++ + + + + + + + + + + + + */
        //TODO finish this
        //try 1: using Max Disk strategy
        return strategy_MaxDisk(b, player);
        /* + + + + + + + CURRENT WORK + + + + + ++ + + ++ ++ + + + + + + + + + + + + */

    }

    private static double strategy_MaxDisk(Board b, int player) {
        int playerPieces, oppPieces;
        double totalPieces, netPieces;
        playerPieces = BoardActor.countPiecesWithColor(player, b);
        oppPieces = BoardActor.countPiecesWithColor(-player, b);
        totalPieces = (double) playerPieces + oppPieces;
        netPieces = (playerPieces - oppPieces) / totalPieces;
        if (OthelloDriver.ISDEBUG) {
            System.out.println("C strategy_MaxDisk evaluation for player: " + player
                    + "\nC\tnetPieces= " + netPieces);
        }
        if (evalPrint) {
            b.printBoard();
            System.out.println("C ^^^^^^^ strategy_MaxDisk eval for " + player + " (" + netPieces + ") ^^^^^");
        }
        return netPieces;
    }

    private static int calculateDepth(int movesLeft) {
        double ratio = 0.618;
        double depth_div_moves = (plyVal/movesLeft);
        int depth = (int) (depth_div_moves/ratio);
        return (depth + 1);
    }
    /**
     * Used by Player to get best move uses alphaBeta/minimax
     *
     * @param b
     * @param movesList
     * @param player
     * @return
     */
//my code
    static Move ab_getMyMove(Board b, ArrayList<Move> movesList, int player) {
        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;
        evalCount = 0;
        System.out.println("C Thinking....");
        depth = MAXDEPTH;
        depth = calculateDepth(TOTALMOVES-plyVal); //maybe not -plyVal??
        while (!OthelloDriver.timeUP) {
//            return alphaBeta(b, movesList, PLY_START, player, alpha, beta, MAXDEPTH);
            MoveWithValue tmp = alphaBeta(b, PLY_START, player, alpha, beta, depth);
            System.out.println("C Evaluated to Depth: " + depth
                    + "\t (" + evalCount + " evaluations" + ")");
            return tmp.getMove();
        }
        return Move.passMove; //if timeUP :(
    }

    //rewriting alphaBeta to generate MovesList itself (and pass moves along with it?)
    static MoveWithValue alphaBeta(Board currentBoard,
            int ply, int player, double alpha, double beta, int maxDepth) {
//End of Recursion
        plyVal++;
//        path.append(plyVal + ": ");
//        System.out.println("C " + path.toString());
        System.out.println("C plyVal="+plyVal);
        if (ply >= maxDepth || OthelloDriver.timeUP) {
            //TODO revisit this after writing else{}!
            Move returnMove = new Move();
            MoveWithValue returnMove2 = new MoveWithValue(returnMove, 0);
            returnMove2.value = evaluateBoardStateForPlayer(currentBoard, player);
            return returnMove2;
        } //If not end of recursion:
        else {
            ArrayList<Move> ms = currentBoard.populateMovesForPlayer(player);
            //            if (moves.isEmpty()) moves.add(new Move()); //add pass move if empty
            if (ms.isEmpty()) {
                ms.add(Move.passMove);
            }
            ArrayList<MoveWithValue> mvs = convertToMVS(ms);
            //Move bestMove = moves.get(0);
            MoveWithValue bestMove = mvs.get(0);
            System.out.println("C " + mvs.size() + " moves to evaluate");
            for (MoveWithValue mv : mvs) {
                Board newBoard = currentBoard.copyBoard();
                BoardActor.updateBoardAfterMove(mv.getMove(), newBoard, player);
                //check if game is over?
                if (newBoard.isGameOver()) {
                    System.out.println("C game over: no more evaluations -- ply = " + ply);
                    int mypieces = BoardActor.countPiecesWithColor(player, newBoard);
                    //TODO finish this... if game is over, who won? and is it good or bad? do we keep this move or another one?

                    plyVal--;
                    return bestMove; //probably not
                }
                maxDepth = calculateDepth(TOTALMOVES - plyVal);
                MoveWithValue tempMove = alphaBeta(newBoard, ply + 1, -player, -beta, -alpha, maxDepth);
                mv.value = -1 * tempMove.value;
                double diff = Double.compare(mv.value, alpha);
                boolean moveGreaterThanAlpha = (diff > 0);
                if (moveGreaterThanAlpha) {
                    System.out.println("C PRUNED!! at depth: " + ply);
                    bestMove = mv;
                    alpha = mv.value;
                    diff = Double.compare(alpha, beta);
                    boolean alphaGreaterThanBeta = (diff > 0);
                    if (alphaGreaterThanBeta) {

                        plyVal--;
                        return bestMove;
                    }
                }
            }
            plyVal--;
            return bestMove;
//            return Move.commentMove; //TODO fix
        }
    }

    static ArrayList<MoveWithValue> convertToMVS(ArrayList<Move> moves) {
        ArrayList<MoveWithValue> outList = new ArrayList<>();
        for (Move m : moves) {
            outList.add(new MoveWithValue(m, 0));
        }
        return outList;
    }

    //OLD
    /**
     * performs alphaBeta pruning using minimax search algorithm
     *
     * @param currentBoard
     * @param movesList
     * @param ply
     * @param player
     * @param alpha
     * @param beta
     * @param maxDepth
     * @return the best move
     */
    static Move alphaBeta1(Board currentBoard, ArrayList<Move> movesList,
            int ply, int player, double alpha, double beta, int maxDepth) {

//DrMec Code + Mine
        if (ply >= maxDepth) { //end of the recursion
//            Move returnMove = new Move();
            MoveWithValue returnMove = new MoveWithValue();
//            returnMove.value = currentBoard.evaluate();
            returnMove.value = evaluateBoardStateForPlayer(currentBoard, player);
            return returnMove.getMove();
        } else {
// My Code
            //1. (skip)
            //2. if MoveList is empty, add passmove to movelist
            try {
                if (movesList.isEmpty()) {
                    movesList.add(Move.passMove);
                }
            } catch (NullPointerException e) {
                System.out.println("C NullPointerException " + e.getMessage());
            }
            //3.
            MoveWithValue bestMove, tempMove, mv;
            bestMove = new MoveWithValue(movesList.get(0), 0);
            //4. for each move in the MoveList
            for (Move m : movesList) {
                //convert Move to MoveWithValue
                mv = new MoveWithValue(m, 0);
                //4a.) newBoard = currentBoard.applyMove(player, move)
                Board newBoard = currentBoard.copyBoard();
                BoardActor.updateBoardAfterMove(m, newBoard, player);
                //4b.) tempMove = alphaBeta(newBoard, ply+1, -player, -beta, -alpha, maxDepth)
                tempMove = new MoveWithValue(m, 0);
                //alphaBeta(newBoard, movesList, ply+1, -player, -beta, -alpha, maxDepth),0);
                //4c.) move.value = -tempMove.value;
                mv.value = -tempMove.value;
                //4d.) if moveValue > alpha
                double diff = Double.compare(mv.value, alpha);
                if (diff > 0) {
                    //4d0) bestMove = move
                    bestMove = mv;
                    //4d1) alpha = moveValue
                    alpha = mv.value;
                    //4d2) if alpha > beta {return bestMove}
                    if (alpha > beta) {
                        return bestMove.getMove();
                    } // this is where the pruning happens
                }
            }
            //5.
            return bestMove.getMove();
        }

//My Code
//        return Move.passMove;
    }

    /**
     * don't use? private static Move alphaBeta(Evaluation aThis, int i, int i0,
     * double alpha, double beta, int i1) { return Move.passMove; // throw new
     * UnsupportedOperationException("Not supported yet."); //To change body of
     * generated methods, choose Tools | Templates. }
     *
     */
    private static class MoveWithValue {

        Move move;
        double value;

        MoveWithValue(Move setMove, double setValue) {
            move = setMove;
            value = setValue;
        }

        MoveWithValue() {

        }

        Move getMove() {
            return move;
        }

        double getValue() {
            return value;
        }

        private boolean isBetterThan(MoveWithValue best) {
            return (this.value > best.getValue());
        }

    } // end MoveWithValue class

} //end Evaluation class
