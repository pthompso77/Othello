
package interact;

import gameplay.Board;
import gameplay.Move;
import gameplay.VisualBoard;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Cleaning up for final submission
 * @author pthompso
 * @version 2019-12-02
 */
public class IO {

    private static Scanner ask;
    public static HashMap<Integer, String> intToText = new HashMap<>();

    static {
        intToText.put(Board.BLACKINT, "" + VisualBoard.BLACK_VISUAL);
        intToText.put(Board.WHITEINT, "" + VisualBoard.WHITE_VISUAL);
    }

    public static String playerInt_txt(int p) {
        return intToText.get(p);
    }

    /**
     * Asks for and receives instruction from opponent player for the next move
     * @param currentPlayer
     * @return everything the opponent typed
     */
    public static String getOpponentMove(int currentPlayer) {
        String player = intToText.get(currentPlayer);
        System.out.println("C Ready for your move (" + player + "): ");
        ask = new Scanner(System.in);
        String input = ask.nextLine();
        return input;
    }

    /**
     * Ask Standard Input for which color to initialize as (B or W)
     * prints R[B/W] when ready to play
     * @return int representation of the color indicated by the input
     */
    public static int getCurrentPlayer() {
        ask = new Scanner(System.in);
        char playerToInitialize;
        System.out.println("C Please initiate color (I W or I B):");
        playerToInitialize = ask.nextLine().toUpperCase().charAt(2);
        int player;
        if (playerToInitialize == VisualBoard.WHITE_VISUAL) {
            player = Board.WHITEINT;
        } else {
            player = Board.BLACKINT;
        }
        System.out.println("R " + playerToInitialize);
        return player;
    }

    /**
     * Prints a human-readable text of the current move and the player it
     * belongs to example: W c 5
     *
     * @param currentPlayer
     * @param currentMove
     */
    public static void printMoveOutput(int currentPlayer, Move currentMove) {
        char playerText = getColorTextValue(currentPlayer);
        if (currentMove.isPass()) {
            System.out.println("C " + playerText + " passes");
            String playerPassMessage = intToText.get(currentPlayer);
            System.out.println(playerPassMessage);
            return;
        }
        if (currentMove.isComment()) {
            return;
        }
        int moveLocation = currentMove.getMovePosition();
        System.out.println(playerText
                + " " + VisualBoard.getColumnTextValue(moveLocation)
                + " " + VisualBoard.getRowTextValue(moveLocation));
    }

    /**
     * Converts integer value to value of player's color
     * @param currentPlayer integer value for the player
     * @return the character representing the color (B or W)
     */
    public static char getColorTextValue(int currentPlayer) {
        return intToText.get(currentPlayer).charAt(0);
    }

    /**
     * for testing (getting Y/N user input)
     *
     * @return true if Yes, false if No
     */
    public static boolean getYesFromUser(String message) {
        System.out.print(message + " -- Y or N:");
        String input = ask.nextLine();
        input = input.substring(0, 1);
        return (input.equalsIgnoreCase("Y"));
    }

}
