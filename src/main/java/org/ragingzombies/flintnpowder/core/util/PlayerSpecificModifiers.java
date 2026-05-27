/*
 * Copyright (C) 2026 Livelandr
 *
 * This file is part of Flint'N'Powder.
 *
 * Flint'N'Powder is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Flint'N'Powder is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.ragingzombies.flintnpowder.core.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.ragingzombies.flintnpowder.Flintnpowder.LOGGER;

// WIP
public class PlayerSpecificModifiers {

    public static PlayerSpecificModifiers INSTANCE = new PlayerSpecificModifiers();
    public static Map<UUID, PlayerSpecificModifierData> playerData = new HashMap<>();

    public static boolean isUUIDRegistered(UUID id) {
        return playerData.containsKey(id);
    }

    public static Player getPlayerByUUID(UUID id) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return null;
        }

        return server.getPlayerList().getPlayer(id);
    }

    public static void setPSMDamage(UUID id, float stat) {
        if (isUUIDRegistered(id)) {
            playerData.get(id).damageModifier = stat;
        }
    }
    public static void setPSMRecoil(UUID id, float stat) {
        if (isUUIDRegistered(id)) {
            playerData.get(id).recoilModifier = stat;
        }
    }
    public static void setPSMAccuracy(UUID id, float stat) {
        if (isUUIDRegistered(id)) {
            playerData.get(id).accuracyModifier = stat;
        }
    }

    public static float getPSMDamage(UUID id) {
        if (!isUUIDRegistered(id)) {
            return playerData.get(id).damageModifier;
        }
        return 1;
    }

    public static float getPSMRecoil(UUID id) {
        if (!isUUIDRegistered(id)) {
            return playerData.get(id).recoilModifier;
        }
        return 1;
    }


    public static float getPSMAccuracy(UUID id) {
        if (!isUUIDRegistered(id)) {
            return playerData.get(id).accuracyModifier;
        }
        return 1;
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
