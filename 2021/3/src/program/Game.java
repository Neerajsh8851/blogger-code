package program;

public class Game {

    private String[] board;
    private final String vacant = "   ";

    private int[][] lines = {
            {6, 7, 8},
            {3, 4, 5},
            {0, 1, 2},
            {6, 3, 0},
            {7, 4, 1},
            {8, 5, 2},
            {6, 4, 2},
            {8, 5, 0}
    };

    // default names of player
    private String p1;
    private String p2;

    private int spot;
    private boolean toggleX = true;
    private boolean finished = false;

    public Game(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;

        board = new String[9];
        for (int i = 0; i < board.length; i++) {
            board[i] = vacant;
        }
        drawBoard();
    }



    public void processGame(String cmd) {
        try {
            int num = Integer.parseInt(cmd) - 1;
            // If the user has chosen a vacant
            // spot then proceed further into
            // the below if block.
            if (board[num].equals(vacant)) {
                spot = num;
                update();
                drawBoard();
                check();
            }
        } catch (Exception e) {
            System.out.println("Error: not a valid input!");
        }
    }

    private void drawBoard() {
        System.out.println(
                board[6] + "|" + board[7] + "|" + board[8] + "\n" +
                        "-----------" + "\n" +
                        board[3] + "|" + board[4] + "|" + board[5] + "\n" +
                        "-----------" + "\n" +
                        board[0] + "|" + board[1] + "|" + board[2] + "\n"

        );
    }


    private void update() {
        if (toggleX) {
            board[spot] = " X ";
            System.out.println(p1 + " has put an X");
        }else {
            board[spot] = " O ";
            System.out.println(p2 + " has put an O");
        }
    }

    private void check() {
        // check for win
        for (int[] line : lines) {
            String ele1 = board[line[0]];
            String ele2 = board[line[1]];
            String ele3 = board[line[2]];

            if (ele1 == ele2 && ele2 == ele3 && ele1 != vacant) {

                String out1 = p1 + " has won.";
                String out2 = p2 + " has won.";
                System.out.println(toggleX ? out1 : out2);
                return;
            }
        }

        // check for draw
        // if we failed in finding a vacant in the board
        // surely! we can say there is a draw!
        for (String spot : board) {
            if (spot.equals(vacant)) {
                toggleX = !toggleX;
                return;
            }
        }

        // failed!
        System.out.println("Draw!!!");
    }


    public void setName(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isFinished() {
        return finished;
    }


}
