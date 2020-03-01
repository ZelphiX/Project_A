package fr.zelphix.projecta.ranks;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.zelphix.projecta.Main;
import fr.zelphix.projecta.SQLConnection;

public class RankCmd implements CommandExecutor {
	
	private Main main;
	private SQLConnection sql;
	
	public RankCmd(SQLConnection sql, Main main) {
		this.sql = sql;
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		Player p = (Player) sender;
		
		if(sender instanceof Player){
			if(cmd.getName().equalsIgnoreCase("rank")){
				if(args.length == 0){
					Ranks rank = sql.getRank(p);
					p.sendMessage(main.getPrefix() + "§6Tu es actuellement : " + rank.getTag() + rank);
				}
			}
			
			if(args.length >= 1){
				if(args[0].equalsIgnoreCase("set")){
					if(args.length == 1 || args.length == 2){
						p.sendMessage("§cErreur. Syntaxe : /rank set <joueur> <grade>");
					}
					
					if(args.length == 3){
						@SuppressWarnings("deprecation")
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null){
							int rank = Integer.valueOf(args[2]);
							Ranks selectedRank = Ranks.powerToRank(rank);
							sql.setRank(target, selectedRank);
							target.sendMessage(main.getPrefix() + "§bVous venez d'être promu au rang " + selectedRank.getTag() + selectedRank);
						}
					}
				}
			}
		}
		
		return false;
	}

}
