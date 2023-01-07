package de.wi2020sebgroup1.nachhilfe.minecraft;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	private Main plugin;
	
	public Commands(Main plugin) {
		plugin.getCommand("logik").setExecutor(this);
		this.plugin = plugin;
	}
	
	public boolean passed;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		System.out.println("yay es geht los: "+args.length);
		
		if(args.length == 2) {
			String name = args[0];
			String level = args[1];
			System.out.println("yay");
			if(level.equalsIgnoreCase("1")) {
				passed = true;
				p.sendMessage("Level 1 wird geprüft...");
				Block s1 = p.getWorld().getBlockAt(-135, 64, 525);
				Block s2 = p.getWorld().getBlockAt(-137, 64, 525);
				Block r = p.getWorld().getBlockAt(-136, 64, 530);
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					s1.setType(Material.BRICK);
					s2.setType(Material.BRICK);
					if(r.isBlockPowered()) {
						passed = false;
						p.sendMessage("Test 1 misslungen");
					} else {
						p.sendMessage("Test 1 geschafft!");
					}
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						s1.setType(Material.REDSTONE_BLOCK);
						s2.setType(Material.BRICK);
						if(r.isBlockPowered()) {
							passed = false;
							p.sendMessage("Test 2 misslungen");
						} else {
							p.sendMessage("Test 2 geschafft!");
						}
						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							s1.setType(Material.BRICK);
							s2.setType(Material.REDSTONE_BLOCK);
							if(r.isBlockPowered()) {
								passed = false;
								p.sendMessage("Test 3 misslungen");
							} else {
								p.sendMessage("Test 3 geschafft!");
							}
							Bukkit.getScheduler().runTaskLater(plugin, () -> {
								s1.setType(Material.REDSTONE_BLOCK);
								s2.setType(Material.REDSTONE_BLOCK);
								if(r.isBlockPowered()) {
									passed = false;
									p.sendMessage("Test 4 misslungen");
								} else {
									p.sendMessage("Test 4 geschafft!");
								}
								Bukkit.getScheduler().runTaskLater(plugin, () -> {
									s1.setType(Material.BRICK);
									s2.setType(Material.BRICK);
									if(passed) {
										p.sendMessage("Level 1 bestanden! Die Challenge wird als 'Geschafft' markiert.");
										//saveInDB(level, name);
									}
								}, 20L);
							}, 20L);
						}, 20L);
					}, 20L);
				}, 20L);
			}
		}
		
		return false;
	}
	
	private void saveInDB(String level, String name) {
		
		try {
			URL url = new URL("http://localhost:4000/stats/"+name);
			String query = "{'id': '','userId': '','learningPoints': 0,'teachingPoints': 0,'profilePoints': 0,'mc1': 0,'mc2': 0,'mc3': 0}";
			if(level.equalsIgnoreCase("1")) {
				query = "{'id': '','userId': '','learningPoints': 0,'teachingPoints': 0,'profilePoints': 0,'mc1': 100,'mc2': 0,'mc3': 0}";
			}
			if(level.equalsIgnoreCase("2")) {
				query = "{'id': '','userId': '','learningPoints': 0,'teachingPoints': 0,'profilePoints': 0,'mc1': 0,'mc2': 100,'mc3': 0}";
			}
			if(level.equalsIgnoreCase("3")) {
				query = "{'id': '','userId': '','learningPoints': 0,'teachingPoints': 0,'profilePoints': 0,'mc1': 0,'mc2': 0,'mc3': 100}";
			}
			System.out.println(query);
			query=query.replace("'", "\"");
			System.out.println(query);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setDoOutput(true);
			conn.setAllowUserInteraction(false);
			System.out.println(conn.toString());
			PrintStream ps = new PrintStream(conn.getOutputStream());
			ps.print(query);
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
