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

    public static final RegistryObject<Item> STEELNUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
