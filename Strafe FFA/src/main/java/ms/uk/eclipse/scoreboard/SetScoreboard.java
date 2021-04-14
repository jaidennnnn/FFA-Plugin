package ms.uk.eclipse.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SetScoreboard {

    public void setScoreboard(Player player, int playercount, int kills, int deaths) {

        ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard board = sm.getNewScoreboard();
        Objective objective = board.registerNewObjective("abc", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§lStrafe§3§lFFA");
        objective.getScore("§m§l--------------").setScore(6);
        objective.getScore("§f§lKills:  §7§l" + kills).setScore(5);
        objective.getScore("§m§8               §3").setScore(4);
        objective.getScore("§f§lDeaths:  §7§l" + deaths).setScore(3);
        objective.getScore("§m§8               §2").setScore(2);
        objective.getScore("§m§l--------------§4").setScore(1);
        player.setScoreboard(board);
    }
}
