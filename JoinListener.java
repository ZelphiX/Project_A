package fr.zelphix.projecta.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		if(!p.isOp()){
			e.setJoinMessage(null);
		}else{
			e.setJoinMessage("§c[Admin] " + p.getDisplayName() + " §6a rejoint le lobby !");
		}
	}

}
