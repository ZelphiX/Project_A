package fr.zelphix.projecta.ranks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.zelphix.projecta.Main;

public class RankListener implements Listener{
	
	private Main main;
	
	public RankListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		Ranks rank = main.getSql().getRank(p);
		
		e.setFormat(rank.getName() + " " + p.getName() + " : §f" + e.getMessage());
		
	}

}
