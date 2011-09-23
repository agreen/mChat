package net.D3GN.MiracleM4n.mChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.logging.Level;

public class MCommandSender implements CommandExecutor {

    mChat plugin;

    public MCommandSender(mChat plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();
        
        if (!cmd.equalsIgnoreCase("mchat"))
            return false;

        if(args.length != 2)
            return false;

        if(args[0].equalsIgnoreCase("reload"))
            if(args[1].equalsIgnoreCase("config")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (plugin.mAPI.checkPermissions(player, "mchat.reload")) {
                        plugin.cListener.checkConfig();
                        plugin.cListener.loadConfig();
                        sender.sendMessage(formatMessage("Config Reloaded."));
                    } else {
                        sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                    }

                    return true;
                } else {
                    plugin.cListener.checkConfig();
                    plugin.cListener.loadConfig();
                    plugin.console.log(Level.INFO, formatMessage("Config Reloaded."));

                    return true;
                }
            } else if (args[1].equalsIgnoreCase("info")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (plugin.mAPI.checkPermissions(player, "mchat.reload")) {
                        plugin.mIListener.checkConfig();
                        plugin.mIListener.loadConfig();
                        sender.sendMessage(formatMessage("Info Reloaded."));
                    } else {
                        sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                    }

                    return true;
                } else {
                    plugin.mIListener.checkConfig();
                    plugin.mIListener.loadConfig();
                    plugin.console.log(Level.INFO, formatMessage("Info Reloaded."));

                    return true;
                }
            } else if (args[1].equalsIgnoreCase("censor")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (plugin.mAPI.checkPermissions(player, "mchat.reload")) {
                        plugin.mCListener.loadConfig();
                        sender.sendMessage(formatMessage("Censor Reloaded."));
                    } else {
                        sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                    }

                    return true;
                } else {
                    plugin.mCListener.loadConfig();
                    plugin.console.log(Level.INFO, formatMessage("Censor Reloaded."));

                    return true;
                }
            } else if (args[1].equalsIgnoreCase("all")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (plugin.mAPI.checkPermissions(player, "mchat.reload")) {
                        plugin.mCListener.loadConfig();
                        plugin.cListener.checkConfig();
                        plugin.cListener.loadConfig();
                        plugin.mIListener.checkConfig();
                        plugin.mIListener.loadConfig();
                        sender.sendMessage(formatMessage("All Config's Reloaded."));
                    } else {
                        sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                    }

                    return true;
                } else {
                    plugin.mCListener.loadConfig();
                    plugin.cListener.checkConfig();
                    plugin.cListener.loadConfig();
                    plugin.mIListener.checkConfig();
                    plugin.mIListener.loadConfig();
                    plugin.console.log(Level.INFO, formatMessage("All Config's Reloaded."));

                    return true;
                }
            }

        return false;
    }

    private String formatMessage(String message) {
        PluginDescriptionFile pdfFile = plugin.getDescription();

        return (plugin.mAPI.addColour("&4[" + (pdfFile.getName()) + "] " + message));
    }
}
