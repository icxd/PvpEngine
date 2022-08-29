package me.icoding.pvpengine.runnables;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.api.Scoreboard;
import me.icoding.pvpengine.utils.ColorUtil;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.List;

public class ScoreboardRunnable {

    public ScoreboardRunnable() {
        if (ConfigUtil.getBoolean("scoreboard.enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        Scoreboard scoreboard = new Scoreboard(ConfigUtil.getFormattedString("scoreboard.title"), "scoreboard");

                        List<String> lines = PvpEngine.INSTANCE.getConfig().getStringList("scoreboard.lines");

                        NumberFormat format = NumberFormat.getInstance();
                        NumberFormat Cformat = NumberFormat.getCurrencyInstance();

                        for (String line : lines) {
                            scoreboard.add(ColorUtil.c(line
                                    .replace("%kills%", ""+format.format(PvpEngine.playerManager.getKills(player)))
                                    .replace("%deaths%", ""+format.format(PvpEngine.playerManager.getDeaths(player)))
                                    .replace("%kdr%", ""+kdr(PvpEngine.playerManager.getKills(player), PvpEngine.playerManager.getDeaths(player)))
                                    .replace("%rank%", ""+PvpEngine.playerManager.getRank(player).toUpperCase())
                                    .replace("%level%", ""+format.format(PvpEngine.playerManager.getLevel(player)))
                                    .replace("%level_color%", ""+PvpEngine.playerManager.getLevelColor(PvpEngine.playerManager.getLevel(player)))
                                    .replace("%coins%", ""+Cformat.format(PvpEngine.playerManager.getCoins(player)))
                            ));
                        }

                        scoreboard.apply(player);


                    }
                }
            }.runTaskTimer(PvpEngine.INSTANCE, 0, 20);
        }
    }

    public float kdr(int kills, int deaths) {
        if (kills == 0 && deaths != 0)
            return 0;
        else if (kills != 0 && deaths == 0)
            return 0;
        else if (kills == 0 && deaths == 0)
            return 0;
        else
            return kills / deaths;
    }

}
