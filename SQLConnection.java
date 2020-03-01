package fr.zelphix.projecta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import fr.zelphix.projecta.ranks.Ranks;

public class SQLConnection {

	private Connection connection;
	private String urlbase,host,database,user,pass;
	
	public SQLConnection(String urlbase, String host, String database, String user, String pass) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}
	
	public void connection(){
		if(!isConnected()){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
				System.out.println("Connection : ON");
			} catch (SQLException | ClassNotFoundException e) {				
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect(){
		if(isConnected()){
			try {
				connection.close();
				System.out.println("Connection : OFF");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * INSERT
	 * UPDATE
	 * DELETE
	 * SELECT
	 * 
	 * PREPARE REQUEST ?, ?
	 * REPLACE ? 
	 * EXECUTE
	 */
	
	public boolean isConnected(){
		return connection != null;
	}
	
	public void createAccount(Player p){
		if(!hasAccount(p)){
			// INSERT
			try {
				PreparedStatement q = connection.prepareStatement("INSERT INTO players(uuid, coins, rank) VALUES(?, ?, ?)");
				q.setString(1, p.getUniqueId().toString());
				q.setInt(2,  100);
				q.setInt(3, Ranks.JOUEUR.getPower());
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean hasAccount(Player p){
		// SELECT
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
			q.setString(1, p.getUniqueId().toString());
			ResultSet result = q.executeQuery();
			@SuppressWarnings("unused")
			boolean hasAccount = result.next();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getBalance(Player p){
		// SELECT
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT coins FROM players WHERE uuid = ?");
			q.setString(1, p.getUniqueId().toString());
			int balance = 0;
			ResultSet rs = q.executeQuery();
			while(rs.next()){
				balance = rs.getInt("coins");
			}
			q.close();
			
			return balance;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addCoins(Player p, int amount){
		// UPDATE
		
		int balance = getBalance(p);
		int newBalance = balance + amount;
		
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE players SET coins = ? WHERE uuid = ?");
			rs.setInt(1, newBalance);
			rs.setString(2, p.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void removeCoins(Player p, int amount){
		// UPDATE		
		
		int balance = getBalance(p);
		int newBalance = balance - amount;
		
		if (newBalance <= 0){
			return;
		}
		
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE players SET coins = ? WHERE uuid = ?");
			rs.setInt(1, newBalance);
			rs.setString(2, p.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void setRank(Player p, Ranks rank){
		
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE players SET rank = ? WHERE uuid = ?");
			rs.setInt(1, rank.getPower());
			rs.setString(2, p.getUniqueId().toString());
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Ranks getRank(Player p){
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT rank FROM players WHERE uuid = ?");
			q.setString(1, p.getUniqueId().toString());
			int power = 0;
			ResultSet rs = q.executeQuery();
			while(rs.next()){
				power = rs.getInt("rank");
			}
			q.close();
			
			return Ranks.powerToRank(power);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Ranks.JOUEUR;
	}

}
