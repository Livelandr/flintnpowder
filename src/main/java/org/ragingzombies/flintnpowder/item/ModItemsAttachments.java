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
package org.ragingzombies.flintnpowder.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.attachments.*;

public class ModItemsAttachments {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> SILENCER = ITEMS.register("silencer",
            () -> new Silencer(new Silencer.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BAYONET = ITEMS.register("bayonet",
            () -> new Bayonet(new Bayonet.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HIGHPROFILEOPTIC = ITEMS.register("hpoptic",
            () -> new HighProfileOptic(new HighProfileOptic.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LOWPROFILEOPTIC = ITEMS.register("lpoptic",
            () -> new LowProfileOptic(new LowProfileOptic.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BIPOD = ITEMS.register("bipod",
            () -> new Bipod(new Bipod.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
