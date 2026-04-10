package org.ragingzombies.flintnpowder.core.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.ragingzombies.flintnpowder.core.network.PacketHandler;
import org.ragingzombies.flintnpowder.core.network.packets.S2C_RecoilCameraOffsetPacket;

public class CameraWork {
    public static void OffsetEntityCamera(LivingEntity player, float Xrot, float Yrot) {
        if (!player.level().isClientSide()) {
        PacketHandler.sendToPlayer(new S2C_RecoilCameraOffsetPacket(Xrot, Yrot), (ServerPlayer) player);
        }
    }
    // Future VR Mod compatibility investment
    public static float getPlayerViewX(LivingEntity ply) {
        return ply.getXRot();
    }
    public static float getPlayerViewY(LivingEntity ply) {
        return ply.getYRot();
    }
}
