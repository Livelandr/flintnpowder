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

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> FNPTABICON = ITEMS.register("fnptabicon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAMROD = ITEMS.register("ramrod",
            () -> new Item(new Item.Properties().stacksTo(1)));


    public static final RegistryObject<Item> CAST_IRON_INGOT = ITEMS.register("cast_iron_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_IRON_ALLOY = ITEMS.register("cast_iron_alloy",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_ALLOY = ITEMS.register("steel_alloy",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_IRON_NUGGET = ITEMS.register("cast_iron_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLINTLOCK_MECHANISM = ITEMS.register("flintlock_mechanism",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MATCHLOCK_MECHANISM = ITEMS.register("matchlock_mechanism",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_BARREL = ITEMS.register("steel_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_HEAVY_BARREL = ITEMS.register("steel_heavy_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZING_BRASS = ITEMS.register("blazing_brass",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZING_POWDER = ITEMS.register("blazing_powder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_IRON_BARREL = ITEMS.register("cast_iron_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_IRON_HEAVY_BARREL = ITEMS.register("cast_iron_heavy_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_GUN_FURNITURE = ITEMS.register("wooden_gun_furniture",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZING_BRASS_ALLOY = ITEMS.register("blazing_brass_alloy",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZE_LOCK_MECHANISM = ITEMS.register("blaze_lock_mechanism",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZING_BRASS_BARREL = ITEMS.register("blazing_brass_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZING_BRASS_HEAVY_BARREL = ITEMS.register("blazing_brass_heavy_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OIL_FLASK = ITEMS.register("oil_flask",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUNMETAL_INGOT = ITEMS.register("gunmetal_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUNMETAL_ALLOY = ITEMS.register("gunmetal_alloy",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUN_METAL_BARREL = ITEMS.register("gun_metal_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GUN_METAL_HEAVY_BARREL = ITEMS.register("gun_metal_heavy_barrel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDLOCK_MECHANISM = ITEMS.register("endlock_mechanism",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PISTOL_GRIP = ITEMS.register("pistol_grip",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PALLADIUM_GREEN = ITEMS.register("palladium_green",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> CAST_IRON_BORE_DRILL = ITEMS.register("cast_iron_bore_drill",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_BORE_DRILL = ITEMS.register("diamond_bore_drill",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_BORE_DRILL = ITEMS.register("netherite_bore_drill",
            () -> new Item(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
