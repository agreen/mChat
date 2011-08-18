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
	TreeMap<String, Object> defaultGroup = new TreeMap<String, Object>();
	TreeMap<String, Object> defaultVar = new TreeMap<String, Object>();
	
	protected void defaultConfig() {
		Configuration config = plugin.mIConfig;
		config.save();
		config.setHeader(
	            "# mChat Info config",
	            "# Only needed if using PermissionsBukkit, superperms.",
	            "");
		defaultGroup.put("admin", "");
		defaultGroup.put("sadmin", "");
		defaultGroup.put("jadmin", "");
		defaultGroup.put("member", "");
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
		plugin.infoMap.put("group", defaultGroup);
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
			defaultGroup.put("admin", "");
			defaultGroup.put("sadmin", "");
			defaultGroup.put("jadmin", "");
			defaultGroup.put("member", "");
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
			plugin.infoMap.put("group", defaultGroup);
			plugin.infoMap.put("prefix", defaultPrefix);
			plugin.infoMap.put("suffix", defaultSuffix);
			plugin.infoMap.put("custVar", defaultVar);
			config.setProperty("mchat", plugin.infoMap);
			config.save();
		}
	}
}
	


