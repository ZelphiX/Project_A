package fr.zelphix.projecta.ranks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

public enum Ranks {
	
	JOUEUR(0, "§7", ChatColor.GRAY),
	VIP(10, "§eVIP", ChatColor.YELLOW),
	MODO(50, "§aModérateur", ChatColor.GREEN),
	ADMIN(100, "§cAdmin", ChatColor.WHITE);
	
	private int power;
	private String displayName;
	private ChatColor colorTag;
	public static Map<Integer, Ranks> rank = new HashMap<>();
	
	Ranks(int power, String displayName, ChatColor tag){
		this.power = power;
		this.displayName = displayName;
		this.colorTag = tag;
	}
	
	static{
		for(Ranks r : Ranks.values()){
			rank.put(r.getPower(), r);
		}
	}
	
	public int getPower(){
		return power;
	}
	
	public String getName(){
		return displayName;
	}
	
	public ChatColor getTag(){
		return colorTag;
	}
	
	public static Ranks powerToRank(int power){
		return rank.get(power);
	}

}
