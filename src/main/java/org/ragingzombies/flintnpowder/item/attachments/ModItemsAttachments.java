package org.ragingzombies.flintnpowder.item.attachments;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;

public class ModItemsAttachments {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> SILENCER = ITEMS.register("silencer",
            () -> new Silencer(new Silencer.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BAYONET = ITEMS.register("bayonet",
            () -> new Bayonet(new Bayonet.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
