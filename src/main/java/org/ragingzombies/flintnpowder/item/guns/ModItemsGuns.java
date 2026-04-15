package org.ragingzombies.flintnpowder.item.guns;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.ammo.CastIronRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.CopperRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.PistolRound;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellDragon;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellSlug;
import org.ragingzombies.flintnpowder.item.attachments.Bayonet;
import org.ragingzombies.flintnpowder.item.attachments.Silencer;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.*;
import org.ragingzombies.flintnpowder.item.guns.magfed.SemiPistol;
import org.ragingzombies.flintnpowder.item.guns.pumpaction.PumpActionShotgun;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.SingleActionRevolver;

public class ModItemsGuns {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> MUSKET = ITEMS.register("musket",
            () -> new Musket(new Musket.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol",
            () -> new Pistol(new Pistol.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FLINTER = ITEMS.register("flinter",
            () -> new Flinter(new Flinter.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VOLLEYGUN = ITEMS.register("volleygun",
            () -> new Volleygun(new Volleygun.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARQUEBUS = ITEMS.register("arquebus",
            () -> new Arquebus(new Arquebus.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SINGLEACTIONREVOLVER = ITEMS.register("single_action_revolver",
            () -> new SingleActionRevolver(new SingleActionRevolver.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PUMPACTIONSHOTGUN = ITEMS.register("shotgun",
            () -> new PumpActionShotgun(new PumpActionShotgun.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SEMIAUTOPISTOL = ITEMS.register("handgun_pistol",
            () -> new SemiPistol(new SemiPistol.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
