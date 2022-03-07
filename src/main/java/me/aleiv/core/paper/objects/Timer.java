package me.aleiv.core.paper.objects;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import lombok.Getter;
import lombok.Setter;
import me.aleiv.core.paper.Core;

public class Timer {

    Core instance;

    int startTime;
    int seconds;

    @Getter
    BossBar bossBar;

    String time = "";

    @Setter
    @Getter
    boolean isActive = false;

    int currentClock = 0;

    public Timer(Core instance, int currentTime) {
        this.instance = instance;
        this.seconds = 0;
        this.startTime = (int) currentTime;
        this.bossBar = Bukkit.createBossBar(new NamespacedKey(instance, "TIMER"), "", BarColor.WHITE, BarStyle.SOLID);
        bossBar.setVisible(false);

    }

    private static String timeConvert(int t) {
        int hours = t / 3600;

        int minutes = (t % 3600) / 60;
        int seconds = t % 60;

        return (hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds)
                : String.format("%02d:%02d", minutes, seconds));
    }

    public int getTime(int currentTime) {
        return (startTime + seconds) - currentTime;
    }

    public void setPreStart(int time) {
        this.time = timeConvert(time);
        this.getBossBar().setVisible(true);
        bossBar.setTitle(this.time);
    }

    public void refreshTime(int currentTime) {
        var time = (startTime + seconds) - currentTime;

        if (time < 0) {
            this.time = "00:00";

        } else {
            this.time = timeConvert((int) time);
            bossBar.setTitle(this.time);
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), "squid:sfx.tic", 1, 1);
            });

        }

        if (time < -1) {
            delete();
            setActive(false);
        }

    }

    public void delete() {
        bossBar.setVisible(false);

    }

    public void start(int seconds, int startTime) {
        this.time = timeConvert(seconds);
        this.seconds = seconds;
        this.startTime = (int) instance.getGame().getGameTime();
        this.isActive = true;
        bossBar.setVisible(true);
    }

}
