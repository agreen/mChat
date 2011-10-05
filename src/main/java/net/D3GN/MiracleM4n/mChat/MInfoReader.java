package net.D3GN.MiracleM4n.mChat;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import java.util.HashMap;

public class MInfoReader {
    mChat plugin;

    public MInfoReader(mChat plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addDefaultPlayer(Player player, String defaultGroup) {
        Configuration config = plugin.mIConfig;
        String pName = player.getName();

        config.load();

        plugin.mIListener.defaultUserInfo.put("prefix", "&2Prefix");
        plugin.mIListener.defaultUser.put("group", "default");
        plugin.mIListener.defaultUser.put("info", plugin.mIListener.defaultUserInfo);
        plugin.mIListener.defaultUsers.put(pName, plugin.mIListener.defaultUser);
        if (config.getProperty("users") != null)
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
        config.setProperty("users", plugin.mIListener.defaultUsers);

        config.save();

        addDefaultGroup(defaultGroup);

        plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addDefaultGroup(String groupName) {
        Configuration config = plugin.mIConfig;

        config.load();

        if (config.getNode("groups." + groupName) == null) {
        plugin.mIListener.defaultGroupInfo.put("prefix", "&4Prefix");
        plugin.mIListener.defaultGroupInfo.put("suffix", "&4Suffix");
        plugin.mIListener.defaultGroup.put("info", plugin.mIListener.defaultGroupInfo);
        plugin.mIListener.defaultGroups.put("default", plugin.mIListener.defaultGroup);
        if (config.getProperty("groups") != null)
            plugin.mIListener.defaultGroups.putAll((HashMap) config.getProperty("groups"));
        config.setProperty("groups", plugin.mIListener.defaultGroups);

        config.save();

        plugin.mIListener.loadConfig();

        }
    }

    public void addPlayer(Player player) {

    }

    public void editPlayer(Player player) {

    }

    public void removePlayer(Player player) {

    }

    public void addPlayerInfoVar(Player player, String var, String value) {

    }

    public void editPlayerInfoVar(Player player, String oldVar, String newVar) {

    }

    public void editPlayerInfoValue(Player player, String var, String newValue) {

    }

    public void removePlayerInfoVar(Player player, String var) {

    }

    public void addPlayerWorld(Player player, String world) {

    }

    public void editPlayerWorld(Player player, String oldWorld, String newWorld) {

    }

    public void removePlayerWorld(Player player, String world) {

    }

    public void addPlayerWorldVar(Player player, String world, String var, String value) {

    }

    public void editPlayerWorldVar(Player player, String world, String oldVar, String newVar) {

    }

    public void editPlayerWorldValue(Player player, String world, String var, String newValue) {

    }

    public void removePlayerWorldVar(Player player, String world, String var) {

    }
    
    public void addGroup(String group) {

    }

    public void editGroup(String group) {

    }

    public void removeGroup(String group) {

    }

    public void addGroupInfoVar(String group, String var, String value) {

    }

    public void editGroupInfoVar(String group, String oldVar, String newVar) {

    }

    public void editGroupInfoValue(String group, String var, String newValue) {

    }

    public void removeGroupInfoVar(String group, String var) {

    }

    public void addGroupWorld(String group, String world) {

    }

    public void editGroupWorld(String group, String oldWorld, String newWorld) {

    }

    public void removeGroupWorld(String group, String world) {

    }

    public void addGroupWorldVar(String group, String world, String var, String value) {

    }

    public void editGroupWorldVar(String group, String world, String oldVar, String newVar) {

    }

    public void editGroupWorldValue(String group, String world, String var, String newValue) {

    }

    public void removeGroupWorldVar(String group, String world, String var) {

    }
}
