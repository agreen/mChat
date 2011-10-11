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
        Player player = null;

        if (sender instanceof  Player)
            player = (Player) sender;

        String cmd = command.getName();

        if (!cmd.equalsIgnoreCase("mchat"))
            return false;

        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("reload")) {
            if (args[1].equalsIgnoreCase("config")) {
                if (sender instanceof Player) {
                    if (plugin.mAPI.checkPermissions(player, "mchat.reload.config")) {
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
                    if (plugin.mAPI.checkPermissions(player, "mchat.reload.info")) {
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
                    if (plugin.mAPI.checkPermissions(player, "mchat.reload.censor")) {
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
                    if (plugin.mAPI.checkPermissions(player, "mchat.reload.all")) {
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
        } else if (args[0].equalsIgnoreCase("u")
                || args[0].equalsIgnoreCase("user")) {
           if (args[1].equalsIgnoreCase("a")
            || args[1].equalsIgnoreCase("add")) {
               if (args[2].equalsIgnoreCase("p")
                || args[2].equalsIgnoreCase("player")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.add.player")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addPlayer(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user add player Player DefaultGroup"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVar")
                        || args[2].equalsIgnoreCase("infoVariable")) {
					if (sender instanceof Player)
                        if (!plugin.mAPI.checkPermissions(player, "mchat.user.add.ivar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addPlayerInfoVar(args[3], args[4], stringArgs(args, 5));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user add ivar Player InfoVariable InfoValue"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("w")
                        || args[2].equalsIgnoreCase("world")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.add.world")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addPlayerWorld(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user add world Player World"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVar")
                        || args[2].equalsIgnoreCase("worldVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.add.wvar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addPlayerWorldVar(args[3], args[4], args[5], stringArgs(args, 6));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user add world Player World InfoVariable InfoValue"));
                        return true;
                    }
                }
            } else if (args[1].equalsIgnoreCase("e")
                    || args[1].equalsIgnoreCase("edit")) {
               if (args[2].equalsIgnoreCase("n")
                || args[2].equalsIgnoreCase("name")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.name")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editPlayerName(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        sender.sendMessage(formatMessage("/mchat user edit name Player NewName"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVar")
                        || args[2].equalsIgnoreCase("infoVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.ivar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editPlayerInfoVar(args[3], args[4], args[5]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user edit ivar Player OldInfoVariable NewInfoVariable"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVal")
                        || args[2].equalsIgnoreCase("infoValue")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.ival")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editPlayerInfoValue(args[3], args[4], stringArgs(args, 5));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user edit ival Player InfoVariable NewValue"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("group")
                        || args[2].equalsIgnoreCase("g")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.group")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.setPlayerGroup(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user edit group Player Group"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("w")
                        || args[2].equalsIgnoreCase("world")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.world")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editPlayerWorldName(args[3], args[4], args[5]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user edit world Player OldWorld NewWorld"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVar")
                        || args[2].equalsIgnoreCase("worldVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.wvar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editPlayerWorldVar(args[3], args[4], args[5], args[6]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user edit wvar Player World OldInfoVariable NewInfoVariable"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVal")
                        || args[2].equalsIgnoreCase("worldValue")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.edit.wval")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editPlayerWorldValue(args[3], args[4], args[5], stringArgs(args, 6));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user edit wval Player World InfoVariable NewValue"));
                        return true;
                    }
                }
            } else if (args[1].equalsIgnoreCase("r")
                    || args[1].equalsIgnoreCase("remove")) {
               if (args[2].equalsIgnoreCase("p")
                || args[2].equalsIgnoreCase("player")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.remove.player")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removePlayer(args[3]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user remove Player"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVar")
                        || args[2].equalsIgnoreCase("infoVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.remove.ivar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removePlayerInfoVar(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user remove Player InfoVariable"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("w")
                        || args[2].equalsIgnoreCase("world")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.remove.world")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removePlayerWorld(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user remove Player World"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVar")
                        || args[2].equalsIgnoreCase("worldVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.user.remove.wvar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removePlayerWorldVar(args[3], args[4], args[5]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat user remove Player World InfoVariable"));
                        return true;
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("g")
                || args[0].equalsIgnoreCase("group")) {
           if (args[1].equalsIgnoreCase("a")
            || args[1].equalsIgnoreCase("add")) {
               if (args[2].equalsIgnoreCase("g")
                || args[2].equalsIgnoreCase("group")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.add.group")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addGroup(args[3]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group add group Group"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVar")
                        || args[2].equalsIgnoreCase("infoVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.add.ivar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addGroupInfoVar(args[3], args[4], stringArgs(args, 5));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group add ivar Group InfoVariable InfoValue"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("w")
                        || args[2].equalsIgnoreCase("world")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.add.world")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addGroupWorld(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group add world Group World"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVar")
                        || args[2].equalsIgnoreCase("worldVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.add.wvar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.addGroupWorldVar(args[3], args[4], args[5], stringArgs(args, 6));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group add world Group World InfoVariable InfoValue"));
                        return true;
                    }
                }
            } else if (args[1].equalsIgnoreCase("e")
                    || args[1].equalsIgnoreCase("edit")) {
               if (args[2].equalsIgnoreCase("n")
                || args[2].equalsIgnoreCase("name")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.edit.name")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editGroupName(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group edit name Group NewName"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVar")
                        || args[2].equalsIgnoreCase("infoVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.edit.ivar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editGroupInfoVar(args[3], args[4], args[5]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group edit ivar Group OldInfoVariable NewInfoVariable"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVal")
                        || args[2].equalsIgnoreCase("infoValue")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.edit.ival")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editGroupInfoValue(args[3], args[4], stringArgs(args, 5));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group edit ival Group InfoVariable NewValue"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("w")
                        || args[2].equalsIgnoreCase("world")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.edit.world")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editGroupWorldName(args[3], args[4], args[5]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group edit world Group OldWorld NewWorld"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVar")
                        || args[2].equalsIgnoreCase("worldVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.edit.wvar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editGroupWorldVar(args[3], args[4], args[5], args[6]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group edit wvar Group World OldInfoVariable NewInfoVariable"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVal")
                        || args[2].equalsIgnoreCase("worldValue")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.edit.wval")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.editGroupWorldValue(args[3], args[4], args[5], stringArgs(args, 6));
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group edit wval Group World InfoVariable NewValue"));
                        return true;
                    }
                }
            } else if (args[1].equalsIgnoreCase("r")
                    || args[1].equalsIgnoreCase("remove")) {
               if (args[2].equalsIgnoreCase("g")
                || args[2].equalsIgnoreCase("group")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.remove.group")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removeGroup(args[3]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group remove Group"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("iVar")
                        || args[2].equalsIgnoreCase("infoVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.remove.ivar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removeGroupInfoVar(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group remove Group InfoVariable"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("w")
                        || args[2].equalsIgnoreCase("world")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.remove.world")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removeGroupWorld(args[3], args[4]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group remove Group World"));
                        return true;
                    }
                } else if (args[2].equalsIgnoreCase("wVar")
                        || args[2].equalsIgnoreCase("worldVariable")) {
					if (sender instanceof Player)
						if (!plugin.mAPI.checkPermissions(player, "mchat.group.remove.wvar")) {
                            sender.sendMessage(formatMessage("You don't have Permission to do that."));
                            return true;
                        }
                    try {
                        plugin.mIReader.removeGroupWorldVar(args[3], args[4], args[5]);
                        sender.sendMessage(formatMessage("Info Addition Successful."));
                        return true;
                    } catch (ArrayIndexOutOfBoundsException er) {
                        sender.sendMessage(formatMessage("/mchat group remove Group World InfoVariable"));
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private String formatMessage(String message) {
        return (plugin.mAPI.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    private String stringArgs (String[] args, Integer startingPoint)  {
        String argString = "";

        for (int i = startingPoint; i < args.length; ++i) {
            if (i == args.length - 1) {
                argString += args[i];
            } else
                argString += args[i] + " ";
        }

        return argString;
    }
}
