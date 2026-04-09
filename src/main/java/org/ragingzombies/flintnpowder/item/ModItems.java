package org.ragingzombies.flintnpowder.item;

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
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Flinter;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Musket;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Pistol;
import org.ragingzombies.flintnpowder.item.guns.pumpaction.PumpActionShotgun;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.SingleActionRevolver;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> FNPTABICON = ITEMS.register("fnptabicon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAMROD = ITEMS.register("ramrod",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> STEELNUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MUSKET = ITEMS.register("musket",
            () -> new Musket(new Musket.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol",
            () -> new Pistol(new Pistol.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLINTER = ITEMS.register("flinter",
            () -> new Flinter(new Flinter.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SINGLEACTIONREVOLVER = ITEMS.register("single_action_revolver",
            () -> new SingleActionRevolver(new SingleActionRevolver.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PUMPACTIONSHOTGUN = ITEMS.register("shotgun",
            () -> new PumpActionShotgun(new PumpActionShotgun.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CASTIRONROUNDSHOT = ITEMS.register("castironroundshot",
            () -> new CastIronRoundshot(new CastIronRoundshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> STEELROUNDSHOT = ITEMS.register("steel_roundshot",
            () -> new CastIronRoundshot(new CastIronRoundshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> COPPERROUNDSHOT = ITEMS.register("copper_roundshot",
            () -> new CopperRoundshot(new CopperRoundshot.Properties().stacksTo(32)));

    public static final RegistryObject<Item> PISTOLROUND = ITEMS.register("pistol_round",
            () -> new PistolRound(new PistolRound.Properties().stacksTo(32)));

    public static final RegistryObject<Item> SHOTGUNSHELL = ITEMS.register("shotgunshell",
            () -> new ShotgunShell(new ShotgunShell.Properties().stacksTo(32)));
    public static final RegistryObject<Item> SHOTGUNSHELLSLUG = ITEMS.register("shotgunshellslug",
            () -> new ShotgunShellSlug(new ShotgunShellSlug.Properties().stacksTo(32)));
    public static final RegistryObject<Item> SHOTGUNSHELLDRAGON = ITEMS.register("shotgunshelldragon",
            () -> new ShotgunShellDragon(new ShotgunShellDragon.Properties().stacksTo(32)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
