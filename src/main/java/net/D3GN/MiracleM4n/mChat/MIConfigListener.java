package net.D3GN.MiracleM4n.mChat;

import java.io.File;
import java.util.TreeMap;

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

        plugin.mIReader.defaultUserWorldInfo.put("prefix", "&2DtKTest");
        plugin.mIReader.defaultUserWorld.put("DtK", plugin.mIReader.defaultUserWorldInfo);
        plugin.mIReader.defaultUserInfo.put("suffix", "&2Suffix");
        plugin.mIReader.defaultUser.put("group", "admin");
        plugin.mIReader.defaultUser.put("worlds", plugin.mIReader.defaultUserWorld);
        plugin.mIReader.defaultUser.put("info", plugin.mIReader.defaultUserInfo);
        config.setProperty("users.MiracleM4n", plugin.mIReader.defaultUser);

        plugin.mIReader.defaultGroupWorldInfo.put("prefix", "&3DtKTest2");
        plugin.mIReader.defaultGroupWorld.put("DtK", plugin.mIReader.defaultGroupWorldInfo);
        plugin.mIReader.defaultGroupInfo.put("prefix", "&4Admin");
        plugin.mIReader.defaultGroupInfo.put("custVar", "");
        plugin.mIReader.defaultGroup.put("worlds", plugin.mIReader.defaultGroupWorld);
        plugin.mIReader.defaultGroup.put("info", plugin.mIReader.defaultGroupInfo);
        config.setProperty("groups.admin", plugin.mIReader.defaultGroup);

		config.save();
	}

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
            plugin.mIReader.defaultUserWorldInfo.put("prefix", "&2DtKTest");
            plugin.mIReader.defaultUserWorld.put("DtK", plugin.mIReader.defaultUserWorldInfo);
            plugin.mIReader.defaultUserInfo.put("suffix", "&2Suffix");
            plugin.mIReader.defaultUser.put("group", "admin");
            plugin.mIReader.defaultUser.put("worlds", plugin.mIReader.defaultUserWorld);
            plugin.mIReader.defaultUser.put("info", plugin.mIReader.defaultUserInfo);
            config.setProperty("users.MiracleM4n", plugin.mIReader.defaultUser);

			config.save();
		}

		if (config.getProperty("groups") == null) {
			config.setHeader(
		            "# mChat Info config",
		            "");
            plugin.mIReader.defaultGroupWorldInfo.put("prefix", "&3DtKTest2");
            plugin.mIReader.defaultGroupWorld.put("DtK", plugin.mIReader.defaultGroupWorldInfo);
            plugin.mIReader.defaultGroupInfo.put("prefix", "&4Admin");
            plugin.mIReader.defaultGroupInfo.put("custVar", "");
            plugin.mIReader.defaultGroup.put("worlds", plugin.mIReader.defaultGroupWorld);
            plugin.mIReader.defaultGroup.put("info", plugin.mIReader.defaultGroupInfo);
            config.setProperty("groups.admin", plugin.mIReader.defaultGroup);

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
        groupNodeList.putAll(config.getNode("groupnames").getAll());
        worldNodeList.putAll(config.getNode("worldnames").getAll());
    }
}
	


