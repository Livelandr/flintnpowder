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

import com.livelandr.flintcore.core.util.HookSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.ragingzombies.flintnpowder.block.entity.ModBlockEntities;
import org.ragingzombies.flintnpowder.enchantments.ModEnchantments;
import org.ragingzombies.flintnpowder.entity.ModEntities;
import org.ragingzombies.flintnpowder.item.ModCreativeModTabs;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ModItemsAttachments;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;
import org.ragingzombies.flintnpowder.loot.ModLootModifiers;
import org.ragingzombies.flintnpowder.sound.ModSounds;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Flintnpowder.MOD_ID)
public class Flintnpowder {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "flintnpowder";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Flintnpowder(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModItemsAmmo.register(modEventBus);
        ModItemsAttachments.register(modEventBus);
        ModItemsGuns.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModProjectiles.register(modEventBus);
        ModEnchantments.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        HookSystem.addHook(HookSystem.CALCULATE_RECOIL_MODIFIER_X, context1 -> {
            LivingEntity shooter = context1.getShooter();
            ItemStack gun = context1.getGun();
            float amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.COMPRENSATOR.get(), gun);

            if (amoLevel > 0) {
                shooter.addDeltaMovement(shooter.getLookAngle().multiply(-0.25F * amoLevel, -0.25F * amoLevel, -0.25F * amoLevel) );
                return 1F-0.25F*amoLevel;
            }
            return 1F;
        });
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
            event.accept(ModItems.ROCKET_FUEL);

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
