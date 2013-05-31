package com.theaetherserver.pvpflight;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("pvpflight")){
			if(player.hasPermission("pvpflight.check")){
				if(args.length == 0){
					player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]");
					player.sendMessage(ChatColor.RED + "inmelee:" + ChatColor.YELLOW + " Checks if you will lose flight from incoming melee damage.");
					player.sendMessage(ChatColor.RED + "outmelee:" + ChatColor.YELLOW + " Checks if you will lose flight from outgoing melee damage.");
					player.sendMessage(ChatColor.RED + "inproj:" + ChatColor.YELLOW + " Checks if you will lose flight from incoming projectile damage.");
					player.sendMessage(ChatColor.RED + "outproj:" + ChatColor.YELLOW + " Checks if you will lose flight from outgoing projectile damage.");
					return true;
				}else if(args.length == 1){
					if(args[0].equalsIgnoreCase("inmelee")){
						if(player.hasPermission("pvpflight.bypass.inmelee")){
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will not lose flight from incoming melee damage.");
							return true;
						}else{
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will lose flight from incoming melee damage.");
							return true;
						}
					}else if(args[0].equalsIgnoreCase("outmelee")){
						if(player.hasPermission("pvpflight.bypass.outmelee")){
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will not lose flight from outgoing melee damage.");
							return true;
						}else{
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will lose flight from outgoing melee damage.");
							return true;
						}
					}else if(args[0].equalsIgnoreCase("inproj")){
						if(player.hasPermission("pvpflight.bypass.inproj")){
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will not lose flight from incoming projectile damage.");
							return true;
						}else{
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will lose flight from incoming projectile damage.");
							return true;
						}
					}else if(args[0].equalsIgnoreCase("outproj")){
						if(player.hasPermission("pvpflight.bypass.outproj")){
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will not lose flight from outgoing projectile damage.");
							return true;
						}else{
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " You will lose flight from outgoing projectile damage.");
							return true;
						}
					}
				}else if(args.length >= 1){
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "PVP Flight" + ChatColor.GOLD + "]" + ChatColor.YELLOW + " Command Usage: /pvpflight");				
				}
			}
		}
		return true;
	}

}