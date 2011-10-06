package net.D3GN.MiracleM4n.mChat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.D3GN.MiracleM4n.mChannel.mChannel;
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

        // Location
        Integer locX = (int) player.getLocation().getX();
        Integer locY = (int) player.getLocation().getY();
        Integer locZ = (int) player.getLocation().getZ();
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);

        // Health
        String healthbar = healthBar(player);
        String health = String.valueOf(player.getHealth());

        // World
        String world = player.getWorld().getName();

        // 1.8 Vars
        String hungerLevel = String.valueOf(player.getFoodLevel());
        String hungerBar = basicBar(player.getFoodLevel(), 20, 10);
        String level = String.valueOf(player.getLevel());
        String exp = String.valueOf(player.getExperience());
        String expBar = basicBar(player.getExperience(), ((player.getLevel() + 1) * 10), 10);
        String tExp = String.valueOf(player.getTotalExperience());
        String gMode = player.getGameMode().name();

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.dateFormat);
        String time = dateFormat.format(now);

        // mChannel Vars
        String mCName = "";
        String mCPref = "";
        String mCSuf = "";
        String mCType = "";

        if (plugin.mChannelB) {
            mCName = mChannel.API.getPlayersChannel(player);
            mCPref = mChannel.API.getChannelPrefix(mCName);
            mCSuf = mChannel.API.getChannelSuffix(mCName);
            mCType = mChannel.API.getChannelType(mCName);
        }


        String format = parseVars(formatAll, player);
        String[] search;
        String[] replace;

        msg = msg.replaceAll("%", "%%");
        format = format.replaceAll("%", "%%");

        if (format == null)
            return msg;

        msg = replaceCensoredWords(msg);

        if (!checkPermissions(player, "mchat.coloredchat"))
            msg = addColour(msg).replaceAll("(§([a-z0-9]))", "");

        search = new String[]{
                "+displayname,+dname,+dn",
                "+experiencebar,+expb,+ebar,+eb",
                "+experience,+exp",
                "+gamemode,+gm",
                "+group,+g",
                "+hungerbar,+hub",
                "+hunger",
                "+healthbar,+hb",
                "+health,+h",
                "+location,+loc",
                "+level,+l",
                "+message,+msg,+m",
                "+name,+n",
                "+prefix,+p",
                "+suffix,+s",
                "+totalexp,+texp,+te",
                "+time,+t",
                "+world,+w",
                "+Cname,+Cn",
                "+Cprefix,+Cp",
                "+Csuffix,+Cs",
                "+Ctype,+Ct",
                "+Groupname,+Gname,+G",
                "+Worldname,+Wname,+W"
                };

        replace = new String[]{
                player.getDisplayName(),
                expBar,
                exp,
                gMode,
                group,
                hungerBar,
                hungerLevel,
                healthbar,
                health,
                loc,
                level,
                msg,
                player.getName(),
                prefix,
                suffix,
                tExp,
                time,
                world,
                mCName,
                mCPref,
                mCSuf,
                mCType,
                getGroupName(group),
                getWorldName(world),
                };



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
       if (plugin.mIListener.groupNodeList.get(group) != null)
           return plugin.mIListener.groupNodeList.get(group).toString();

       return group;
    }

    public String getWorldName(String world) {
       if (plugin.mIListener.worldNodeList.get(world) != null)
           return plugin.mIListener.worldNodeList.get(world).toString();

       return world;
    }

    /*
     * Info Stuff
     */
    public String getRawInfo(Player player, String info) {

        if (plugin.mChat_Info_Only)
            return getmChatInfo(player, info);

        if (plugin.permissionsB)
            return getPermissionsInfo(player, info);

        if (plugin.gmPermissionsB)
            return getGroupManagerInfo(player, info);

        if (plugin.PEXB)
            return getPEXInfo(player, info);

        if (plugin.bPermB)
            return getbPermInfo(player, info);

        if (plugin.mChat_Nodes_Only) {
            if (plugin.PermissionBuB)
                return getPermBukkitInfo(player, info);

            return getBukkitInfo(player, info);
        }

        return getmChatInfo(player, info);
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
     * mChat Stuff
     */
    String getmChatInfo(Player player, String info) {
        if (info.equals("group"))
            return getmChatGroup(player);

        if (!getmChatPlayerInfo(player, info).isEmpty() ||
                getmChatPlayerInfo(player, info) != null)
            return getmChatPlayerInfo(player, info);

        if (!getmChatGroupInfo(player, info).isEmpty() ||
                getmChatGroupInfo(player, info) != null)
            return getmChatGroupInfo(player, info);

        return "";
    }

    String getmChatPlayerInfo(Player player, String info) {
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (plugin.usersMap.get(pName + ".info." + info) != null)
            return plugin.usersMap.get(pName + ".info." + info).toString();

        if (plugin.usersMap.get(pName + ".worlds." + world + "." + info) != null)
            return plugin.usersMap.get(pName + ".worlds." + world + "." + info).toString();

        return getmChatGroupInfo(player, info);
    }


    String getmChatGroupInfo(Player player, String info) {
        String world = player.getWorld().getName();
        String group = getmChatGroup(player);

        if (plugin.groupsMap.get(group + ".info." + info) != null)
            return plugin.groupsMap.get(group + ".info." + info).toString();

        if (plugin.groupsMap.get(group + ".worlds." + world + "." + info) != null)
            return plugin.groupsMap.get(group + ".worlds." + world + "." + info).toString();

        return "";
    }

    String getmChatGroup(Player player) {
        String pName = player.getName();
        if (plugin.usersMap.get(pName + ".group") != null)
            return plugin.usersMap.get(pName + ".group").toString();

        return "";
    }

    /*
     * Bukkit Stuff
     */
    String getBukkitInfo(Player player, String info) {
        if (plugin.mIConfig.getNode("mchat." + info) == null)
            return "";

        plugin.oldNodeMap.putAll(plugin.mIConfig.getNode("mchat." + info).getAll());
        for (Entry<String, Object> entry : plugin.oldNodeMap.entrySet()) {
            if (checkPermissions(player, "mchat." + info + "." + entry.getKey(), false)) {
                String infoResolve = entry.getValue().toString();

                if (infoResolve != null && !info.isEmpty())
                    return infoResolve;

                break;
            }
        }

        return "";
    }

    /*
     * PermissionsBukkit Stuff
     */
     String getPermBukkitInfo(Player player, String info) {
        if (info.equals("group"))
            return getPermBukkitGroup(player);

        if (plugin.mIConfig.getNode("mchat." + info) == null)
            return "";

        plugin.oldNodeMap.putAll(plugin.mIConfig.getNode("mchat." + info).getAll());
        for (Entry<String, Object> entry : plugin.oldNodeMap.entrySet()) {
            if (checkPermissions(player, "mchat." + info + "." + entry.getKey(), false)) {
                String infoResolve = entry.getValue().toString();

                if (infoResolve != null && !info.isEmpty())
                    return infoResolve;
            }
        }

        return "";
     }

    String getPermBukkitGroup(Player player) {
        Plugin pPlugin = plugin.pm.getPlugin("PermissionsBukkit");
        PermissionsPlugin pBukkit = (PermissionsPlugin)pPlugin;
        List<Group> pGroups = pBukkit.getGroups(player.getName());

        if (pGroups.isEmpty()) return "";

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
     * PEX Stuff
     */
    String getPEXInfo(Player player, String info) {
        if (info.equals("group"))
            return getPEXGroup(player);

        String pName = player.getName();
        String world = player.getWorld().getName();

        String userString = plugin.pexPermissions.getUser(pName).getOption(info, world);
        if (userString != null && !userString.isEmpty())
            return userString;

        return "";
    }

    String getPEXGroup(Player player) {
        String pName = player.getName();
        String world = player.getWorld().getName();

        String group = plugin.pexPermissions.getUser(pName).getGroupsNames(world)[0];

        if (group == null)
            return "";

        return group;
    }

    /*
     * bPermissions Stuff
     */
    String getbPermInfo(Player player, String info) {
        if (info.equals("group"))
            return getbPermGroup(player);

        String userString = plugin.bInfoR.getValue(player, info);
        if (userString != null && !userString.isEmpty())
            return userString;

        return "";
    }

    String getbPermGroup(Player player) {
        String group = plugin.bPermS.getPermissionSet(player.getWorld()).getGroups(player).get(0);

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

        return basicBar(health, maxHealth, barLength);
    }

    public String basicBar(float currentValue, float maxValue, float barLength) {
        int fill = Math.round((currentValue / maxValue) * barLength);

        String barColor = (fill <= (barLength/4)) ? "&4" : (fill <= (barLength/7)) ? "&e" : "&2";

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
        if (plugin.permissionsB)
            if (plugin.permissions.has(player, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissions.has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        return player.hasPermission(node) || player.isOp();

    }

    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (plugin.permissionsB)
            if (plugin.permissions.has(player, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissions.has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        if (useOp)
            return player.isOp();

        return player.hasPermission(node);

    }

    public String getEventMessage(String eventName) {
        if (eventName.equalsIgnoreCase("join"))
            eventName = plugin.joinMessage;

        if (eventName.equalsIgnoreCase("enter"))
            eventName = plugin.joinMessage;

        if (eventName.equalsIgnoreCase("kick"))
            eventName = plugin.kickMessage;

        if (eventName.equalsIgnoreCase("quit"))
            eventName = plugin.leaveMessage;

        if (eventName.equalsIgnoreCase("leave"))
            eventName = plugin.leaveMessage;

        return plugin.mAPI.addColour(eventName);
    }

    String parseVars(String format, Player player) {
        Pattern pattern = Pattern.compile("\\+<(.*?)>");
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

    String replaceCensoredWords(String msg) {
        for (Entry<String, Object> entry : plugin.censorMap.entrySet()) {
            Pattern pattern = Pattern.compile("(?i)" + entry.getKey());
            Matcher matcher = pattern.matcher(msg);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String var = entry.getValue().toString();
                matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
            }

            matcher.appendTail(sb);

            msg = sb.toString();
        }

        return msg;
    }

    public void log(String loggedString) {
        plugin.getServer().getConsoleSender().sendMessage(loggedString);
    }
}
