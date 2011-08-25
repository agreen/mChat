package net.D3GN.MiracleM4n.mChat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avaje.ebeaninternal.server.el.ElSetValue;
import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionInfo;
import com.platymuus.bukkit.permissions.PermissionsMain;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import com.sun.deploy.config.Config;
import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class mChatAPI {

    mChat plugin;

    public mChatAPI(mChat plugin) {
        this.plugin = plugin;
    }

    /*
     * Format Stuff
     */
    public String ParseChatMessage(Player player, String msg, String formatAll) {
        String prefix = getRawPrefix(player);
        String suffix = getRawSuffix(player);
        String group = getRawGroup(player);

        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (group == null)
            group = "";


        Integer locX = (int) player.getLocation().getX();
        Integer locY = (int) player.getLocation().getY();
        Integer locZ = (int) player.getLocation().getZ();
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);

        String healthbar = healthBar(player);
        String health = String.valueOf(player.getHealth());
        String world = player.getWorld().getName();

        if (world.contains("_nether"))
            world = world.replace("_nether", " Nether");

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.dateFormat);
        String time = dateFormat.format(now);

        String format = parseVars(formatAll, player);
        String[] search;
        String[] replace;

        msg = msg.replaceAll("%", "%%");

        if (format == null) {
            return msg;
        }
        if (checkPermissions(player, "mchat.coloredchat")) {
            search = new String[]{"+suffix,+s", "+prefix,+p", "+group,+g", "+world,+w", "+time,+t", "+name,+n", "+displayname,+dname,+dn", "+healthbar,+hb", "+health,+h", "+location,+loc", "+message,+msg,+m", "+Groupname,+Gname,+G", "+Worldname,+Wname,+W"};
            replace = new String[]{suffix, prefix, group, world, time, player.getName(), player.getDisplayName(), healthbar, health, loc, msg, getGroupName(group), getWorldName(world)};
        } else {
            search = new String[]{"+suffix,+s", "+prefix,+p", "+group,+g", "+world,+w", "+time,+t", "+name,+n", "+displayname,+dname,+dn", "+healthbar,+hb", "+health,+h", "+location,+loc", "+message,+msg,+m", "+Groupname,+Gname,+G", "+Worldname,+Wname,+W"};
            replace = new String[]{suffix, prefix, group, world, time, player.getName(), player.getDisplayName(), healthbar, health, loc, addColour(msg).replaceAll("(§([a-z0-9]))", ""), getGroupName(group), getWorldName(world)};
        }
        return replaceVars(format, search, replace);
    }

    public String ParseChatMessage(Player player, String msg) {
        return ParseChatMessage(player, msg, plugin.chatFormat);
    }

    public String ParsePlayerName(Player player) {
        return ParseChatMessage(player, "", plugin.nameFormat);
    }

    public String ParseJoinName(Player player) {
        return ParseChatMessage(player, "", plugin.joinFormat);
    }

    public String getGroupName(String group) {
       if (plugin.mIListener.groupNodeList.get(group) != null) {
           return plugin.mIListener.groupNodeList.get(group).toString();
       }
       return group;
    }

    public String getWorldName(String world) {
       if (plugin.mIListener.worldNodeList.get(world) != null) {
           return plugin.mIListener.worldNodeList.get(world).toString();
       }
       return world;
    }

    /*
     * Info Stuff
     */
    public String getRawInfo(Player player, String info) {
        refreshMaps();

        if (plugin.permissionsB)
            return getPermissionsInfo(player, info);

        if (plugin.gmPermissionsB)
            return getGroupManagerInfo(player, info);

        if (plugin.PermissionBuB) {
            return getSuperPermsInfo(player, info);
        }

        return getBukkitInfo(player, info);
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
        return addColour(getRawInfo(player, info));
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

    /*
     * Bukkit Stuff
     */
    String getBukkitInfo(Player player, String info) {
        if (plugin.mIConfig.getNode("mchat." + info) == null)
            return "";

        plugin.otherMap.putAll(plugin.mIConfig.getNode("mchat." + info).getAll());
        for (Entry<String, Object> entry : plugin.otherMap.entrySet()) {
            if (player.hasPermission("mchat." + info + "." + entry.getKey())) {
                plugin.infoResolve = entry.getValue().toString();

                if (plugin.infoResolve != null && !info.isEmpty())
                    return plugin.infoResolve;

                break;
            }
        }

        return "";
    }


    /*
     * SuperPerms Stuff
     */
     String getSuperPermsInfo(Player player, String info) {
        if (info.equals("group"))
            return getSuperPermsGroup(player);

        if (plugin.mIConfig.getNode("mchat." + info) == null)
            return "";

        plugin.otherMap.putAll(plugin.mIConfig.getNode("mchat." + info).getAll());
        for (Entry<String, Object> entry : plugin.otherMap.entrySet()) {
            if (player.hasPermission("mchat." + info + "." + entry.getKey())) {
                plugin.infoResolve = entry.getValue().toString();

                if (plugin.infoResolve != null && !info.isEmpty())
                    return plugin.infoResolve;

                break;
            }
        }

        return "";
     }

    String getSuperPermsGroup(Player player) {
        Plugin pPlugin = plugin.pm.getPlugin("PermissionsBukkit");
        PermissionsPlugin pBukkit = (PermissionsPlugin)pPlugin;
        List<Group> pGroups = pBukkit.getGroups(player.getName());

        if (pGroups == null)
            return "";

        return pGroups.get(0).getName();
    }

    /*
     * Permissions Stuff
     */
    @SuppressWarnings("deprecation")
    String getPermissionsInfo(Player player, String info) {
        if (info.equals("group"))
            return getPermissionsGroup(player);

        String pName = player.getName();
        String world = player.getWorld().getName();

        if (plugin.permissions3) {
            String userString = plugin.permissions.getInfoString(world, pName, info, false);
            String group = plugin.permissions.getPrimaryGroup(world, pName);

            if (userString != null && !userString.isEmpty())
                return userString;

            if (group == null)
                return "";

            String groupString = plugin.permissions.getInfoString(world, group, info, true);
            if (groupString == null)
                return "";

            return groupString;
        } else {
            String group = plugin.permissions.getGroup(world, pName);
            String userString = plugin.permissions.getUserPermissionString(world, pName, info);
            if (userString != null && !userString.isEmpty())
                return userString;

            if (group == null)
                return "";

            return plugin.permissions.getGroupPermissionString(world, group, info);
        }
    }

    @SuppressWarnings("deprecation")
    String getPermissionsGroup(Player player) {
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (plugin.permissions3) {
            String group = plugin.permissions.getPrimaryGroup(world, pName);

            if (group == null)
                return "";

            return group;
        } else {
            String group = plugin.permissions.getGroup(world, pName);

            if (group == null)
                return "";

            return group;
        }
    }

    /*
     * GroupManager Stuff
     */
    String getGroupManagerInfo(Player player, String info) {
        if (info.equals("group"))
            return getGroupManagerGroup(player);

        String pName = player.getName();
        String group = plugin.gmPermissions.getGroup(pName);
        String userString = plugin.gmPermissions.getUserPermissionString(pName, info);

        if (userString != null && !userString.isEmpty())
            return userString;

        if (group == null)
            return "";

        return plugin.gmPermissions.getGroupPermissionString(group, info);
    }

    String getGroupManagerGroup(Player player) {
        String pName = player.getName();
        String group = plugin.gmPermissions.getGroup(pName);

        if (group == null)
            return "";

        return group;
    }

    /*
     * Misc Stuff
     */
    public String healthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();
        int fill = Math.round((health / maxHealth) * barLength);

        String barColor = (fill <= 4) ? "&4" : (fill <= 7) ? "&e" : "&2";

        StringBuilder out = new StringBuilder();
        out.append(barColor);

        for (int i = 0; i < barLength; i++) {
            if (i == fill)
                out.append("&8");
            
            out.append("|");
        }

        out.append("&f");

        return out.toString();
    }

    public String addColour(String string) {
        string = string.replace("`e", "")
                        .replace("`r", ChatColor.RED.toString())             .replace("`R", ChatColor.DARK_RED.toString())
                        .replace("`y", ChatColor.YELLOW.toString())          .replace("`Y", ChatColor.GOLD.toString())
                        .replace("`g", ChatColor.GREEN.toString())           .replace("`G", ChatColor.DARK_GREEN.toString())
                        .replace("`a", ChatColor.AQUA.toString())            .replace("`A", ChatColor.DARK_AQUA.toString())
                        .replace("`b", ChatColor.BLUE.toString())            .replace("`B", ChatColor.DARK_BLUE.toString())
                        .replace("`p", ChatColor.LIGHT_PURPLE.toString())    .replace("`P", ChatColor.DARK_PURPLE.toString())
                        .replace("`k", ChatColor.BLACK.toString())           .replace("`s", ChatColor.GRAY.toString())
                        .replace("`S", ChatColor.DARK_GRAY.toString())       .replace("`w", ChatColor.WHITE.toString());

        string = string.replace("<r>", "")
                        .replace("<black>", "\u00A70")                       .replace("<navy>", "\u00A71")
                        .replace("<green>", "\u00A72")                       .replace("<teal>", "\u00A73")
                        .replace("<red>", "\u00A74")                         .replace("<purple>", "\u00A75")
                        .replace("<gold>", "\u00A76")                        .replace("<silver>", "\u00A77")
                        .replace("<gray>", "\u00A78")                        .replace("<blue>", "\u00A79")
                        .replace("<lime>", "\u00A7a")                        .replace("<aqua>", "\u00A7b")
                        .replace("<rose>", "\u00A7c")                        .replace("<pink>", "\u00A7d")
                        .replace("<yellow>", "\u00A7e")                      .replace("<white>", "\u00A7f");

        string = string.replaceAll("(§([a-z0-9]))", "\u00A7$2");

        string = string.replaceAll("(&([a-z0-9]))", "\u00A7$2");

        return string.replace("&&", "&");
    }

    public Boolean checkPermissions(Player player, String node) {
        if (plugin.permissionsB) {
            if (plugin.permissions.has(player, node))
                return true;
        } else if (plugin.gmPermissionsB) {
            if (plugin.gmPermissions.has(player, node))
                return true;
        }   else if (player.hasPermission(node)) {
              return true;
        }
        return false;
    }

    public String replaceMess(String string) {
        if (string.equals("joinMessage"))
            string = plugin.joinMessage;

        if (string.equals("kickMessage"))
            string = plugin.kickMessage;

        if (string.equals("leaveMessage"))
            string = plugin.leaveMessage;

        return plugin.mAPI.addColour(string);
    }

    String parseVars(String format, Player player) {
        Pattern pattern;
        pattern = Pattern.compile("\\+<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = getRawInfo(player, matcher.group(1));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    String replaceVars(String format, String[] search, String[] replace) {
        if (search.length != replace.length)
            return "";
        for (int i = 0; i < search.length; i++) {
            if (search[i].contains(","))
                for (String s : search[i].split(",")) {
                    if (s == null || replace[i] == null)
                        continue;
                    
                    format = format.replace(s, replace[i]);
                }
            else
                format = format.replace(search[i], replace[i]);
        }

        return addColour(format);
    }

    protected void refreshMaps() {
        plugin.otherMap.clear();
        plugin.infoMap.clear();
        plugin.infoMap.putAll(plugin.mIConfig.getNode("mchat").getAll());
    }

    /*
     * Depreciated Stuff
     */
    @Deprecated
    public String parseName(Player player) {
        return ParsePlayerName(player);
    }

    @Deprecated
    public String parseJoin(Player player) {
        return ParseJoinName(player);
    }

    @Deprecated
    public String parseChat(Player player, String msg) {
        return ParseChatMessage(player, msg, plugin.chatFormat);
    }
}
