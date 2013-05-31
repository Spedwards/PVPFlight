package com.theaetherserver.pvpflight;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.theaetherserver.pvpflight.config.Config;

public class Main extends JavaPlugin implements Listener{
	protected final static Logger logger = Logger.getLogger("Minecraft");
	public static final String name = "PVP Flight";
	private final CreativePVP creativepvp = new CreativePVP(this);
	private static Main instance;

	PluginDescriptionFile pdfFile = this.getDescription();
	public static boolean update = false;
	public static String named = "";
	public static long size = 0;

	@Override
	public void onEnable(){
		instance = this;
		
		Updater updater = new Updater(this, "pvp-flight", this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
		update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; // Determine if there is an update ready for us
		named = updater.getLatestVersionString(); // Get the latest version
		size = updater.getFileSize(); // Get latest size


		PluginManager pm = getServer().getPluginManager();


		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}

		getCommand("pvpflight").setExecutor(new Commands());

		pm.registerEvents(this, this);

		if(getConfig().getBoolean("disable-creative-pvp") == true){
			pm.registerEvents(creativepvp, this);
		}else{
			logger.info("[PVP Flight] Creative in pvp will not be disabled.");
		}

		Config.loadConfig();
	}
	
	public void onDisable(){
		instance = null;
	}
	
	public static Main inst(){
		return instance;
	}

	public boolean isDouble(String str){
		try{
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

	// UPDATER
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(commandLabel.equalsIgnoreCase("updatepvpflight")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "] " + ChatColor.YELLOW;
				if(player.hasPermission("pvpflight.update")){
					@SuppressWarnings("unused")
					Updater updater = new Updater(this, "pvp-flight", this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true); // Go straight to downloading, and announce progress to console.
					player.sendMessage(prefix + "Now updating. Progress is reporting to console.");
					return true;
				}
			}else{
				sender.sendMessage("Please run this command in game.");
				return true;
			}
		}
		return false;
	}

	// EVENTS
	@EventHandler
	public void login(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "] " + ChatColor.YELLOW;
		if(player.hasPermission("pvpflight.update") && update){
			player.sendMessage(prefix + "An update is available: " + named + " (" + size + " bytes)");
			player.sendMessage(prefix + "Type " + ChatColor.GOLD + "/updatepvpflight" + ChatColor.YELLOW + " if you would like to update.");
		}
	}


	@EventHandler
	public void onFlyDisableBecausePlayerGotHit(EntityDamageByEntityEvent event){
		if(getConfig().getBoolean("disable-flight-pvp") == true){
			Entity damager = event.getDamager();
			Entity damaged = event.getEntity();
			if(damager instanceof Player && damaged instanceof Player){ // Damager loses flight.
				Player player = (Player)damager;
				if(player.hasPermission("pvpflight.bypass.outmelee")){
					// Player keeps flight
				}else{
					if(player.getAllowFlight() == true){
						player.setAllowFlight(false); 
						player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + "]" + ChatColor.YELLOW + " Flight has been disabled due to pvp.");
					}
				}
			}


			if(damager instanceof Projectile){
				Projectile projectile = (Projectile)damager;
				if(damaged instanceof Player && projectile.getShooter() instanceof Player){ // Damager using projectile loses flight.
					Player player = (Player)projectile.getShooter();
					if(player.hasPermission("pvpflight.bypass.outproj")){
						// Player keeps flight
					}else{
						if(player.getAllowFlight() == true){
							player.setAllowFlight(false); 
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + "]" + ChatColor.YELLOW + " Flight has been disabled due to pvp.");
						}
					}
				}
			}

			if(damager instanceof Projectile && damaged instanceof Player){ // Receiver hit by projectile loses flight.
				Player player = (Player)damaged;
				if(player.getAllowFlight()){
					if(player.hasPermission("pvpflight.bypass.inproj")){
						// Player keeps flight
					}else{
						if(player.getAllowFlight() == true){
							player.setAllowFlight(false); 
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + "]" + ChatColor.YELLOW + " Flight has been disabled due to pvp.");
						}
					}
				}
			}
			if(damager instanceof Player && damaged instanceof Player){ // Receiver loses flight.
				Player player = (Player)damaged;
				if(player.getAllowFlight()){
					if(player.hasPermission("pvpflight.bypass.inmelee")){
						// Player keeps flight
					}else{
						if(player.getAllowFlight() == true){
							player.setAllowFlight(false); 
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + "]" + ChatColor.YELLOW + " Flight has been disabled due to pvp.");
						}
					}
				}
			}
		}
	}


	public static void log(String txt) {
		logger.log(Level.INFO, String.format("[%s] %s", name, txt));
	}
}
