package me.aleiv.core.paper.commands;

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

    @Subcommand("start")
    public void start(CommandSender sender) {
        
    }

    @Subcommand("reset")
    public void reset(CommandSender sender) {
        
    }

    @Subcommand("resume")
    public void resume(CommandSender sender) {
        
    }

    @Subcommand("pause")
    public void pause(CommandSender sender) {
        
    }

}
