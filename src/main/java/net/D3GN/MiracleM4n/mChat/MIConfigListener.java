package net.D3GN.MiracleM4n.mChat;

import java.util.TreeMap;

import org.bukkit.util.config.Configuration;

public class MIConfigListener {
	mChat plugin;
	
	public MIConfigListener(mChat plugin) {
		this.plugin = plugin;
	}
	
	TreeMap<String, Object> defaultPrefix = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultSuffix = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultGroupNames = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultWorldNames = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultVar = new TreeMap<String, Object>();
    TreeMap<String, Object> groupNodeList = new TreeMap<String, Object>();
    TreeMap<String, Object> worldNodeList = new TreeMap<String, Object>();
	
	protected void defaultConfig() {
		Configuration config = plugin.mIConfig;
		config.save();
		config.setHeader(
	            "# mChat Info config",
	            "# Only needed if using PermissionsBukkit, superperms.",
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
        
		defaultPrefix.put("admin", "&4DtK [SO] &7");
		defaultPrefix.put("sadmin", "&9DtK [SA] &7");
		defaultPrefix.put("jadmin", "&aDtK [JA] &7");
		defaultPrefix.put("member", "&cDtK [M] &7");
		defaultSuffix.put("admin", "");
		defaultSuffix.put("sadmin", "");
		defaultSuffix.put("jadmin", "");
		defaultSuffix.put("member", "");
		defaultVar.put("admin", "");
		defaultVar.put("sadmin", "");
		defaultVar.put("jadmin", "");
		defaultVar.put("member", "");
		plugin.infoMap.put("prefix", defaultPrefix);
		plugin.infoMap.put("suffix", defaultSuffix);
		plugin.infoMap.put("custVar", defaultVar);
		config.setProperty("mchat", plugin.infoMap);
		config.save();
	}
	
	protected void checkConfig() {
		Configuration config = plugin.mIConfig;
		config.load();
		if (config.getProperty("mchat") == null) {
			if (plugin.otherMap != null) {
				plugin.otherMap.clear();
			}
			if (plugin.infoMap != null) {
				plugin.infoMap.clear();
			}
			config.setHeader(
		            "# mChat Info config",
		            "# Only needed if using PermissionsBukkit, superperms.",
		            "");
			defaultPrefix.put("admin", "&4DtK [SO] &7");
			defaultPrefix.put("sadmin", "&9DtK [SA] &7");
			defaultPrefix.put("jadmin", "&aDtK [JA] &7");
			defaultPrefix.put("member", "&cDtK [M] &7");
			defaultSuffix.put("admin", "");
			defaultSuffix.put("sadmin", "");
			defaultSuffix.put("jadmin", "");
			defaultSuffix.put("member", "");
			defaultVar.put("admin", "");
			defaultVar.put("sadmin", "");
			defaultVar.put("jadmin", "");
			defaultVar.put("member", "");
			plugin.infoMap.put("prefix", defaultPrefix);
			plugin.infoMap.put("suffix", defaultSuffix);
			plugin.infoMap.put("custVar", defaultVar);
			config.setProperty("mchat", plugin.infoMap);
			config.save();
		}

        if (config.getProperty("groupnames") == null) {
			config.setHeader(
		            "# mChat Info config",
		            "# Only needed if using PermissionsBukkit, superperms.",
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
		            "# Only needed if using PermissionsBukkit, superperms.",
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
	


