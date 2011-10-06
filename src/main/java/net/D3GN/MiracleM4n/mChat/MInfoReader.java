package net.D3GN.MiracleM4n.mChat;

import org.bukkit.util.config.Configuration;

import java.util.HashMap;

public class MInfoReader {
    mChat plugin;

    public MInfoReader(mChat plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addPlayer(String player, String defaultGroup) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users") != null) {
            plugin.mIListener.defaultUserInfo.put("prefix", "&2Prefix");
            plugin.mIListener.defaultUser.put("group", "default");
            plugin.mIListener.defaultUser.put("info", plugin.mIListener.defaultUserInfo);
            plugin.mIListener.defaultUsers.put(player, plugin.mIListener.defaultUser);
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
            config.setProperty("users", plugin.mIListener.defaultUsers);

            config.save();

            addDefaultGroup(defaultGroup);

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void addDefaultGroup(String groupName) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + groupName) == null) {
            plugin.mIListener.defaultGroupInfo.put("prefix", "&4Prefix");
            plugin.mIListener.defaultGroupInfo.put("suffix", "&4Suffix");
            plugin.mIListener.defaultGroup.put("info", plugin.mIListener.defaultGroupInfo);
            plugin.mIListener.defaultGroups.put(groupName, plugin.mIListener.defaultGroup);
            plugin.mIListener.defaultGroups.putAll((HashMap) config.getProperty("groups"));
            config.setProperty("groups", plugin.mIListener.defaultGroups);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editPlayerName(String player, String newName) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + newName) == null) {
                plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
                plugin.mIListener.defaultUsers.put(newName, plugin.mIListener.defaultUsers.get(player));
                plugin.mIListener.defaultUsers.remove(player);
                config.setProperty("users", plugin.mIListener.defaultUsers);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removePlayer(String player) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
            plugin.mIListener.defaultUsers.remove(player);
            config.setProperty("users", plugin.mIListener.defaultUsers);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addPlayerInfoVar(String player, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + ".info") != null)
                plugin.mIListener.defaultUserInfo.putAll(config.getNode("users." + player + ".info").getAll());
            plugin.mIListener.defaultUserInfo.put(var, value);
            plugin.mIListener.defaultUser.putAll(config.getNodes("users." + player));
            plugin.mIListener.defaultUser.put("info", plugin.mIListener.defaultUserInfo);
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
            plugin.mIListener.defaultUsers.put(player, plugin.mIListener.defaultUser);
            config.setProperty("users", plugin.mIListener.defaultUsers);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editPlayerInfoVar(String player, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + ".info") != null)
                plugin.mIListener.defaultUserInfo.putAll(config.getNode("users." + player + ".info").getAll());
            plugin.mIListener.defaultUserInfo.put(newVar, plugin.mIListener.defaultUserInfo.get(oldVar));
            plugin.mIListener.defaultUserInfo.remove(oldVar);
            plugin.mIListener.defaultUser.putAll(config.getNodes("users." + player));
            plugin.mIListener.defaultUser.put("info", plugin.mIListener.defaultUserInfo);
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
            plugin.mIListener.defaultUsers.put(player, plugin.mIListener.defaultUser);
            config.setProperty("users", plugin.mIListener.defaultUsers);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editPlayerInfoValue(String player, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + ".info") != null)
                plugin.mIListener.defaultUserInfo.putAll(config.getNode("users." + player + ".info").getAll());
            plugin.mIListener.defaultUserInfo.put(var, newValue);
            plugin.mIListener.defaultUser.putAll(config.getNodes("users." + player));
            plugin.mIListener.defaultUser.put("info", plugin.mIListener.defaultUserInfo);
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
            plugin.mIListener.defaultUsers.put(player, plugin.mIListener.defaultUser);
            config.setProperty("users", plugin.mIListener.defaultUsers);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removePlayerInfoVar(String player, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + ".info") != null)
                plugin.mIListener.defaultUserInfo.putAll(config.getNode("users." + player + ".info").getAll());
            plugin.mIListener.defaultUserInfo.remove(var);
            plugin.mIListener.defaultUser.putAll(config.getNodes("users." + player));
            plugin.mIListener.defaultUser.put("info", plugin.mIListener.defaultUserInfo);
            plugin.mIListener.defaultUsers.putAll((HashMap) config.getProperty("users"));
            plugin.mIListener.defaultUsers.put(player, plugin.mIListener.defaultUser);
            config.setProperty("users", plugin.mIListener.defaultUsers);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addPlayerWorld(String player, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editPlayerWorld(String player, String oldWorld, String newWorld) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removePlayerWorld(String player, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addPlayerWorldVar(String player, String world, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editPlayerWorldVar(String player, String world, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editPlayerWorldValue(String player, String world, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removePlayerWorldVar(String player, String world, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addGroup(String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editGroup(String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeGroup(String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addGroupInfoVar(String group, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editGroupInfoVar(String group, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editGroupInfoValue(String group, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeGroupInfoVar(String group, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addGroupWorld(String group, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editGroupWorld(String group, String oldWorld, String newWorld) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeGroupWorld(String group, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addGroupWorldVar(String group, String world, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editGroupWorldVar(String group, String world, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editGroupWorldValue(String group, String world, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeGroupWorldVar(String group, String world, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();
    }
}
