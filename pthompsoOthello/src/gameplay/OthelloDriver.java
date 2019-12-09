package gameplay;

import interact.IO;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Cleaning up for final submission
 *
 * @author pthompso
 * @version 2019-12-02
 */
public class OthelloDriver {

    private static int currentPlayer; //swaps with every turn change
    private static int MYVALUE;
    private static int OPPONENTVALUE; //to keep track of who is whom
    public static final boolean ISDEBUG = false;     //only change this here
    static int moveNumber = 0; //to keep track of the number of moves
    public static int getMoveNumber() {return moveNumber;}

//begin Timer
    public int timeRemaining = 90000; //declare a variable to keep track of remaining time ..... initialize at beginning of game
    static int timeForMove;
    static Timer timer; //use this to start the interupt task
    public static boolean timeUP;  //boolean flag to check frequently for time up....this is set to true in the 
//end Timer

    OthelloDriver() {
    } //empty constructor

    /**
     * Main Gameplay constructs an OthelloDriver that calls the main playGame
     * function
     *
     * @param args
     */
    public static void main(String[] args) {
        OthelloDriver driver = new OthelloDriver();
        driver.playGame(args);
    }

    void playGame(String[] args) {
        Board gameboard = new Board();
        currentPlayer = Board.getBlackInt();
        //prints: Please initiate color (I W or I B)
        if (args.length == 0) {
            MYVALUE = IO.getCurrentPlayer(); //Please initiate color (I W or I B)
        } else MYVALUE = 1;
        OPPONENTVALUE = (-1 * MYVALUE);
        gameboard.printBoard();
        while (!gameboard.isGameOver()) {
            try {
                System.out.println("C\nC\nC\tMove number " + moveNumber + "\nC"
                        + "Player is " + VisualBoard.VALUE_VISUAL_ASSOC[currentPlayer + 2]
                        + "\nC");
                Move currentMove = getMoveFromPlayer(gameboard, currentPlayer);
                if (currentPlayer == MYVALUE) {
                    //print my move to std.out
                    IO.printMoveOutput(currentPlayer, currentMove);
                }
                //Update the board
                BoardActor.updateBoardAfterMove(currentMove, gameboard, currentPlayer);
                System.out.println("C ====== Board after last move: " + currentMove + " ======");
                gameboard.printBoard();
                currentPlayer *= -1; // swap players
            } catch (Exception e) {
                System.out.println("C playGame Exception: " + e.getMessage()+"\n"+e.getCause());
                try {
                    timer.cancel(); //maybe?
                } catch (Exception e2) {
                    System.out.println("C playGame timer Exception: " + e2.getMessage());
                }
            }
        }
    }

    private Move getMoveFromPlayer(Board gameboard, int player) {
//Timer Code 
        moveNumber++;
        timeUP = false;  //time up is false at the beginning of move
        timer = new Timer();  //initialize the new timer
//timer code
        //compute in seconds the amount of time for move
        timeForMove = (int) (timeAllocation[moveNumber] * (double) timeRemaining);
        System.out.println("C Move Time:  " + timeForMove + ")");
        timer.schedule(new InterruptTask(), timeForMove * 1000);  //schedule the  interrupt task
//back to my code
        Move nextMove = getMoveFromPlayer_orig(gameboard, player);
//timer code
        if (!timeUP) {
            timer.cancel();
        }
        timeRemaining -= timeForMove;  //update the time remaining 
        System.out.println("C Remaining Time: " + timeRemaining + ")");
//my code
        return nextMove;
    }

    private Move getMoveFromPlayer_orig(Board gameboard, int player) {
        boolean isMe = (player == MYVALUE);
        if (isMe) { // "computer" makes a move            
            return gameboard.getNextMove(MYVALUE, isMe);
        } else { // opponent makes a move            
            return gameboard.getNextMove(OPPONENTVALUE, isMe);
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

        @Override
        public void run() {
            System.out.println("C ****>timeup)");
            timeUP = true;
            timer.cancel();
        }
    }
}
