package fr.zelphix.projecta.listeners;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.connorlinfoot.titleapi.TitleAPI;

import fr.zelphix.projecta.Main;

public class PlayerListener implements Listener{
	
	private Main main;
	
	public PlayerListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		// Welcome & Worlds
		Player p = e.getPlayer();
		p.getInventory().clear();
		TitleAPI.sendTitle(p,30,30,30,"§fBienvenue dans §aProject A§f, §a" + p.getName(), "§fLe serveur est encore en §cALPHA §f! §6Installe toi !");
		
		Location spawnloc = main.getServer().getWorld("spawn").getSpawnLocation();
		
		Location spawn = new Location(main.getSpawn(), spawnloc.getX(), spawnloc.getY(), spawnloc.getZ());
		p.teleport(spawn);
		
		// Spawn Items
		ItemStack menu = new ItemStack(Material.COMPASS, 1);
		ItemMeta menum = menu.getItemMeta();
		menum.setDisplayName("§6> Naviguation <");
		menum.setLore(Arrays.asList("§7Te permet de naviguer entre tous les menus !"));
		menu.setItemMeta(menum);
		
		p.getInventory().setItem(4, menu);
	}
	
	@EventHandler
	public void onInterract(PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if(it == null) return;
		
		if(it.getType() == Material.COMPASS && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§6> Naviguation <")){
			if(action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR){
				
				Inventory menuinv = Bukkit.createInventory(null, 63, "§7Naviguation");
				
				// Game Selector
				ItemStack hungergame = new ItemStack(Material.IRON_SWORD, 1);
				ItemMeta hungergamem = hungergame.getItemMeta();
				hungergamem.setDisplayName("§fHunger Games | §cAlpha");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§7En gros c'est Fortnite sur Minecraft :kappa:");
				lore.add("§7Joueurs en jeu : §e0");
				hungergamem.setLore(lore);
				hungergame.setItemMeta(hungergamem);

				menuinv.setItem(31, hungergame);
				
				p.openInventory(menuinv);
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		
		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		
		if(current == null) return;
		
		if(inv.getName().equalsIgnoreCase("§7Naviguation")){
			
			e.setCancelled(true);
			
			if(current.getType() == Material.IRON_SWORD){
				if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§fHunger Games | §cAlpha")){
					p.sendMessage(main.getPrefix() + "§cCe jeu est encore en développement.");
					p.closeInventory();
					
				}
			}
		}
	}
	
	

}
