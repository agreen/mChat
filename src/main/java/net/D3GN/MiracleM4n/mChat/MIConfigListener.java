package net.D3GN.MiracleM4n.mChat;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class MIConfigListener {
	mChat plugin;
	
	public MIConfigListener(mChat plugin) {
		this.plugin = plugin;
	}

	TreeMap<String, Object> defaultGroupNames = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultWorldNames = new TreeMap<String, Object>();
    TreeMap<String, Object> groupNodeList = new TreeMap<String, Object>();
    TreeMap<String, Object> worldNodeList = new TreeMap<String, Object>();

    TreeMap<String, Object> defaultUsers = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUser = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUserWorld = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUserWorldInfo = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultUserInfo = new TreeMap<String, Object>();
    
	TreeMap<String, Object> defaultGroups = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultGroup = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultGroupWorld = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultGroupWorldInfo = new TreeMap<String, Object>();
    TreeMap<String, Object> defaultGroupInfo = new TreeMap<String, Object>();

    @SuppressWarnings("unchecked")
	protected void defaultConfig() {
		Configuration config = plugin.mIConfig;
		config.save();
		config.setHeader(
	            "# mChat Info config",
	            "");
		defaultGroupNames.put("admin", "[a]");
		defaultGroupNames.put("sadmin", "[sa]");
		defaultGroupNames.put("jadmin", "[ja]");
		defaultGroupNames.put("member", "[m]");
		config.setProperty("groupnames", defaultGroupNames);
        
		defaultWorldNames.put("D3GN", "[D]");
		defaultWorldNames.put("DtK", "[DtK]");
		defaultWorldNames.put("Nether", "[N]");
		defaultWorldNames.put("Hello", "[H]");
		config.setProperty("worldnames", defaultWorldNames);

        defaultUserWorldInfo.put("prefix", "&2DtKTest");
        defaultUserWorld.put("DtK", defaultUserWorldInfo);
        defaultUserInfo.put("suffix", "&2Suffix");
        defaultUser.put("group", "admin");
        defaultUser.put("worlds", defaultUserWorld);
        defaultUser.put("info", defaultUserInfo);
        defaultUsers.put("MiracleM4n", defaultUser);
        config.setProperty("users", defaultUsers);

        defaultGroupWorldInfo.put("prefix", "&3DtKTest2");
        defaultGroupWorld.put("DtK", defaultGroupWorldInfo);
        defaultGroupInfo.put("prefix", "&4Admin");
        defaultGroupInfo.put("custVar", "");
        defaultGroup.put("worlds", defaultGroupWorld);
        defaultGroup.put("info", defaultGroupInfo);
        defaultGroups.put("admin", defaultGroup);
        config.setProperty("groups",defaultGroups);

		config.save();
	}

	@SuppressWarnings("unchecked")
	protected void checkConfig() {
        if (!(new File(plugin.getDataFolder(), "info.yml")).exists()) {
            defaultConfig();
        }

		Configuration config = plugin.mIConfig;
		config.load();
		if (config.getProperty("users") == null) {
			config.setHeader(
		            "# mChat Info config",
		            "");
            defaultUserWorldInfo.put("prefix", "&2DtKTest");
            defaultUserWorld.put("DtK", defaultUserWorldInfo);
            defaultUserInfo.put("suffix", "&2Suffix");
            defaultUser.put("group", "admin");
            defaultUser.put("worlds", defaultUserWorld);
            defaultUser.put("info", defaultUserInfo);
            defaultUsers.put("MiracleM4n", defaultUser);
            config.setProperty("users", defaultUsers);

			config.save();
		}

		if (config.getProperty("groups") == null) {
			config.setHeader(
		            "# mChat Info config",
		            "");
            defaultGroupWorldInfo.put("prefix", "&3DtKTest2");
            defaultGroupWorld.put("DtK", defaultGroupWorldInfo);
            defaultGroupInfo.put("prefix", "&4Admin");
            defaultGroupInfo.put("custVar", "");
            defaultGroup.put("worlds", defaultGroupWorld);
            defaultGroup.put("info", defaultGroupInfo);
            defaultGroups.put("admin", defaultGroup);
            config.setProperty("groups",defaultGroups);

			config.save();
		}

        if (config.getProperty("groupnames") == null) {
			config.setHeader(
		            "# mChat Info config",
		            "");
		    defaultGroupNames.put("admin", "[a]");
		    defaultGroupNames.put("sadmin", "[sa]");
		    defaultGroupNames.put("jadmin", "[ja]");
		    defaultGroupNames.put("member", "[m]");
		    config.setProperty("groupnames", defaultGroupNames);

            config.save();
        }

        if (config.getProperty("worldnames") == null) {
			config.setHeader(
		            "# mChat Info config",
		            "");
		    defaultWorldNames.put("D3GN", "[D]");
		    defaultWorldNames.put("DtK", "[DtK]");
		    defaultWorldNames.put("Nether", "[N]");
		    defaultWorldNames.put("Hello", "[H]");
		    config.setProperty("worldnames", defaultWorldNames);

            config.save();
        }
	}

    public void loadConfig() {
        Configuration config = plugin.mIConfig;
        if (groupNodeList != null) {
            groupNodeList.clear();
        }
        if (worldNodeList != null) {
            worldNodeList.clear();
        }
        if (plugin.usersMap != null) {
            plugin.usersMap.clear();
        }
        if (plugin.groupsMap != null) {
            plugin.groupsMap.clear();
        }
        plugin.usersMap.putAll(config.getNode("users").getAll());
        plugin.groupsMap.putAll(config.getNode("groups").getAll());
        groupNodeList.putAll(config.getNode("groupnames").getAll());
        worldNodeList.putAll(config.getNode("worldnames").getAll());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addDefaultPlayer(Player player) {
        Configuration config = plugin.mIConfig;
        String pName = player.getName();

        config.load();

        defaultUserInfo.put("prefix", "&2Prefix");
        defaultUser.put("group", "default");
        defaultUser.put("info", defaultUserInfo);
        defaultUsers.put(pName, defaultUser);
        if (config.getProperty("users") != null)
            defaultUsers.putAll((HashMap) config.getProperty("users"));
        config.setProperty("users", defaultUsers);

        if (config.getNode("groups.default") == null) {
            defaultGroupInfo.put("prefix", "&4Prefix");
            defaultGroupInfo.put("suffix", "&4Suffix");
            defaultGroup.put("info", defaultGroupInfo);
            defaultGroups.put("default", defaultGroup);
            if (config.getProperty("groups") != null)
                defaultGroups.putAll((HashMap) config.getProperty("groups"));
            config.setProperty("groups",defaultGroups);
        }

        config.save();

        loadConfig();
    }
}
	


