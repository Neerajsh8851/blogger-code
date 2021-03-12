package program;

import java.util.Scanner;

public class TTT {

    private final Scanner scanner = new Scanner(System.in);
    private Game game;

    // default names of player
    private String p1 = "Player 1";
    private String p2 = "Player 2";

    public void run() {
        showMSG();
        processUserCMD();
    }



    private void rename() {
        System.out.println("Enter first player name: ");
        p1 = scanner.nextLine();
        System.out.println("Enter second player name: ");
        p2 = scanner.nextLine();
        System.out.println("Done!");

        if (game != null) {
            game.setName(p1, p2);
        }
    }

    private void showMSG() {
        System.out.println(
                "CMD: \n rename - to rename the players name" +
                        "\n start - to start the game" +
                        "\n exit - to exit the game\n\n" +
                        "First player name: " + p1 + "\n" +
                        "Second player name: " + p2
        );
    }

    private void processUserCMD(String cmd) {
        switch (cmd) {
            case "start":
                start(); break;
            case "restart":
                restart(); break;
            case "rename":
                rename(); break;
            case "exit":
                System.out.println("Exited from the game!");
                System.exit(0);
            default:
                if (game != null) {
                    game.processGame(cmd);
                    if (game.isFinished()) {
                        game = null;
                    }
                }

        }
    }

    private void processUserCMD() {
        while (true) {
            String cmd = scanner.nextLine();
            // remove leading and trailing spaces, if any exists.
            cmd = cmd.trim();
            processUserCMD(cmd);
        }
    }

    private void start() {
        if (game == null) {
            restart();
        }
    }

    private void restart() {
        game = new Game(p1, p2);
    }


    public static void main(String[] args) {
        new TTT().run();
    }
}
