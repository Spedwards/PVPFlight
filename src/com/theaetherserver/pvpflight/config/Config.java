package com.theaetherserver.pvpflight.config;

import java.io.File;

import com.theaetherserver.pvpflight.Main;

public class Config {
	@SuppressWarnings("unused")
	private Main plugin;
	public Config (Main plugin) {
		this.plugin = plugin;
	}

	static File configFile = new File(Main.inst().getDataFolder(), "config.yml");
	
	public static void loadConfig(){
		if(!configFile.exists()){
			Main.inst().getConfig().options().copyDefaults(true);
			Main.inst().saveConfig();
			System.out.println("Configuration created.");
		}else{
			System.out.println("Configuration already exists.");
		}
	}
	
	public static void reloadConfig(){
		Main.inst().reloadConfig();
	}	
}
