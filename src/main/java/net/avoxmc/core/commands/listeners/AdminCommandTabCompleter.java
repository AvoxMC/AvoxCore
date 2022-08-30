package net.avoxmc.core.commands.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class AdminCommandTabCompleter implements TabCompleter {

	Map<String, List<String>> cmds = new HashMap<>();

	public AdminCommandTabCompleter() {
		List<String> lsc = new ArrayList<>();
		lsc.add("update");
//		lsc.add("reload");
		cmds.put("avox", lsc);

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> completions = new ArrayList<>();
		if (args.length == 1)
			if (cmd.getName().equalsIgnoreCase("avox")) {
				StringUtil.copyPartialMatches(args[0], cmds.get("avox"), completions);
			}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("reload")) {
				StringUtil.copyPartialMatches(args[1], cmds.get("avox.reload"), completions);
			}
		}

		return completions;

	}

	public List<String> getOnlinePlayers() {
		List<String> players = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			players.add(player.getName());
		}
		return players;
	}

}
