package me.zap.blockbreaknms.listeners;

import me.zap.blockbreaknms.packets.BlockBreakPacket;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

import static me.zap.blockbreaknms.BlockBreakNMS.plugin;

public class BlockBreakListener implements Listener {
    private final Map<Location, BukkitRunnable> breakTasks = new HashMap<>();
    private final Map<Location, Integer> breakStates = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Location blockLocation = block.getLocation();

            // If there's already a break task for this block, get it
            BukkitRunnable breakTask = breakTasks.get(blockLocation);

            if (breakTask == null || breakTask.isCancelled()) {
                breakTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        int breakState = breakStates.getOrDefault(blockLocation, 0);
                        if (breakState < 9) {
                            // Update the block break animation
                            BlockBreakPacket.sendBlockBreakPacket(event.getPlayer(), blockLocation, breakState);
                            breakStates.put(blockLocation, breakState + 1);
                        } else {
                            block.setType(Material.AIR);
                            breakTasks.remove(blockLocation);
                            breakStates.remove(blockLocation);
                            this.cancel();
                        }
                    }
                };

                breakTask.runTaskTimer(plugin, 0L, 10L);
                breakTasks.put(blockLocation, breakTask);
            } else {
                breakStates.put(blockLocation, breakStates.getOrDefault(blockLocation, 0));
            }
        }
    }
}