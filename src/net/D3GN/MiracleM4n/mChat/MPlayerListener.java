package net.D3GN.MiracleM4n.mChat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MPlayerListener extends PlayerListener implements Runnable {
	mChat plugin;
	
	public MPlayerListener(mChat plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("static-access")
	public void onPlayerChat(PlayerChatEvent event) {
		if (event.isCancelled()) return;
		final Player player = event.getPlayer();
		String msg = event.getMessage();
		if (msg == null) return;
		event.setFormat(plugin.API.parseChat(player, msg));
	}
	
	@SuppressWarnings("static-access")
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String msg = event.getJoinMessage();
		if (msg == null) return;
		event.setJoinMessage(plugin.API.parseJoin(player) + " " + replaceMess("joinMessage"));
	}
	
	@SuppressWarnings("static-access")
	public void onPlayerKick(PlayerKickEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		String msg = event.getLeaveMessage();
		if (msg == null) return;
		event.setLeaveMessage(plugin.API.parseJoin(player) + " " + replaceMess("kickMessage"));
	}
	
	@SuppressWarnings("static-access")
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String msg = event.getQuitMessage();
		if (msg == null) return;
		event.setQuitMessage(plugin.API.parseJoin(player) + " " + replaceMess("leaveMessage"));
	}
	
	@SuppressWarnings("static-access")
	private String replaceMess(String string) {
		if (string.equals("joinMessage")) {
			string = plugin.joinMessage;
		} else if (string.equals("kickMessage")) {
			string = plugin.kickMessage;
		} else if (string.equals("leaveMessage")) {
			string = plugin.leaveMessage;
		}
		return plugin.API.addColour(string);
	}

	public void run() {
	}
}

