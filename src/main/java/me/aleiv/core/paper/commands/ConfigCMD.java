package me.aleiv.core.paper.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import me.aleiv.core.paper.Core;

@CommandAlias("config")
@CommandPermission("admin.perm")
public class ConfigCMD extends BaseCommand {

    private @NonNull Core instance;

    public ConfigCMD(Core instance) {
        this.instance = instance;

    }

    @Subcommand("timer")
    public void setTimer(CommandSender sender, Integer seconds) {
        var game = instance.getGame();
        var timer = game.getTimer();

        timer.setPreStart(seconds);
        Bukkit.getScheduler().runTaskLater(instance, task -> {
            timer.start(seconds, (int) game.getGameTime());
        }, 20 * 1);
    }

    @Subcommand("pvp")
    public void pvp(CommandSender sender) {
        
    }

    @Subcommand("stage")
    public void stage(CommandSender sender) {
        
    }

    @Subcommand("slots")
    public void slots(CommandSender sender) {
        
    }

    @Subcommand("nether")
    public void nether(CommandSender sender) {
        
    }

    @Subcommand("border-size")
    public void borderSize(CommandSender sender) {
        
    }

    @Subcommand("border-time")
    public void borderTime(CommandSender sender) {
        
    }

    @Subcommand("border-center-time")
    public void borderCenterTime(CommandSender sender) {
        
    }

    @Subcommand("pvp-time")
    public void pvpTime(CommandSender sender) {
        
    }

    @Subcommand("beds")
    public void beds(CommandSender sender) {
        
    }

    @Subcommand("beds-nerf")
    public void bedsNerf(CommandSender sender) {
        
    }

    @Subcommand("strength")
    public void strength(CommandSender sender) {
        
    }

    @Subcommand("strength-nerf")
    public void strengthNerf(CommandSender sender) {
        
    }

    @Subcommand("tears")
    public void tears(CommandSender sender) {
        
    }

    @Subcommand("apple-rate")
    public void appleRate(CommandSender sender) {
        
    }

    @Subcommand("flint-rate")
    public void flintRate(CommandSender sender) {
        
    }

}
