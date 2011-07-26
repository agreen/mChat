package net.D3GN.MiracleM4n.mChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class commandSender implements CommandExecutor {
	mChat plugin;
	
	public commandSender(mChat plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		if (label.equalsIgnoreCase("mchat")) {
			if(args.length == 0) {
				return false;
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (plugin.bukkitPermission) {
							 if (player.hasPermission("mchat.reload")) {
								plugin.checkConfig();
								plugin.loadConfig();
								sender.sendMessage("[" + (pdfFile.getName()) + "]" + " Config Reloaded.");
								return true;
							 } else {
								sender.sendMessage("[" + (pdfFile.getName()) + "]" + " You are not allowed to reload mChat.");
								return true;
							 }
						} else if (plugin.oldPerm) {
							if(mChat.permissions.has(player, ("mchat.reload"))) {
								plugin.checkConfig();
								plugin.loadConfig();
								sender.sendMessage("[" + (pdfFile.getName()) + "]" + " Config Reloaded.");
								return true;
							} else {
								sender.sendMessage("[" + (pdfFile.getName()) + "]" + " You are not allowed to reload mChat.");
								return true;
							}
						} else if (player.isOp()) {
							plugin.checkConfig();
							plugin.loadConfig();
							sender.sendMessage("[" + (pdfFile.getName()) + "]" + " Config Reloaded.");
							return true;
						} else {
							sender.sendMessage("[" + (pdfFile.getName()) + "]" + " You are not allowed to reload mChat.");
							return true;
						}
					} else {
						plugin.checkConfig();
						plugin.loadConfig();
						plugin.console.sendMessage("[" + (pdfFile.getName()) + "]" + " Config Reloaded.");
						return true;
					}
				}
			}
			return false;
    	}
		return false;
	}
}