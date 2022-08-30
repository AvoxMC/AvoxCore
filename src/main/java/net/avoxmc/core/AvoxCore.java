package net.avoxmc.core;

import org.bukkit.plugin.java.JavaPlugin;

import net.avoxmc.core.commands.AdminCommands;

public class AvoxCore extends JavaPlugin {
	@Override
	public void onEnable() {
		new AdminCommands(this, "avox");
	}
}
