package de.wi2020sebgroup1.nachhilfe.minecraft;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		System.out.println("Enabling...");
		new Commands(this);
		System.out.print("done");
	}
	
	@Override
	public void onDisable() {
		System.out.println("Disabling...");
		System.out.print("done");
	}
	
}
