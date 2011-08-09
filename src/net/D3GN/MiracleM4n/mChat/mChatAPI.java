package net.D3GN.MiracleM4n.mChat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

public class mChatAPI {
    mChat plugin;
    
    public mChatAPI(mChat plugin) {
        this.plugin = plugin;
    }

    public String replaceVars(String format, String[] search, String[] replace) {
        if (search.length != replace.length) {
            return "";
        }
        for (int i = 0; i < search.length; i++) {
            if (search[i].contains(",")) {
                for (String s : search[i].split(",")) {
                    if (s == null || replace[i] == null) {
                        continue;
                    }
                    format = format.replace(s, replace[i]);
                }
            } else {
                format = format.replace(search[i], replace[i]);
            }
        }
        return addColour(format);
    }

    public String parseChat(Player player, String msg, String formatAll) {
        String prefix = getRawPrefix(player);
        String suffix = getRawSuffix(player);
        String group = getRawGroup(player);
        
        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";
        if (group == null) group = "";
        
        Integer locX = (int) player.getLocation().getX();
        Integer locY = (int) player.getLocation().getY();
        Integer locZ = (int) player.getLocation().getZ();
        
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);
        String healthbar = healthBar(player);
        String health = String.valueOf(player.getHealth());
        String world = player.getWorld().getName();
        if (world.contains("_nether")) world.replace("_nether", " Nether");
        
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.dateFormat);
        String time = dateFormat.format(now);
        String format = formatAll;
        String[] search;
        String[] replace;
        
        if (msg == "") {
            search = new String[]{"+suffix,+s", "+prefix,+p", "+group,+g", "+world,+w", "+time,+t", "+name,+n", "+displayname,+dname,+dn", "+healthbar,+hb", "+health,+h", "+location,+loc"};
            replace = new String[]{suffix, prefix, group, world, time, player.getName(), player.getDisplayName(), healthbar, health, loc};
        } else {
            msg = msg.replaceAll("%", "%%");
            
            if (format == null) return msg;
            
            search = new String[]{"+suffix,+s", "+prefix,+p", "+group,+g", "+world,+w", "+time,+t", "+name,+n", "+displayname,+dname,+dn", "+healthbar,+hb", "+health,+h", "+location,+loc", "+message,+msg,+m"};
            replace = new String[]{suffix, prefix, group, world, time, player.getName(), player.getDisplayName(), healthbar, health, loc, msg};
        }
        
        return replaceVars(format, search, replace);
    }

    public String healthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();
        int fill = Math.round((health / maxHealth) * barLength);
        
        String barColor = (fill <= 4) ? "&4" : (fill <= 7) ? "&e" : "&2";
        
        StringBuilder out = new StringBuilder();
        out.append(barColor);
        
        for (int i = 0; i < barLength; i++) {
            if (i == fill) {
                out.append("&8");
            }
            out.append("|");
        }
        
        out.append("&f");
        
        return out.toString();
    }

    public String parseChat(Player player, String msg) {
        return parseChat(player, msg, plugin.chatFormat);
    }

    public String addColour(String string) {
        return string.replaceAll("(&([A-Fa-f0-9]))", "\u00A7$2");
    }

    public Boolean checkPermissions(Player player, String node) {
        if (plugin.permissionsB) {
            if (mChat.permissions.has(player, node)) {
                return true;
            }
        } else if (plugin.gmPermissionsB) {
            if (mChat.gmPermissions.has(player, node)) {
                return true;
            }
        } else {
            if (player.hasPermission(node)) {
                return true;
            }
        }
        return false;
    }

    public String parseName(Player player) {
        return parseChat(player, "", plugin.nameFormat);
    }

    public String parseJoin(Player player) {
        return parseChat(player, "", plugin.joinFormat);
    }
    
	public String getRawInfo(Player player, String info) {
		refreshMaps();
		if (plugin.permissionsB) {
			return getPermissionsInfo(player, info);
	    } else if (plugin.gmPermissionsB) {
	    	return getGroupManagerInfo(player, info);
	    } else {
			if (plugin.mIConfig.getNode("mchat." + info) == null) return "";
			plugin.otherMap.putAll(plugin.mIConfig.getNode("mchat." + info).getAll());
			for (Entry<String, Object> entry : plugin.otherMap.entrySet()) {
				if (player.hasPermission("mchat." + info + "." + entry.getKey())) {
					plugin.infoResolve = entry.getValue().toString();
					if (plugin.infoResolve != null && !info.isEmpty()) {
						return plugin.infoResolve;
					}
			        break;
			    }
			}
	    }
		return "";
	}
	
	public String getRawPrefix(Player player) {
		return getRawInfo(player, "prefix");
	}
	
	public String getRawSuffix(Player player) {
		return getRawInfo(player, "suffix");
	}	
	
	public String getRawGroup(Player player) {
		return getRawInfo(player, "group");
	}
	
	public String getInfo(Player player, String info) {
		return getRawInfo(player, info).replaceAll("(&([A-Fa-f0-9]))", "\u00A7$2");
	}
	
	public String getPrefix(Player player) {
		return getInfo(player, "prefix");
	}
	
	public String getSuffix(Player player) {
		return getInfo(player, "suffix");
	}
	
	
	public String getGroup(Player player) {
		return getInfo(player, "group");
	}
	
	protected void refreshMaps() {
		plugin.otherMap.clear();
		plugin.infoMap.clear();
		plugin.infoMap.putAll(plugin.mIConfig.getNode("mchat").getAll());
		plugin.mIListener.checkConfig();
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	private String getPermissionsInfo(Player player, String info) {
		if (info == "group") {
			return getPermissionsGroup(player);
		}
		String pName = player.getName();
		String world = player.getWorld().getName();
		if (plugin.permissions3) {
			String userString = plugin.permissions.getInfoString(world, pName, info, false);
			String group = plugin.permissions.getPrimaryGroup(world, pName);
			if (userString != null && !userString.isEmpty()) {
				return userString;
			}
			if (group == null) return "";
			String groupString = plugin.permissions.getInfoString(world, group, info, true);
			if (groupString == null) return "";
			return groupString;
		} else {
			String group = plugin.permissions.getGroup(world, pName);
			String userString = plugin.permissions.getUserPermissionString(world, pName, info);
			if (userString != null && !userString.isEmpty()) {
				return userString;
			}
			if (group == null) return "";
			String groupString = plugin.permissions.getGroupPermissionString(world, group, info);
			return groupString;
		}
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	private String getPermissionsGroup(Player player) {
		String pName = player.getName();
		String world = player.getWorld().getName();
		if (plugin.permissions3) {
			String group = plugin.permissions.getPrimaryGroup(world, pName);
			if (group == null) return "";
			return group;
		} else {
			String group = plugin.permissions.getGroup(world, pName);
			if (group == null) return "";
			return group;
		}
	}
	
	@SuppressWarnings("static-access")
	private String getGroupManagerInfo(Player player, String info) {
		if (info == "group") {
			return getGroupManagerGroup(player);
		}
		String pName = player.getName();
		String group = plugin.gmPermissions.getGroup(pName);
		String userString = plugin.gmPermissions.getUserPermissionString(pName, info);
		if (userString != null && !userString.isEmpty()) {
			return userString;
		}
		if (group == null) return "";
		String groupString = plugin.gmPermissions.getGroupPermissionString(group, info);
		return groupString;
	}
	
	@SuppressWarnings("static-access")
	private String getGroupManagerGroup(Player player) {
		String pName = player.getName();
		String group = plugin.gmPermissions.getGroup(pName);
		if (group == null) return "";
		return group;
	}
}
