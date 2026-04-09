package org.ragingzombies.flintnpowder.core.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2C_RecoilCameraOffsetPacket {
    private float Xrot;
    private float Yrot;

    public S2C_RecoilCameraOffsetPacket(float X, float Y) {
        this.Xrot = X;
        this.Yrot = Y;
    }

    public S2C_RecoilCameraOffsetPacket(FriendlyByteBuf buffer) {
        this(buffer.readFloat(), buffer.readFloat());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(Xrot);
        buffer.writeFloat(Yrot);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        LocalPlayer player = Minecraft.getInstance().player;

        player.setXRot(player.getXRot()+Xrot);
        player.setYRot(player.getYRot()+Yrot);
    }
}
