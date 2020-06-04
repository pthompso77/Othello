package gameplay;

import java.util.ArrayList;

/**
 * Holds methods for evaluating boards for best moves Cleaning up for final
 * submission
 *
 * @author pthompso
 * @version 2019-12-02
 */
public class Evaluation_orig {

    static final boolean evalPrint = false;
    static Move currentMoveBeingEvaluated;
    static int currentPlayerBeingEvaluated;
    static String[] playerColors = {"White", "0", "Black"};

    static String playerColor(int i) {
        return playerColors[i + 1];
    }

    static String playerColor() {
        return playerColors[currentPlayerBeingEvaluated + 1];
    }

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
        }
        double maxmoves =  strategy_MaxMoves(b, player);
        double maxdisk = strategy_MaxDisk(b,player);
        return maxmoves * maxdisk;
    }
    
        private static double strategy_MaxDisk(Board b, int player) {
        int playerPieces, oppPieces;
        double totalPieces, netPieces;
        playerPieces = BoardAnalyzer.countPiecesWithColor(player, b);
        oppPieces = BoardAnalyzer.countPiecesWithColor(-player, b);
        totalPieces = (double) playerPieces + oppPieces;
        netPieces = (playerPieces - oppPieces) / totalPieces;
        if (evalPrint) {
            b.printBoard();
            System.out.println("C ^^^^^^^ strategy_MaxDisk eval for " + player + " (" + netPieces + ") ^^^^^");
        }
        return netPieces;
    }    
        
        private static double strategy_MaxMoves(Board b, int player) {
        // TODO code this baby up
        int maxMoves = BoardAnalyzer.countBoardMovesForPlayer(b, player);
        if (evalPrint) {
            System.out.println("C ====== Evaluating Board for MaxMoves " + playerColor() + " " + currentMoveBeingEvaluated + " ======");
            b.printBoard();
            System.out.println("C ^^^^^^^ strategy_MaxMoves eval for player:" + playerColor() + " (" + maxMoves + ") ^^^^^");
        }
        return (double) maxMoves;
    }

    private static int calculateDepth() {
//        int movenum = (OthelloDriver.getMoveNumber()/2) + 1;
//        return (TOTALMOVES / 2) - Math.abs(TOTALMOVES / 2 - movenum)+1;
//        return (TOTALMOVES - movenum);
    return MAXDEPTH;
    }

    /**
     * Used by Player to get best move uses alphaBeta/minimax
     *
     * @param b
     * @param movesList
     * @param player
     * @return
     */
    static Move ab_getMyMove(Board b, ArrayList<Move> movesList, int player) {
        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;
        evalCount = 0;
//        depth = MAXDEPTH;
        depth = calculateDepth();
        System.out.println("C Evaluating to depth "+depth);
//        depth = calculateDepth(TOTALMOVES - plyVal); //maybe not -plyVal??
        while (!OthelloDriver.timeUP) {
//            return alphaBeta(b, movesList, PLY_START, player, alpha, beta, MAXDEPTH);
//NULL POINTER HERE
            MoveWithValue tmp = alphaBeta1(b, movesList, PLY_START, player, alpha, beta, depth);
            if (evalPrint) {
                System.out.println("C Evaluated to Depth: " + depth
                        + "\t (" + evalCount + " evaluations" + ")"
                + " and move value is "+tmp.value);
            }
            return tmp.getMove();
        }
        return Move.passMove; //if timeUP :(
    }

    static ArrayList<MoveWithValue> convertToMVS(ArrayList<Move> moves) {
        ArrayList<MoveWithValue> outList = new ArrayList<>();
        for (Move m : moves) {
            outList.add(new MoveWithValue(m, 0));
        }
        return outList;
    }

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
    static MoveWithValue alphaBeta1(Board currentBoard, ArrayList<Move> movesList,
            int ply, int player, double alpha, double beta, int maxDepth) {
        currentPlayerBeingEvaluated = player;
        if (ply >= maxDepth || OthelloDriver.timeUP) { //end of the recursion
            MoveWithValue returnMove = new MoveWithValue();
            if (movesList.isEmpty()) {
                returnMove.move = Move.passMove;
            } else {
                returnMove.move = movesList.get(0);
            }
//            returnMove.value = currentBoard.evaluate();
            returnMove.value = evaluateBoardStateForPlayer(currentBoard, player);
            if (evalPrint) {
                System.out.println("C !! alphaBeta1 is returning move (" + returnMove.move + ") value=" + returnMove.value);
            }
            return returnMove;
        } else {
            movesList = Board.populateMovesForPlayer1(currentBoard, player);
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
            printThisMovesList(movesList, player);
            //4. for each move in the MoveList
            for (Move m : movesList) {
                currentMoveBeingEvaluated = m;
//                System.out.println("watching movenumber " + movesList.indexOf(m));
                //convert Move to MoveWithValue
                mv = new MoveWithValue(m, 0);
                //4a.) newBoard = currentBoard.applyMove(player, move)
                Board newBoard = currentBoard.copyBoard();
                BoardActor.updateBoardAfterMove(m, newBoard, player);
                //4b.) tempMove = alphaBeta(newBoard, ply+1, -player, -beta, -alpha, maxDepth)
//                tempMove = new MoveWithValue(m, 0);
                ArrayList templist = new ArrayList<Move>();
                templist.addAll(movesList);
                templist.remove(m);
                tempMove = alphaBeta1(newBoard, templist, ply + 1, -player, -beta, -alpha, maxDepth);
                //4c.) move.value = -tempMove.value;
                mv.value = -tempMove.value;
                //4d.) if moveValue > alpha
                double diff = Double.compare(mv.value, alpha);
                boolean moveGreaterThanAlpha = (diff > 0);
                if (moveGreaterThanAlpha) {
                    if (evalPrint) {
                        System.out.println("C PRUNED!! at depth: " + ply);
                    }
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
            //5.
            return bestMove;
        }
    }

    private static void printThisMovesList(ArrayList<Move> movesList, int player) {
        StringBuilder sb = new StringBuilder("C Evaluating these Moves for player " + playerColor());
        for (Move m : movesList) {
            sb.append("(");
            sb.append(m);
            sb.append(") ");
        }
        if (evalPrint) {
            System.out.println(sb.toString());
        }
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
            // ?
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
