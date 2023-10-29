public class Player {

    private String name;
    private int score;

    private int winStreak;

    public Player(String name) {
        this.name = name;
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void incrementScore() {
        score++;
    }

    public void reset() {
        score = 0;
    }

    public void incrementWinStreak() {
        winStreak++;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void resetWinStreak() {
        winStreak = 0;
    }
}