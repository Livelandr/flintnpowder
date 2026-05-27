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
package org.ragingzombies.flintnpowder;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.ragingzombies.flintnpowder.core.util.PlayerSpecificModifiers;
import org.ragingzombies.flintnpowder.enchantments.ModEnchantments;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ModCreativeModTabs;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ModItemsAttachments;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;
import org.ragingzombies.flintnpowder.core.network.PacketHandler;
import org.ragingzombies.flintnpowder.sound.ModSounds;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Flintnpowder.MOD_ID)
public class Flintnpowder {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "flintnpowder";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Flintnpowder() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModItemsAmmo.register(modEventBus);
        ModItemsAttachments.register(modEventBus);
        ModItemsGuns.register(modEventBus);

        ModSounds.register(modEventBus);
        ModProjectiles.register(modEventBus);
        ModEnchantments.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PlayerSpecificModifiers.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ServerTickHandler.INSTANCE);
        modEventBus.addListener(this::addCreative);

        PacketHandler.register();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {

            event.accept(ModItems.CAST_IRON_INGOT);
            event.accept(ModItems.CAST_IRON_NUGGET);
            event.accept(ModItems.CAST_IRON_ALLOY);

            event.accept(ModItems.STEEL_INGOT);
            event.accept(ModItems.STEEL_NUGGET);
            event.accept(ModItems.STEEL_ALLOY);

            event.accept(ModItems.GUNMETAL_INGOT);
            event.accept(ModItems.GUNMETAL_ALLOY);

            event.accept(ModItems.BLAZING_POWDER);
            event.accept(ModItems.BLAZING_BRASS);
            event.accept(ModItems.BLAZING_BRASS_ALLOY);

            event.accept(ModItems.PALLADIUM_GREEN);

            event.accept(ModItems.MATCHLOCK_MECHANISM);
            event.accept(ModItems.FLINTLOCK_MECHANISM);
            event.accept(ModItems.BLAZE_LOCK_MECHANISM);
            event.accept(ModItems.ENDLOCK_MECHANISM);

            event.accept(ModItems.CAST_IRON_BARREL);
            event.accept(ModItems.CAST_IRON_HEAVY_BARREL);
            event.accept(ModItems.STEEL_BARREL);
            event.accept(ModItems.STEEL_HEAVY_BARREL);
            event.accept(ModItems.BLAZING_BRASS_BARREL);
            event.accept(ModItems.BLAZING_BRASS_HEAVY_BARREL);
            event.accept(ModItems.GUN_METAL_BARREL);
            event.accept(ModItems.GUN_METAL_HEAVY_BARREL);

            event.accept(ModItems.WOODEN_GUN_FURNITURE);
            event.accept(ModItems.PISTOL_GRIP);
            event.accept(ModItems.OIL_FLASK);

            event.accept(ModItems.CAST_IRON_BORE_DRILL);
            event.accept(ModItems.DIAMOND_BORE_DRILL);
            event.accept(ModItems.NETHERITE_BORE_DRILL);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Starting Flint'n'Powder...");
    }

}
