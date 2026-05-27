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
package org.ragingzombies.flintnpowder.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Enchantment> GHOST_LOADING =
            ENCHANTMENTS.register("ghost_loading", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });


    public static final RegistryObject<Enchantment> QUALITY_PROPELLANT =
            ENCHANTMENTS.register("quality_propellant", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });

    public static final RegistryObject<Enchantment> TRIGGER_FINGER =
            ENCHANTMENTS.register("trigger_finger", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });

    public static final RegistryObject<Enchantment> SWIFT_RELOAD =
            ENCHANTMENTS.register("swift_reload", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });


    public static final RegistryObject<Enchantment> RAMROD_MASTERY =
            ENCHANTMENTS.register("ramrod_mastery", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });


    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
