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

    public void addPlayer(String player, String defaultGroup) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getProperty("users") != null) {
            if (config.getNode("users." + player) == null) {
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

        if (config.getNode("groups." + groupName) == null) {
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

        if (config.getNode("users." + player) != null) {
            config.setProperty("users." + player + ".group", group);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerName(String player, String newName) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + newName) == null) {
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

        if (config.getNode("users." + player) != null) {
            config.removeProperty("users." + player);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addPlayerInfoVar(String player, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + ".info") != null)
                defaultUserInfo.putAll(config.getNode("users." + player + ".info").getAll());
            defaultUserInfo.put(var, value);
            defaultUser.putAll(config.getNodes("users." + player));
            defaultUser.put("info", defaultUserInfo);
            config.setProperty("users." + player, defaultUser);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerInfoVar(String player, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + ".info") != null)
                defaultUserInfo.putAll(config.getNode("users." + player + ".info").getAll());
            defaultUserInfo.put(newVar, defaultUserInfo.get(oldVar));
            defaultUserInfo.remove(oldVar);
            defaultUser.putAll(config.getNodes("users." + player));
            defaultUser.put("info", defaultUserInfo);
            config.setProperty("users" + player, defaultUser);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerInfoValue(String player, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player + ".info." + var) != null) {
            config.setProperty("users." + player + ".info." + var, newValue);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removePlayerInfoVar(String player, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player + ".info." + var) != null) {
            config.removeProperty("users." + player + ".info." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addPlayerWorld(String player, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + "." + world) == null) {
                config.setProperty("users." + player + "." + world, "");

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void editPlayerWorldName(String player, String oldWorld, String newWorld) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + "." + oldWorld) != null) {
                config.setProperty("users." + player + "." + newWorld, config.getProperty("users." + player + "." + oldWorld));
                config.removeProperty("users." + player + "." + oldWorld);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void removePlayerWorld(String player, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player) != null) {
            if (config.getNode("users." + player + "." + world) != null) {
                config.removeProperty("users." + player + "." + world);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void addPlayerWorldVar(String player, String world, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player + "." + world) != null) {
            if (config.getNode("users." + player + "." + world + ".info") != null)
                defaultUserWorldInfo.putAll(config.getNode("users." + player + "." + world + ".info").getAll());
            defaultUserWorldInfo.put(var, value);
            config.setProperty("users." + player + "." + world, defaultUserWorldInfo);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerWorldVar(String player, String world, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player + "." + world + ".info." + oldVar) != null) {
            config.setProperty("users." + player + "." + world + ".info." + newVar, config.getProperty("users." + player + "." + world + ".info." + oldVar));
            config.removeProperty("users." + player + "." + world + ".info." + oldVar);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editPlayerWorldValue(String player, String world, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player + "." + world + ".info." + var) != null) {
            config.setProperty("users." + player + "." + world + ".info." + var, newValue);
            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removePlayerWorldVar(String player, String world, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("users." + player + "." + world + ".info." + var) != null) {
            config.removeProperty("users." + player + "." + world + ".info." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addGroup(String group) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group) == null) {
            config.setProperty("groups." + group, "");

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupName(String oldGroup, String newGroup) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + oldGroup) != null) {
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

        if (config.getNode("groups." + group) != null) {
            config.removeProperty("groups." + group);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addGroupInfoVar(String group, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group) != null) {
            if (config.getNode("groups." + group + ".info") != null)
                defaultGroupInfo.putAll(config.getNode("groups." + group + ".info").getAll());
            defaultGroupInfo.put(var, value);
            defaultGroup.putAll(config.getNodes("groups." + group));
            defaultGroup.put("info", defaultGroupInfo);
            config.setProperty("groups." + group, defaultGroup);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupInfoVar(String group, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group) != null) {
            if (config.getNode("groups." + group + ".info") != null)
                defaultGroupInfo.putAll(config.getNode("groups." + group + ".info").getAll());
            defaultGroupInfo.put(newVar, defaultGroupInfo.get(oldVar));
            defaultGroupInfo.remove(oldVar);
            defaultGroup.putAll(config.getNodes("groups." + group));
            defaultGroup.put("info", defaultGroupInfo);
            config.setProperty("groups" + group, defaultGroup);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupInfoValue(String group, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group + ".info." + var) != null) {
            config.setProperty("groups." + group + ".info." + var, newValue);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removeGroupInfoVar(String group, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group + ".info." + var) != null) {
            config.removeProperty("groups." + group + ".info." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void addGroupWorld(String group, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group) != null) {
            if (config.getNode("groups." + group + "." + world) == null) {
                config.setProperty("groups." + group + "." + world, "");

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void editGroupWorldName(String group, String oldWorld, String newWorld) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group) != null) {
            if (config.getNode("groups." + group + "." + oldWorld) != null) {
                config.setProperty("groups." + group + "." + newWorld, config.getProperty("groups." + group + "." + oldWorld));
                config.removeProperty("groups." + group + "." + oldWorld);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void removeGroupWorld(String group, String world) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group) != null) {
            if (config.getNode("groups." + group + "." + world) != null) {
                config.removeProperty("groups." + group + "." + world);

                config.save();

                plugin.mIListener.loadConfig();
            }
        }
    }

    public void addGroupWorldVar(String group, String world, String var, String value) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group + "." + world) != null) {
            if (config.getNode("groups." + group + "." + world + ".info") != null)
                defaultGroupWorldInfo.putAll(config.getNode("groups." + group + "." + world + ".info").getAll());
            defaultGroupWorldInfo.put(var, value);
            config.setProperty("groups." + group + "." + world, defaultGroupWorldInfo);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupWorldVar(String group, String world, String oldVar, String newVar) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group + "." + world + ".info." + oldVar) != null) {
            config.setProperty("groups." + group + "." + world + ".info." + newVar, config.getProperty("groups." + group + "." + world + ".info." + oldVar));
            config.removeProperty("groups." + group + "." + world + ".info." + oldVar);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void editGroupWorldValue(String group, String world, String var, String newValue) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group + "." + world + ".info." + var) != null) {
            config.setProperty("groups." + group + "." + world + ".info." + var, newValue);
            config.save();

            plugin.mIListener.loadConfig();
        }
    }

    public void removeGroupWorldVar(String group, String world, String var) {
        Configuration config = plugin.mIConfig;

        config.load();
		plugin.mIListener.loadConfig();

        if (config.getNode("groups." + group + "." + world + ".info." + var) != null) {
            config.removeProperty("groups." + group + "." + world + ".info." + var);

            config.save();

            plugin.mIListener.loadConfig();
        }
    }
}
