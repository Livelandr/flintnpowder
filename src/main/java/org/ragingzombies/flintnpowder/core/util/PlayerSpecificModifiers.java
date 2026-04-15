package org.ragingzombies.flintnpowder.core.util;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.ragingzombies.flintnpowder.Flintnpowder.LOGGER;

// WIP
public class PlayerSpecificModifiers {

    public static PlayerSpecificModifiers INSTANCE = new PlayerSpecificModifiers();
    public static Map<UUID, PlayerSpecificModifierData> playerData = new HashMap<>();

    public boolean isUUIDRegistered(UUID id) {
        return playerData.containsKey(id);
    }

    public void registerUUID(UUID id) {
        if (isUUIDRegistered(id)) return;
        playerData.put(id, new PlayerSpecificModifierData());
    }

    public void deleteUUID(UUID id) {
        playerData.remove(id);
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        registerUUID(event.getEntity().getUUID());
        LOGGER.info("Player " + event.getEntity().getName().getString() + " joined, registering new PSM ID: " + event.getEntity().getUUID());
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        deleteUUID(event.getEntity().getUUID());
        LOGGER.info("Player " + event.getEntity().getName().getString() + " left, purging PSM ID: " + event.getEntity().getUUID());
    }

}
