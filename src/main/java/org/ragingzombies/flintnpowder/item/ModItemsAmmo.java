package org.ragingzombies.flintnpowder.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.ammo.*;
import org.ragingzombies.flintnpowder.item.ammo.magazines.BattleRifleMag;
import org.ragingzombies.flintnpowder.item.ammo.magazines.ExtendedHandgunMag;
import org.ragingzombies.flintnpowder.item.ammo.magazines.HandgunMag;
import org.ragingzombies.flintnpowder.item.ammo.magazines.ShotgunMag;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellDragon;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellSlug;

public class ModItemsAmmo {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> STEELROUNDSHOT = ITEMS.register("steel_roundshot",
            () -> new SteelRoundshot(new SteelRoundshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> CASTIRONROUNDSHOT = ITEMS.register("castironroundshot",
            () -> new CastIronRoundshot(new CastIronRoundshot.Properties().stacksTo(32)));

    public static final RegistryObject<Item> COPPERROUNDSHOT = ITEMS.register("copper_roundshot",
            () -> new CopperRoundshot(new CopperRoundshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> COPPERVOLLEYSHOT = ITEMS.register("copper_volleyshot",
            () -> new CopperVolleyshot(new CopperVolleyshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> FOOLSGOLDROUNDSHOT = ITEMS.register("golden_roundshot",
            () -> new FoolsGoldRoundshot(new FoolsGoldRoundshot.Properties().stacksTo(32)));

    public static final RegistryObject<Item> HEAVYCASTIRONROUNDSHOT = ITEMS.register("heavy_cast_iron_roundshot",
            () -> new HeavyCastIronRoundshot(new HeavyCastIronRoundshot.Properties().stacksTo(16)));
    public static final RegistryObject<Item> HEAVYSTEELROUNDSHOT = ITEMS.register("heavy_steel_roundshot",
            () -> new HeavySteelRoundshot(new HeavySteelRoundshot.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CASTIRONBUCKSHOT = ITEMS.register("cast_iron_buckshot",
            () -> new CastIronBuckshot(new CastIronBuckshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> STEELBUCKSHOT = ITEMS.register("steel_buckshot",
            () -> new SteelBuckshot(new SteelBuckshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> FLAMINGBUCKSHOT = ITEMS.register("flaming_buckshot",
            () -> new FlamingBuckshot(new FlamingBuckshot.Properties().stacksTo(32)));

    public static final RegistryObject<Item> PISTOLROUND = ITEMS.register("pistol_round",
            () -> new PistolRound(new PistolRound.Properties().stacksTo(32)));
    public static final RegistryObject<Item> RIFLEROUND = ITEMS.register("rifle_round",
            () -> new RifleRound(new RifleRound.Properties().stacksTo(32)));

    public static final RegistryObject<Item> FLAMINGGRAPESHOT = ITEMS.register("greek_fire_buckshot",
            () -> new FlamingGrapeshot(new FlamingGrapeshot.Properties().stacksTo(32)));
    public static final RegistryObject<Item> OILFLAMESHOT = ITEMS.register("greek_fire_charge",
            () -> new OilFlameshot(new OilFlameshot.Properties().stacksTo(32)));

    public static final RegistryObject<Item> SHOTGUNSHELL = ITEMS.register("shotgunshell",
            () -> new ShotgunShell(new ShotgunShell.Properties().stacksTo(32)));
    public static final RegistryObject<Item> SHOTGUNSHELLSLUG = ITEMS.register("shotgunshellslug",
            () -> new ShotgunShellSlug(new ShotgunShellSlug.Properties().stacksTo(32)));
    public static final RegistryObject<Item> SHOTGUNSHELLDRAGON = ITEMS.register("shotgunshelldragon",
            () -> new ShotgunShellDragon(new ShotgunShellDragon.Properties().stacksTo(32)));

    public static final RegistryObject<Item> HANDGUNMAG = ITEMS.register("handgunmagazine",
            () -> new HandgunMag(new HandgunMag.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EXTENDEDHANDGUNMAG = ITEMS.register("handgunmagazineextended",
            () -> new ExtendedHandgunMag(new ExtendedHandgunMag.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BATTLERIFLEMAGAZINE = ITEMS.register("battleriflemagazine",
            () -> new BattleRifleMag(new BattleRifleMag.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SHOTGUNMAGAZINE = ITEMS.register("shotgun_magazine",
            () -> new ShotgunMag(new ShotgunMag.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CASTIRONBOMB = ITEMS.register("cast_iron_bomb",
            () -> new CastIronBomb(new CastIronBomb.Properties().stacksTo(16)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
