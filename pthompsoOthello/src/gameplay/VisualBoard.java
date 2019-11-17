
package gameplay;

/**
 * Reformatting and Rewriting to streamline and simplify
 * @author pthompso
 * @version 2019-11-09
 */
public class VisualBoard {

    private static final String SEP = " ";
    public static final char[] COLUMN_LABELS = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    //private static final int[] ROW_LABELS = { 1, 2, 3, 4, 5, 6, 7, 8 };
    private static StringBuilder sb = new StringBuilder();
    static final char EMPTY_VISUAL = '-';
    public static final char WHITE_VISUAL = 'W';

    public static final char BLACK_VISUAL = 'B';
    static final char[] VALUE_VISUAL_ASSOC
            = {'*', WHITE_VISUAL, EMPTY_VISUAL, BLACK_VISUAL};

    private static void printArrayBoard(int[] board) {
        StringBuilder sb = new StringBuilder();
        //TODO start every row with "C"
        sb.append("C").append(SEP).append(SEP).append(SEP);
        for (char c : COLUMN_LABELS) {
            sb.append(c)
                    .append(SEP);
        }
        sb.append("\nC ");

        // keep track of rows
        int rowcounter = 0; //start at zero (for top border)
        for (int i = 0; i < 100; i += 10) {
            int j = (i + 1);
            if (rowcounter > 0 && rowcounter < 9) {
                sb.append(rowcounter);
            } else {
                sb.append(" ");
            }
            rowcounter++;
            sb.append(SEP)
                    .append(VALUE_VISUAL_ASSOC[board[i] + 2])
                    .append(SEP);
            for (; j < (i + 9); j++) {
                sb.append(VALUE_VISUAL_ASSOC[board[j] + 2]).append(SEP);
            }
            sb.append(VALUE_VISUAL_ASSOC[board[j++] + 2]).append("\nC ");
        }

        System.out.println(sb.toString());
    }

    /**
     * See a human-readable text of the board location example: "a 2"
     *
     * @param boardLocation the location on the board to be viewed
     * @return String value in column/row form
     */
    public static String seeColRowFromLocation(int boardLocation) {
        return getColumnTextValue(boardLocation)
                + " " + getRowTextValue(boardLocation);
    }

    /* ------ Overload for Board object ------ */
    public static void printBoard(Board gameboard) {
        printArrayBoard(gameboard.getBoard());
    }

    public static char getColumnTextValue(int moveLocation) {
        try {
            return COLUMN_LABELS[moveLocation % 10];
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("C Exception in VB.getColumnTextValue: moveLocation=" + moveLocation
                    + "\nC"+ex.getMessage());
            return (moveLocation == -1) ? 'P' : '!';
        }
    }

    public static String getRowTextValue(int moveLocation) {
        int rowLoc = moveLocation / 10;
        return Integer.toString(rowLoc);
    }

    //    // for testing only
    //    public static void main(String[] args) {
    //        System.out.println("C IO.main: TESTING");
    //
    //        System.out.println("C " + getRowTextValue(88));
    //    }
}
