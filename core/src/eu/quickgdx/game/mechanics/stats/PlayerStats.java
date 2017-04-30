package eu.quickgdx.game.mechanics.stats;

/**
 * Created by chzellot on 30.04.17.
 */

public class PlayerStats {

    int[] playerStats;

    public PlayerStats(int nrPlayers) {
        playerStats = new int[nrPlayers];
        for (int i = 0; i < nrPlayers; i++) {
            playerStats[i] = 0;
        }
    }

    public void incrPlayer(int playnr) {
        playerStats[playnr - 1]++;
    }

    public int getWinner() {
        int winnerPoints = 0;
        int winner = -1;
        for (int i = 0; i < playerStats.length; i++) {
            if (playerStats[i] > winnerPoints) {
                winner = i;
            }
        }
        return winner + 1;
    }

    public int[] getPlayerStats() {
        return playerStats;
    }

}
