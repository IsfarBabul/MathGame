import java.util.Scanner;

public class MathGameUI {

    private MathGame game;
    private Scanner scan;

    public MathGameUI() {
        game = null; // initialized in setup()
        scan = new Scanner(System.in);
        setup();
        mainMenu();
    }

    // ------------ PRIVATE HELPER METHODS (internal use only) ------------
    private void setup() {
        // get player names
        System.out.println("Welcome to the Math Game!");
        System.out.print("Enter player 1's name: ");
        String p1Name = scan.nextLine();
        System.out.print("Enter player 2's name: ");
        String p2Name = scan.nextLine();
        System.out.print("Enter player 3's name: ");
        String p3Name = scan.nextLine();
        System.out.print("Enter player 4's name: ");
        String p4Name = scan.nextLine();

        // initialize Player objects and MathGame object
        Player p1 = new Player(p1Name);
        Player p2 = new Player(p2Name);
        Player p3 = new Player(p3Name);
        Player p4 = new Player(p4Name);
        game = new MathGame(p1, p2, p3, p4, scan);
    }

    private void mainMenu() {
        String playAgain = "y";
        while (playAgain.equals("y")) {
            game.resetGame(); // reset game
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("GAME HAS STARTED!");
            game.playRound(); // play one round of game
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("GAME OVER!");
            if (game.getWinner() != null) {  // check for null just to be safe (so we don't call a method on null)
                System.out.println("The winner was " + game.getWinner().getName());   // print winner's name
                System.out.println("with a score of " + game.getWinner().getScore()); // print winner's score
                if (game.getWinner().getWinStreak() > 1) {
                    System.out.println(game.getWinner().getName() + " has won " + game.getWinner().getWinStreak() + " games in a row!"); // print winner's win streak if any
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("Want to play another round? y/n: ");
            playAgain = scan.nextLine().toLowerCase(); // take input and covert to lowercase
        }
        System.out.println("Thanks for playing the Math Game! Goodbye!");
    }
}
