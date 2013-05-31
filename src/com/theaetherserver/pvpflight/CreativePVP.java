package com.theaetherserver.pvpflight;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CreativePVP implements Listener{
	@SuppressWarnings("unused")
	private final Main plugin;

	public CreativePVP(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void creativePVP(EntityDamageByEntityEvent event){
		Entity damager = event.getDamager();
		Entity damaged = event.getEntity();
		GameMode c = GameMode.CREATIVE;
		GameMode s = GameMode.SURVIVAL;
		if(damager instanceof Player && damaged instanceof Player){
			Player player = (Player)damager;
			if(player.getGameMode() == c){
				if(!player.hasPermission("pvpflight.bypass.creative.outmelee")){
					player.setGameMode(s);
				}
			}
		}
		
		if(damager instanceof Projectile){
			Projectile projectile = (Projectile) damager;
			if(projectile.getShooter() instanceof Player && damaged instanceof Player){
				Player player = (Player) projectile.getShooter();
				if(player.getGameMode() == c){
					if(!player.hasPermission("pvpflight.bypass.creative.outproj")){
						player.setGameMode(s);
					}
				}
			}
		}
		
		if(damager instanceof Projectile && damaged instanceof Player){
			Player player = (Player) damaged;
			if(player.getGameMode() == c){
				if(!player.hasPermission("pvpflight.bypass.creative.inproj")){
					player.setGameMode(s);
				}
			}
		}
		
		if(damager instanceof Player && damaged instanceof Player){
			Player player = (Player) damaged;
			if(player.getGameMode() == c){
				if(player.hasPermission("pvpflight.bypass.creative.inmelee")){
					player.setGameMode(s);
				}
			}
		}
	}
}
