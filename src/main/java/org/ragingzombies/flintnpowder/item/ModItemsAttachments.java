package org.ragingzombies.flintnpowder.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.attachments.Bayonet;
import org.ragingzombies.flintnpowder.item.attachments.HighProfileOptic;
import org.ragingzombies.flintnpowder.item.attachments.LowProfileOptic;
import org.ragingzombies.flintnpowder.item.attachments.Silencer;

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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
