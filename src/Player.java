public class Player {

    private String name;
    private int score;

    private int winStreak;

    private String status;

    public Player(String name) {
        this.name = name;
        score = 0;
        status = "safe";
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public void incrementScore() {
        score++;
    }

    public void reset() {
        score = 0;
    }

    public void resetStatus() {
        status = "safe";
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