package gameplay;

import interact.IO;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Reformatting and Rewriting to streamline and simplify
 *
 * @author pthompso
 * @version 2019-11-09
 */
public class OthelloDriver {

    private static int currentPlayer;
    private static int MYVALUE;
    private static int OPPONENTVALUE; //to keep track of who is whom
    public static final boolean ISDEBUG = false;     //only change this here
    static int moveNumber = 0; //to keep track of the number of moves

//begin Timer
    public int timeRemaining = 90; //declare a variable to keep track of remaining time ..... initialize at beginning of game
    static int timeForMove;
    static Timer timer; //use this to start the interupt task
    public static boolean timeUP;  //boolean flag to check frequently for time up....this is set to true in the 
    //interrupt task
//end Timer

    OthelloDriver() {
    } //empty constructor

    public static void main(String[] args) {
        OthelloDriver driver = new OthelloDriver();
        driver.playGame();
    }

    void playGame() {
        Board gameboard = new Board();
        currentPlayer = Board.getBlackInt();
        //prints: Please initiate color (I W or I B)
        MYVALUE = IO.getCurrentPlayer(); //Please initiate color (I W or I B)
        OPPONENTVALUE = (-1 * MYVALUE);
        gameboard.printBoard();
        while (!gameboard.isGameOver()) {
            try {
                System.out.println("C\nC\nC\tMove number " + moveNumber + "\nC"
                        + "Player is " + VisualBoard.VALUE_VISUAL_ASSOC[currentPlayer + 2]
                        + "\nC");
                //currentMove ends up null!
                Move currentMove = getMoveFromPlayer(gameboard, currentPlayer);
                if (currentPlayer == MYVALUE) {
                    //print my move to std.out
                    IO.printMoveOutput(currentPlayer, currentMove);
                }
//            // Update the board
                BoardActor.updateBoardAfterMove(currentMove, gameboard, currentPlayer);
//timer code
                if (!timeUP) {
                    timer.cancel();
                }
                timeRemaining -= timeForMove;  //update the time remaining 
                System.out.println("C Remaining Time: " + timeRemaining + ")");
//my code
                System.out.println("C ====== Board after last move " + currentMove + " ======");
                gameboard.printBoard();
                currentPlayer *= -1;
            } catch (Exception e) {
                System.out.println("C Exception: " + e.getMessage());
            }
        }
    }

    private /*static*/ Move getMoveFromPlayer(Board gameboard, int player) {
//Timer Code 
        moveNumber++;
        timeUP = false;  //time up is false at the beginning of move
        timer = new Timer();  //initialize the new timer
        //compute in seconds the amount of time for move
//back to my code
        Move nextMove = getMoveFromPlayer_orig(gameboard, player);
//timer code
        timeForMove = (int) (timeAllocation[moveNumber] * (double) timeRemaining);
        System.out.println("C Move Time:  " + timeForMove + ")");
        timer.schedule(new InterruptTask(), timeForMove * 1000);  //schedule the  interrupt task
//my code
        return nextMove;
    }

    private /*static*/ Move getMoveFromPlayer_orig(Board gameboard, int player) {
        if (ISDEBUG) {
            System.out.println("C getMoveFromPlayer() has: player=" + player + " and MYVALUE=" + MYVALUE);
        }
        if (player == MYVALUE) {
            // "computer" makes a move
            Move m = gameboard.getNextMove(MYVALUE, true);

            //TODO SOMETHING BREAKS RIGHT HERE
            if (ISDEBUG) {
                System.out.println("C getNextMove(mine) is " + m);
            }
            return m;
        } else {
            // opponent makes a move
            Move o = gameboard.getNextMove(OPPONENTVALUE, false);
            if (ISDEBUG) {
                System.out.println("C getNextMove(other) is " + o);
            }
            return o;
        }
    }

//time array.....each position represents a percentage of the remaining time to be used
    static double timeAllocation[] = {0.015, 0.015, 0.015, 0.015, 0.025, 0.025, 0.025, 0.025, 0.025, 0.025,
        0.048, 0.048, 0.048, 0.048, 0.048, 0.048, 0.050, 0.051, 0.052, 0.053,
        0.044, 0.045, 0.049, 0.049, 0.049, 0.051, 0.053, 0.055, 0.057, 0.059,
        0.060, 0.060, 0.061, 0.062, 0.063, 0.064, 0.065, 0.065, 0.065, 0.065,
        0.167, 0.168, 0.169, 0.169, 0.171, 0.172, 0.173, 0.175, 0.180, 0.180,
        0.181, 0.187, 0.196, 0.199, 0.220, 0.220, 0.220, 0.220, 0.220, 0.220,
        0.220, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250
    };

//define the interrupt task
    class InterruptTask extends TimerTask {

        public void run() {
            System.out.println("C ****>timeup)");
            timeUP = true;
            timer.cancel();
        }
    }
}
//}
//}
