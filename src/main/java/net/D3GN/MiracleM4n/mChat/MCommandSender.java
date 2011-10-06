package net.D3GN.MiracleM4n.mChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCommandSender implements CommandExecutor {

    mChat plugin;

    public MCommandSender(mChat plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();
        
        if (!cmd.equalsIgnoreCase("mchat"))
            return false;

        if (args[0].equalsIgnoreCase("reload")) {
            if (args[1].equalsIgnoreCase("config")) {
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
                    plugin.mAPI.log(formatMessage("Config Reloaded."));

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
                    plugin.mAPI.log(formatMessage("Info Reloaded."));

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
                    plugin.mAPI.log(formatMessage("Censor Reloaded."));

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
                    plugin.mAPI.log(formatMessage("All Config's Reloaded."));

                    return true;
                }
            }
        }
        /*
        if (args[1].equalsIgnoreCase("u")) {
            if (args[2].equalsIgnoreCase("a")) {
                if (args[3].equalsIgnoreCase("p")) {
                    try {
                        plugin.mIReader.addPlayer(args[4], args[5]);
                        return true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        plugin.mIReader.addPlayer(args[4], "default");
                        return true;
                    }
                } else if (args[3].equalsIgnoreCase("i")) {
                    if (args[4].equalsIgnoreCase("var")) {
                        try {
                        plugin.mIReader.addPlayerInfoVar(args[5], args[6], args[7]);
                        return true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        return true;
                        }
                    }
                }
            } else if (args[2].equalsIgnoreCase("e")) {
                if (args[3].equalsIgnoreCase("p")) {
                    try {
                        plugin.mIReader.editPlayerName(args[4], args[5]);
                        return true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return true;
                    }
                } else if (args[3].equalsIgnoreCase("i")) {
                    if (args[4].equalsIgnoreCase("var")) {
                        try {
                        plugin.mIReader.editPlayerInfoVar(args[5], args[6], args[7]);
                        return true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        return true;
                        }
                    } else if (args[4].equalsIgnoreCase("val")) {
                        try {
                        plugin.mIReader.editPlayerInfoValue(args[5], args[6], args[7]);
                        return true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        return true;
                        }
                    }
                }
            } else if (args[2].equalsIgnoreCase("r")) {
                if (args[3].equalsIgnoreCase("p")) {
                    try {
                        plugin.mIReader.removePlayer(args[4]);
                        return true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return true;
                    }
                } else if (args[3].equalsIgnoreCase("i")) {
                    if (args[4].equalsIgnoreCase("var")) {
                        try {
                        plugin.mIReader.removePlayerInfoVar(args[5], args[6]);
                        return true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                        return true;
                        }
                    }
                }
            }
        } else if (args[1].equalsIgnoreCase("g")) {
            if (args[2].equalsIgnoreCase("a")) {
                if (args[3].equalsIgnoreCase("")) {

                }
            }
        }
        */
        return false;
    }

    private String formatMessage(String message) {
        return (plugin.mAPI.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }
}
