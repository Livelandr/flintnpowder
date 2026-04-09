package org.ragingzombies.flintnpowder.core.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.core.network.packets.S2C_RecoilCameraOffsetPacket;


public class PacketHandler {
    private static int packetId = 0;
    private static final String PROTOCOL_VERSION = "1";

    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(Flintnpowder.MOD_ID, "main"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions((version) -> true)
            .serverAcceptedVersions((version) -> true)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(S2C_RecoilCameraOffsetPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2C_RecoilCameraOffsetPacket::encode)
                .decoder(S2C_RecoilCameraOffsetPacket::new)
                .consumerMainThread(S2C_RecoilCameraOffsetPacket::handler)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
