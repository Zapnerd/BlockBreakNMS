package me.zap.blockbreaknms;

import me.zap.blockbreaknms.listeners.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockBreakNMS extends JavaPlugin {
    public static BlockBreakNMS plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

}
