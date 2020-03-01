package fr.zelphix.projecta;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.zelphix.projecta.coins.Coins;
import fr.zelphix.projecta.coins.CoinsCmd;
import fr.zelphix.projecta.listeners.JoinListener;
import fr.zelphix.projecta.listeners.PlayerListener;
import fr.zelphix.projecta.ranks.RankCmd;
import fr.zelphix.projecta.ranks.RankListener;

public class Main extends JavaPlugin{
	
	private SQLConnection sql;
	
	private World spawn = Bukkit.getWorld("spawn");
	private String prefix = "§8[§9Project A§8] §f";
	
	@Override
	public void onEnable() {
		System.out.println(prefix + "Hello world!");
		
		// SQL
		sql = new SQLConnection("jdbc:mysql://", "localhost", "projecta", "root", "");
		sql.connection();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new Coins(this), this);
		pm.registerEvents(new RankListener(this), this);
		getCommand("coins").setExecutor(new CoinsCmd(sql, this));
		getCommand("rank").setExecutor(new RankCmd(sql, this));
		
	}
	
	@Override
	public void onDisable() {
		sql.disconnect();
	}
	
	public World getSpawn(){
		return spawn;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public SQLConnection getSql(){
		return sql;
	}

}
