package me.zap.blockbreaknms.packets;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BlockBreakPacket {
    public static void sendBlockBreakPacket(Player player, Location loc, int destroyStage) {
        destroyStage = Math.min(Math.max(destroyStage, 0), 9);

        // Create the packet
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
                generateEntityId(),
                new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
                destroyStage
        );

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PlayerConnection connection = entityPlayer.c;
        connection.a(packet);
    }

    private static int generateEntityId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}
