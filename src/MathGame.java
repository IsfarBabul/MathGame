import java.util.Arrays;
import java.util.Scanner;

public class MathGame {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player currentPlayer;
    private Player winner;

    private Player streakWinner;
    private boolean gameOver;
    private Scanner scanner;

    private String mode;

    // create MathGame object
    public MathGame(Player player1, Player player2, Player player3, Player player4, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.scanner = scanner;
        currentPlayer = null; // will get assigned at start of game
        winner = null; // will get assigned when a Player wins
        streakWinner = null; // when someone gets their first win they are the streak winner; can be lost upon a loss
        gameOver = false;
    }

    // ------------ PUBLIC METHODS (to be used by client classes) ------------

    // returns winning Player; will be null if neither Player has won yet
    public Player getWinner() {
        return winner;
    }

    // plays a round of the math game
    public void playRound() {
        Player turnPlayer = chooseStartingPlayer();  // this helper method (shown below) sets currentPlayer to either player1 or player2
        int turnCount = 0;
        boolean deathMatch = false;
        boolean deathMatchHold = false;
        boolean threat = false;
        Player threateningPlayer = null;
        while (!gameOver && currentPlayer != null) {
            if (threat) {
                currentPlayer.setStatus("threatened");
                threat = false;
            }
            if (currentPlayer.equals(turnPlayer)) {
                turnCount++;
            }
            int modeCount = determineTurnMode(turnCount);
            printGameState();   // this helper method (shown below) prints the state of the Game
            System.out.println("Current player: " + currentPlayer.getName());
            boolean correct = askQuestion(modeCount);  // this helper method (shown below) asks a question and returns T or F
            if (correct) {
                System.out.println("Correct!");
                if (deathMatch && currentPlayer.getStatus().equals("threatened")) {
                    threateningPlayer.setStatus("eliminated");
                    gameOver = true;
                    winner = currentPlayer;
                    determineWinStreak();  //actual win
                } else if (currentPlayer.getStatus().equals("threatened")) {
                    currentPlayer.setStatus("safe");
                    threateningPlayer.setStatus("safe");
                }
                currentPlayer.incrementScore();  // this increments the currentPlayer's score
            } else {
                System.out.println("INCORRECT!");
                if (currentPlayer.getStatus().equals("safe")) {
                    threat = true;
                    threateningPlayer = currentPlayer;
                } else if (currentPlayer.getStatus().equals("threatened")) {
                    if (!deathMatch) {
                        currentPlayer.setStatus("eliminated");
                        threateningPlayer.setStatus("eliminated");
                        threateningPlayer = null;
                        deathMatchHold = true; /*needed so for the non-deathmatch swapPlayers to execute one more
                                                        time to mitigate any issues with ordering for example where
                                                         after the middle two players are eliminated the program
                                                         doesn't jump back to the first player without reaching the
                                                         fourth player */

                    } else {
                        currentPlayer.setStatus("safe");
                        threateningPlayer.setStatus("safe");
                    }
                }
            }
            if (!gameOver) {
                swapPlayers(deathMatch);  // this helper method (shown below) sets currentPlayer to the other Player
            }
            if (deathMatchHold)  {
                deathMatch = true;
                deathMatchHold = false;
            }
        }
    }

    // prints the current scores of the two players
    private void printGameState() {
        System.out.println("--------------------------------------");
        System.out.println("Current Mode: " + mode);
        System.out.println("Current Scores:");
        System.out.println(player1.getName() + ": " + player1.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
        System.out.println(player3.getName() + ": " + player3.getScore());
        System.out.println(player4.getName() + ": " + player4.getScore());
        System.out.println("Current Status:");
        System.out.println(player1.getName() + ": " + player1.getStatus());
        System.out.println(player2.getName() + ": " + player2.getStatus());
        System.out.println(player3.getName() + ": " + player3.getStatus());
        System.out.println(player4.getName() + ": " + player4.getStatus());
        System.out.println("--------------------------------------");
    }

    // resets the game back to its starting state
    public void resetGame() {
        player1.reset(); // this method resets the player
        player2.reset();
        player3.reset();
        player4.reset();
        player1.resetStatus(); // this method resets the player's status to safe
        player2.resetStatus();
        player3.resetStatus();
        player4.resetStatus();
        gameOver = false;
        currentPlayer = null;
        winner = null;
    }

    // ------------ PRIVATE HELPER METHODS (internal use only) ------------

    // randomly chooses one of the Player objects to be the currentPlayer
    private Player chooseStartingPlayer() {
        int randNum = (int) (Math.random() * 4) + 1;
        if (randNum == 1) {
            currentPlayer = player1;
        } else if (randNum == 2) {
            currentPlayer = player2;
        } else if (randNum == 3) {
            currentPlayer = player3;
        } else {
            currentPlayer = player4;
        }
        return currentPlayer;
    }

    private int determineTurnMode(int turnCount) {
        if (turnCount > 21) {
            mode = "Random";
            return (int) (Math.random() * 100000) + 1;
        } else if (turnCount > 15) {
            mode = "Challenging";
            return 10000;
        } else if (turnCount > 10) {
            mode = "Hard";
            return 1000;
        } else if (turnCount > 5) {
            mode = "Medium";
            return 100;
        } else {
            mode = "Easy";
            return 10;
        }
    }
    // asks a math question and returns true if the player answered correctly, false if not

    private boolean askQuestion(int modeCount) {
        int operation = (int) (Math.random() * 4) + 1;
        int num1 = (int) (Math.random() * modeCount) + 1;
        int num2 = (int) (Math.random() * modeCount) + 1;
        int correctAnswer;
        System.out.println("Type in your answer as an integer (/ is int division)");
        System.out.println("If you think your answer exceeds 2,147,483,647 then type 0.");
        if (operation == 1) {
            System.out.print(num1 + " + " + num2 + " = ");
            correctAnswer = num1 + num2;
        } else if (operation == 2) {
            System.out.print(num1 + " - " + num2 + " = ");
            correctAnswer = num1 - num2;
        } else if (operation == 3) {
            System.out.print(num1 + " * " + num2 + " = ");
            correctAnswer = num1 * num2;
        } else {  // option == 4
            System.out.print(num1 + " / " + num2 + " = ");
            correctAnswer = num1 / num2;
        }

        int playerAnswer = scanner.nextInt(); // get player's answer using Scanner
        scanner.nextLine(); // clear text buffer after numeric scanner input

        return playerAnswer == correctAnswer;
    }

    // swaps the currentPlayer to the other player
    private void swapPlayers(boolean deathMatch) {
        if(!deathMatch) {
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else if (currentPlayer == player2) {
                currentPlayer = player3;
            } else if (currentPlayer == player3) {
                currentPlayer = player4;
            } else {
                currentPlayer = player1;
            }
        } else {
            Player[] safePlayerArray = findSafePlayers();
            Player safePlayer1 = safePlayerArray[0];
            Player safePlayer2 = safePlayerArray[1];
            if (currentPlayer == safePlayer1) {
                currentPlayer = safePlayer2;
            } else {
                currentPlayer = safePlayer1;
            }
            System.out.println(currentPlayer.getName());
        }
    }

    private Player[] findSafePlayers() {
        Player safePlayer1 = null;
        Player safePlayer2 = null;
        if (!player1.getStatus().equals("eliminated")) {
            safePlayer1 = player1;
        }
        if (!player2.getStatus().equals("eliminated")) {
            if (safePlayer1 == null)  {
                safePlayer1 = player2;
            } else {
                safePlayer2 = player2;
            }
        }
        if (!player3.getStatus().equals("eliminated")) {
            if (safePlayer1 == null) {
                safePlayer1 = player3;
            } else {
                safePlayer2 = player3;
            }
        }
        if (safePlayer2 == null) {
            safePlayer2 = player4;
        }
        return new Player[]{safePlayer1, safePlayer2};
    }

    private void determineWinStreak() {
        if (streakWinner != null && streakWinner != winner) {
            streakWinner.resetWinStreak();
        }
        streakWinner = winner;
        winner.incrementWinStreak();
    }
}