package net.D3GN.MiracleM4n.mChat;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.config.Configuration;

import java.io.File;

public class MConfigListener {
    mChat plugin;
    Boolean hasChanged = false;

    public MConfigListener(mChat plugin) {
        this.plugin = plugin;
    }

    protected void loadConfig() {
        Configuration config = plugin.mConfig;
        config.load();

        plugin.chatFormat = config.getString("mchat-message-format", plugin.chatFormat);
        plugin.nameFormat = config.getString("mchat-name-format", plugin.nameFormat);
        plugin.joinFormat = config.getString("mchat-playerEvent-format", plugin.joinFormat);
        plugin.dateFormat = config.getString("mchat-date-format", plugin.dateFormat);
        plugin.joinMessage = config.getString("mchat-join-message", plugin.joinMessage);
        plugin.leaveMessage = config.getString("mchat-leave-message", plugin.leaveMessage);
        plugin.kickMessage = config.getString("mchat-kick-message", plugin.kickMessage);
        plugin.mAPI_Only_Mode = config.getBoolean("mchat-API-only", plugin.mAPI_Only_Mode);
        plugin.mChat_Info_Only = config.getBoolean("mchat-info-only", plugin.mChat_Info_Only);
        plugin.mFormat_Events = config.getBoolean("mchat-format-events", plugin.mFormat_Events);
        plugin.chatDistance = config.getDouble("mchat-chat-distance", plugin.chatDistance);
        plugin.useAddDefault = config.getBoolean("mchat-add-info-players", plugin.useAddDefault);
    }

    protected void defaultConfig() {
        Configuration config = plugin.mConfig;
        config.save();
        config.setHeader(
            "# mChat configuration file",
            "# ",
            "#           **IMPORTANT**",
            "#   usage of mchat-message-format can be, but is not limited to",
            "#       +suffix,+s, +prefix,+p, +group,+g, +world,+w, +time,+t, +name,+n, +dname,+dn, +health,+h +healthbar,+hb, +message,+msg,+m",
            "#       Suffix, Prefix, Group, World, Time, Player Name, Player Display Name, Health, Health Bar, Message",
            ""
        );

        config.setProperty("mchat-date-format", plugin.dateFormat);
        config.setProperty("mchat-message-format", plugin.chatFormat);
        config.setProperty("mchat-name-format", plugin.nameFormat);
        config.setProperty("mchat-playerEvent-format", plugin.joinFormat);
        config.setProperty("mchat-join-message", plugin.joinMessage);
        config.setProperty("mchat-leave-message", plugin.leaveMessage);
        config.setProperty("mchat-kick-message", plugin.kickMessage);
        config.setProperty("mchat-API-only", plugin.mAPI_Only_Mode);
        config.setProperty("mchat-info-only", plugin.mChat_Info_Only);
        config.setProperty("mchat-format-events", plugin.mFormat_Events);
        config.setProperty("mchat-chat-distance", plugin.chatDistance);
        config.setProperty("mchat-add-info-players", plugin.useAddDefault);
        config.save();
    }

    protected void checkConfig() {
        Configuration config = plugin.mConfig;
        config.load();

        if (!(new File(plugin.getDataFolder(), "config.yml")).exists()) {
            defaultConfig();
        }

        checkCOption(config, "mchat-date-format", plugin.dateFormat);
        checkCOption(config, "mchat-message-format", plugin.chatFormat);
        checkCOption(config, "mchat-name-format", plugin.nameFormat);
        checkCOption(config, "mchat-playerEvent-format", plugin.joinFormat);
        checkCOption(config, "mchat-join-message", plugin.joinMessage);
        checkCOption(config, "mchat-leave-message", plugin.leaveMessage);
        checkCOption(config, "mchat-kick-message", plugin.kickMessage);
        checkCOption(config, "mchat-API-only", plugin.mAPI_Only_Mode);
        checkCOption(config, "mchat-info-only", plugin.mChat_Info_Only);
        checkCOption(config, "mchat-format-events", plugin.mFormat_Events);
        checkCOption(config, "mchat-chat-distance", plugin.chatDistance);
        checkCOption(config, "mchat-add-info-players", plugin.useAddDefault);

        if (hasChanged) {
            config.setHeader(
            "# mChat configuration file",
                 "# ",
                "#           **IMPORTANT**",
                "#   usage of mchat-message-format can be, but is not limited to",
                "#       +suffix,+s, +prefix,+p, +group,+g, +world,+w, +time,+t, +name,+n, +dname,+dn, +health,+h +healthbar,+hb, +message,+msg,+m",
                "#       Suffix, Prefix, Group, World, Time, Player Name, Player Display Name, Health, Health Bar, Message",
                ""
            );

            plugin.mAPI.log("[" + plugin.pdfFile.getName() + "]" + " config.yml has been updated.");
            config.save();
        }
    }

    protected void checkCOption(Configuration config, String option, Object dOption) {
        if (config.getProperty(option) == null) {
            config.setProperty(option, dOption);
            hasChanged = true;
        }
    }
}
