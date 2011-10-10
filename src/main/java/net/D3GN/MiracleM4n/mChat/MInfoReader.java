package net.D3GN.MiracleM4n.mChat;

import org.bukkit.util.config.Configuration;

import java.util.TreeMap;

public class MInfoReader {
    mChat plugin;

    public MInfoReader(mChat plugin) {
        this.plugin = plugin;
    }

    TreeMap<String, Object> defaultUser = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUserWorld = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUserWorldInfo = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUserInfo = new TreeMap<String, Object>();

	TreeMap<String, Object> defaultGroup = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultGroupWorld = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultGroupWorldInfo = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultGroupInfo = new TreeMap<String, Object>();

    // Player Info
    public void addPlayer(String player, String defaultGroup) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users") != null) {
            if (config.getProperty("users." + player) == null) {
                defaultUserInfo.put("prefix", "");
                defaultUserInfo.put("suffix", "");
                defaultUser.put("group", defaultGroup);
                defaultUser.put("info", defaultUserInfo);
                config.setProperty("users." + player, defaultUser);

                config.save();

                addDefaultGroup(defaultGroup);

                plugin.mIListener.loadConfig();
            }
        }
    }

    protected void addDefaultGroup(String groupName) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + groupName) == null) {
            defaultGroupInfo.put("prefix", "");
            defaultGroupInfo.put("suffix", "");
            defaultGroup.put("info", defaultGroupInfo);
            config.setProperty("groups." + groupName, defaultGroup);

            config.save();
        }
    }

    public void setPlayerGroup(String player, String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player) != null) {
            config.setProperty("users." + player + ".group", group);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerName(String player, String newName) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player) != null) {
            if (config.getProperty("users." + newName) == null) {
                config.setProperty("users." + newName, config.getProperty("users." + player));
                config.removeProperty("users." + player);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void removePlayer(String player) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player) != null) {
            config.removeProperty("users." + player);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addPlayerInfoVar(String player, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".info") != null) {
            config.setProperty("users." + player + ".info." + var, value);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerInfoVar(String player, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".info." + oldVar) != null) {
            config.setProperty("users." + player + ".info." + newVar, config.getProperty("users." + player + ".info." + oldVar));
            config.removeProperty("users." + player + ".info." + oldVar);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerInfoValue(String player, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".info." + var) != null) {
            config.setProperty("users." + player + ".info." + var, newValue);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removePlayerInfoVar(String player, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".info." + var) != null) {
            config.removeProperty("users." + player + ".info." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addPlayerWorld(String player, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player) != null) {
            if (config.getProperty("users." + player + ".worlds." + world) == null) {
                defaultUserWorld.put("prefix", "");
                defaultUserWorld.put("suffix", "");
                config.setProperty("users." + player + ".worlds." + world, defaultUserWorld);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void editPlayerWorldName(String player, String oldWorld, String newWorld) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".worlds." + oldWorld) != null) {
            config.setProperty("users." + player + ".worlds." + newWorld, config.getProperty("users." + player + ".worlds." + oldWorld));
            config.removeProperty("users." + player + ".worlds." + oldWorld);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removePlayerWorld(String player, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player) != null) {
            if (config.getProperty("users." + player + ".worlds." + world) != null) {
                config.removeProperty("users." + player + ".worlds." + world);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void addPlayerWorldVar(String player, String world, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".worlds." + world) != null) {
            config.setProperty("users." + player + ".worlds." + world + "." + var, value);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerWorldVar(String player, String world, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".worlds." + world + "." + oldVar) != null) {
            config.setProperty("users." + player + ".worlds." + world + "." + newVar, config.getProperty("users." + player + ".worlds." + world + "." + oldVar));
            config.removeProperty("users." + player + ".worlds." + world + "." + oldVar);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerWorldValue(String player, String world, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".worlds." + world + "." + var) != null) {
            config.setProperty("users." + player + ".worlds." + world + "." + var, newValue);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removePlayerWorldVar(String player, String world, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users." + player + ".worlds." + world + "." + var) != null) {
            config.removeProperty("users." + player + ".worlds." + world + "." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    // Group Info
    public void addGroup(String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group) == null) {
            defaultGroupInfo.put("prefix", "");
            defaultGroupInfo.put("suffix", "");
            defaultGroup.put("info", defaultGroupInfo);
            config.setProperty("groups." + group, defaultGroup);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupName(String oldGroup, String newGroup) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + oldGroup) != null) {
            config.setProperty("groups." + newGroup, config.getProperty("groups." + oldGroup));
            config.removeProperty("groups." + oldGroup);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removeGroup(String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group) != null) {
            config.removeProperty("groups." + group);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addGroupInfoVar(String group, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group) != null) {
            config.setProperty("groups." + group + ".info." + var, value);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupInfoVar(String group, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".info." + oldVar) != null) {
            config.setProperty("groups." + group + ".info." + newVar, config.getProperty("groups." + group + ".info." + oldVar));
            config.removeProperty("groups." + group + ".info." + oldVar);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupInfoValue(String group, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".info." + var) != null) {
            config.setProperty("groups." + group + ".info." + var, newValue);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removeGroupInfoVar(String group, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".info." + var) != null) {
            config.removeProperty("groups." + group + ".info." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addGroupWorld(String group, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group) != null) {
            if (config.getProperty("groups." + group + ".worlds." + world) == null) {
                config.setProperty("groups." + group + ".worlds." + world, "");

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void editGroupWorldName(String group, String oldWorld, String newWorld) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".worlds." + oldWorld) != null) {
            config.setProperty("groups." + group + ".worlds." + newWorld, config.getProperty("groups." + group + ".worlds." + oldWorld));
            config.removeProperty("groups." + group + ".worlds." + oldWorld);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removeGroupWorld(String group, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group) != null) {
            if (config.getProperty("groups." + group + ".worlds." + world) != null) {
                config.removeProperty("groups." + group + ".worlds." + world);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void addGroupWorldVar(String group, String world, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".worlds." + world) != null) {
            config.setProperty("groups." + group + ".worlds." + world + "." + var, value);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupWorldVar(String group, String world, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".worlds." + world + "." + oldVar) != null) {
            config.setProperty("groups." + group + ".worlds." + world + "." + newVar, config.getProperty("groups." + group + ".worlds." + world + "." + oldVar));
            config.removeProperty("groups." + group + ".worlds." + world + "." + oldVar);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupWorldValue(String group, String world, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".worlds." + world + "." + var) != null) {
            config.setProperty("groups." + group + ".worlds." + world + "." + var, newValue);
            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removeGroupWorldVar(String group, String world, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("groups." + group + ".worlds." + world + "." + var) != null) {
            config.removeProperty("groups." + group + ".worlds." + world + "." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }
}
