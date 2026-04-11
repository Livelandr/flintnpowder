package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.ammo.magazines.HandgunMag;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellDragon;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellSlug;

public class ModItemsAmmo {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

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

    public static final RegistryObject<Item> HANDGUNMAG = ITEMS.register("handgunmag",
            () -> new HandgunMag(new HandgunMag.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
