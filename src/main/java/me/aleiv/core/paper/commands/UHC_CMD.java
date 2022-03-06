package me.aleiv.core.paper.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import me.aleiv.core.paper.Core;

@CommandAlias("uhc")
@CommandPermission("admin.perm")
public class UHC_CMD extends BaseCommand {

    private @NonNull Core instance;

    public UHC_CMD(Core instance) {
        this.instance = instance;

    }

    @Subcommand("timer")
    public void setTimer(CommandSender sender, Integer seconds) {
        var game = instance.getGame();
        var timer = game.getTimer();

        timer.setPreStart(seconds);
        Bukkit.getScheduler().runTaskLater(instance, task -> {
            timer.start(seconds, (int) game.getGameTime());
        }, 20 * 5);
    }

}
