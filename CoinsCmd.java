package fr.zelphix.projecta.coins;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.zelphix.projecta.Main;
import fr.zelphix.projecta.SQLConnection;

public class CoinsCmd implements CommandExecutor {
	
	private SQLConnection sql;
	private Main main;

	public CoinsCmd(SQLConnection sql, Main main) {
		this.sql = sql;
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		Player p = (Player) sender;
		
		if(sender instanceof Player){
			if(args.length == 0){
				int balance = sql.getBalance(p);
				p.sendMessage(main.getPrefix() + "§6Tu dispose actuellement §e" + balance + " §6Project A §bCoins");
			}
		}
		
		if(args.length >= 1){
			if(args[0].equalsIgnoreCase("add")){
				if(args.length == 1 || args.length == 2){
					p.sendMessage("§cErreur. Syntaxe : /coins add <joueur> <montant>");
				}
				
				if(args.length == 3){
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayer(args[1]);
					if(target != null){
						int amount = Integer.valueOf(args[2]);
						sql.addCoins(target, amount);
						target.sendMessage(main.getPrefix() + "§bVous venez de recevoir §e" + amount + " §6Project A §bCoins §6de la part de §e" + p.getName());
						target.sendMessage(main.getPrefix() + "§bVous venez d'envoyer §e" + amount + " §6Project A §bCoins §6à §e" + target.getName());
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("remove")){
				if(args.length == 1 || args.length == 2){
					p.sendMessage("§cErreur. Syntaxe : /coins remove <joueur> <montant>");
				}
				
				if(args.length == 3){
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayer(args[1]);
					if(target != null){
						int amount = Integer.valueOf(args[2]);
						sql.removeCoins(target, amount);
						target.sendMessage(main.getPrefix() + "§cVous venez de perdre §e" + amount + " §6Project A §bCoins");
						p.sendMessage(main.getPrefix() + "§cVous venez de retirer §e" + amount + " §6Project A §bCoins §cà §e" + target.getName());
					}
				}
			}
		}
		
		return false;
	}

}
