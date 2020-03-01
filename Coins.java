package fr.zelphix.projecta.coins;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.zelphix.projecta.Main;

public class Coins implements Listener{
	
	private Main main;
	
	public Coins(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		main.getSql().createAccount(p);
	}
	

}
