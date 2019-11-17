/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interact;

import gameplay.Board;
import gameplay.Move;
import gameplay.VisualBoard;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author pthompso
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
     *
     * @param currentPlayer
     * @return
     */
    public static String getOpponentMove(int currentPlayer) {
        String player = intToText.get(currentPlayer);
        System.out.println("C Ready for your move (" + player + "): ");
        ask = new Scanner(System.in);
        String input = ask.nextLine();
//        System.out.println("C input: " + input);
        return input;
    }

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
//        System.out.println("C " + playerText
//                + " " + getColumnTextValue(moveLocation)
//                + " " + getRowTextValue(moveLocation));
// TODO is this right?
        System.out.println(playerText
                + " " + VisualBoard.getColumnTextValue(moveLocation)
                + " " + VisualBoard.getRowTextValue(moveLocation));
    }

    public static char getColorTextValue(int currentPlayer) {
        return intToText.get(currentPlayer).charAt(0);
    }

    /**
     * for testing (user input)
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
