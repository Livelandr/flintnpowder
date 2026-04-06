package org.ragingzombies.flintnpowder.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.ammo.CastIronRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.ShotgunShell;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Musket;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.SingleActionRevolver;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> FNPTABICON = ITEMS.register("fnptabicon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAMROD = ITEMS.register("ramrod",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MUSKET = ITEMS.register("musket",
            () -> new Musket(new Musket.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SINGLEACTIONREVOLVER = ITEMS.register("single_action_revolver",
            () -> new SingleActionRevolver(new SingleActionRevolver.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CASTIRONROUNDSHOT = ITEMS.register("castironroundshot",
            () -> new CastIronRoundshot(new CastIronRoundshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> SHOTGUNSHELL = ITEMS.register("shotgunshell",
            () -> new ShotgunShell(new ShotgunShell.Properties().stacksTo(32)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
