package net.avoxmc.core.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.avoxmc.core.AvoxCore;
import net.avoxmc.core.commands.listeners.AdminCommandTabCompleter;
import net.avoxmc.core.utils.CoreUtils;
import net.avoxmc.core.utils.DebugUtils;
import net.avoxmc.core.utils.chat.EmoticonType;
import net.avoxmc.core.utils.chat.Emoticons;

public class AdminCommands implements CommandExecutor {

	public AdminCommands(AvoxCore plugin, String... cmd) {
		for (String s : cmd) {
			PluginCommand com = plugin.getCommand(s);
			com.setExecutor(this);
			com.setTabCompleter(new AdminCommandTabCompleter());
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("avox")) {
			if (!sender.hasPermission("avocmx.admin")) {
				sender.sendMessage(
						CoreUtils.prefixes("admin") + CoreUtils.colorize("You don't have permission for that."));
				return true;
			}
			if (args.length == 0) {
				return true;
			}
			if (args[0].equalsIgnoreCase("update")) {
				String plugin = args.length == 1 ? "AvoxCore" : args[1];
				String filename = plugin + ".jar";
				String url = "https://ci.quickscythe.com/job/" + plugin + "/lastSuccessfulBuild/artifact/target/"
						+ filename;
				sender.sendMessage(CoreUtils.prefixes("admin") + "Downloading " + filename + "...");
				if (CoreUtils.downloadFile(url, "plugins/" + filename, "QuickScythe", "r6Pt#BF#Lg73@s4t"))
					sender.sendMessage(
							CoreUtils.prefixes("admin") + CoreUtils.colorize("Finished downloading " + filename));
				else {
					sender.sendMessage(CoreUtils.prefixes("admin") + CoreUtils
							.colorize("There was an error downloading " + filename + ". Trying alt site..."));
					if (CoreUtils.downloadFile("https://downloads.mysticcloud.net/" + filename, "plugins/" + filename,
							"admin", "v4pob8LW"))
						sender.sendMessage(
								CoreUtils.prefixes("admin") + CoreUtils.colorize("Finished downloading " + filename));
					else {
						sender.sendMessage(CoreUtils.prefixes("admin") + CoreUtils
								.colorize("There was an error downloading " + filename + ". Trying alt site..."));
					}
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("debug")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("avoxmc.debug")) {
					if (args.length == 0) {
						if (DebugUtils.isDebugger(((Player) sender).getUniqueId())) {
							sender.sendMessage(CoreUtils.prefixes("debug") + "Removing you from the Debug Group");
							DebugUtils.removeDebugger(((Player) sender).getUniqueId());
							return true;
						}
						sender.sendMessage(CoreUtils.prefixes("debug") + "Adding you to the Debug Group");
						DebugUtils.addDebugger(((Player) sender).getUniqueId());
						return true;

					}

					if (args.length >= 1) {
						if (args[0].equalsIgnoreCase("emoticons")) {
							if (args.length == 1) {
								for (Emoticons emote : Emoticons.values())
									sender.sendMessage(emote.name() + ": " + emote);

							}
							if (args.length == 2) {
								for (Emoticons emote : Emoticons.values()) {
									if (EmoticonType.valueOf(args[1].toUpperCase()) != null) {
										if (emote.getTypes().contains(EmoticonType.valueOf(args[1].toUpperCase()))) {
											sender.sendMessage(emote.name() + ": " + emote);
										}
									} else if (emote.name().contains(args[1].toUpperCase()))
										sender.sendMessage(emote.name() + ": " + emote);
								}
							}
						}
						if (args.length == 2) {
							if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")
									|| args[0].equalsIgnoreCase("add")) {
								if (Bukkit.getPlayer(args[1]) == null) {
									sender.sendMessage(CoreUtils.prefixes("debug") + "That player is not online.");
									return true;
								}
								if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
									UUID uid = Bukkit.getPlayer(args[1]).getUniqueId();
									if (DebugUtils.isDebugger(uid)) {
										DebugUtils.removeDebugger(uid);
										Bukkit.getPlayer(uid).sendMessage(
												CoreUtils.prefixes("debug") + "Removing you from the Debug Group");
									} else
										sender.sendMessage(
												CoreUtils.prefixes("debug") + "That player isn't in the Debug Group");

									return true;
								}
								if (args[0].equalsIgnoreCase("add")) {
									UUID uid = Bukkit.getPlayer(args[1]).getUniqueId();
									if (!DebugUtils.isDebugger(uid)) {
										DebugUtils.addDebugger(uid);
										Bukkit.getPlayer(uid).sendMessage(
												CoreUtils.prefixes("debug") + "Adding you to the Debug Group");
									} else
										sender.sendMessage(CoreUtils.prefixes("debug")
												+ "That player is already in the Debug Group");

									return true;
								}
							}
						}
					}
				}
			}
			return true;
		}
		return true;
	}
}
