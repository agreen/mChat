package net.D3GN.MiracleM4n.mChat;

import org.bukkit.util.config.Configuration;

import java.io.File;

public class MCConfigListener {
    mChat plugin;

    public MCConfigListener(mChat plugin) {
        this.plugin = plugin;
    }

    protected void loadConfig() {
        if (!(new File(plugin.getDataFolder(), "censor.yml")).exists()) {
            defaultConfig();
        }

        Configuration config = plugin.mCConfig;
        config.load();

        plugin.censorMap.putAll(config.getAll());
    }

    protected void defaultConfig() {
        Configuration config = plugin.mCConfig;
        config.save();
        config.setHeader(
            "# mChat Censor file",
            ""
        );

        config.setProperty("fuck", "fawg");
        config.setProperty("cunt", "punt");
        config.setProperty("shit", "feces");
        config.setProperty("dick", "LARGE PENIS");
        config.setProperty("miracleman", "MiracleM4n");
        config.save();
    }
}
