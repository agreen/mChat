package net.D3GN.MiracleM4n.mChat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.desmin88.mobdisguise.MobDisguise;

public class MPlayerListener extends PlayerListener {
	mChat plugin;
	
	public MPlayerListener(mChat plugin) {
		this.plugin = plugin;
	}

	public void onPlayerChat(PlayerChatEvent event) {
		if (event.isCancelled()) return;
        Player player = event.getPlayer();
		String pName = player.getName();
		String msg = event.getMessage();

		if (msg == null) return;

		event.setFormat(plugin.mAPI.ParseChatMessage(pName, msg));

        // For Dragonslife
        if (plugin.chatDistance > 0)
            for (Player players : plugin.getServer().getOnlinePlayers())
                if (players.getLocation().distance(player.getLocation()) > plugin.chatDistance)
					event.getRecipients().remove(players);

        if (plugin.mobD)
            if (MobDisguise.p2p.get(pName) != null) {
                pName = MobDisguise.p2p.get(pName);
                event.setFormat(plugin.mAPI.ParseChatMessage(pName, msg));
            }

        if (plugin.mAPI.ParsePlayerList(player).length() > 15) {
                String pLName = plugin.mAPI.ParsePlayerList(player);
                pLName = pLName.substring(0, 16);
                player.setPlayerListName(pLName);
        } else {
            player.setPlayerListName(plugin.mAPI.ParsePlayerList(player));
        }
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String msg = event.getJoinMessage();

		if (msg == null) return;

		event.setJoinMessage(plugin.mAPI.ParseJoinName(player) + " " + plugin.mAPI.getEventMessage("Join"));

        if (plugin.useAddDefault)
            if (plugin.mIConfig.getProperty("users." + player.getName()) == null)
                plugin.mIReader.addPlayer(player.getName(), "default");
    }

	public void onPlayerKick(PlayerKickEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
        String msg = event.getLeaveMessage();

        String reason = event.getReason();

        String kickMsg = plugin.mAPI.getEventMessage("Kick");

        kickMsg = plugin.mAPI.addColour(kickMsg.replace("+reason", reason).replace("+r", reason));

        if (msg == null) return;

		event.setLeaveMessage(plugin.mAPI.ParseJoinName(player) + " " + kickMsg);
    }
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String msg = event.getQuitMessage();

		if (msg == null) return;

		event.setQuitMessage(plugin.mAPI.ParseJoinName(player) + " " + plugin.mAPI.getEventMessage("Quit"));
	}
}

