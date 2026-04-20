package org.ragingzombies.flintnpowder.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.BreakActionCoachgun;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.TrapdoorRifle;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.*;
import org.ragingzombies.flintnpowder.item.guns.magfed.ClosedBoltBattleRifle;
import org.ragingzombies.flintnpowder.item.guns.magfed.OpenBoltSubmachineGun;
import org.ragingzombies.flintnpowder.item.guns.magfed.SemiPistol;
import org.ragingzombies.flintnpowder.item.guns.other.LogCannon;
import org.ragingzombies.flintnpowder.item.guns.pumpaction.BoltActionRifle;
import org.ragingzombies.flintnpowder.item.guns.pumpaction.PumpActionShotgun;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.SingleActionRevolver;

public class ModItemsGuns {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Item> LOGCANNON = ITEMS.register("log_cannon",
            () -> new LogCannon(new LogCannon.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MUSKET = ITEMS.register("musket",
            () -> new Musket(new Musket.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol",
            () -> new Pistol(new Pistol.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLUNDERBUSS = ITEMS.register("blunderbuss",
            () -> new Blunderbuss(new Blunderbuss.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FLINTER = ITEMS.register("flinter",
            () -> new Flinter(new Flinter.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VOLLEYGUN = ITEMS.register("volleygun",
            () -> new Volleygun(new Volleygun.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARQUEBUS = ITEMS.register("arquebus",
            () -> new Arquebus(new Arquebus.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GREEKFIRE = ITEMS.register("greek_fire",
            () -> new GreekFire(new GreekFire.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLAMINGHALBERD = ITEMS.register("flaming_halberd",
            () -> new FlamingHalberd(new FlamingHalberd.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HANDGONNE = ITEMS.register("hand_gonne",
            () -> new Handgonne(new Handgonne.Properties().stacksTo(1)));


    public static final RegistryObject<Item> RIFLE = ITEMS.register("rifle",
            () -> new Rifle(new Rifle.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BIGGAMEGUN = ITEMS.register("big_game",
            () -> new BigGameGun(new BigGameGun.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BRUTTBUSS = ITEMS.register("bruttbuss",
            () -> new Bruttbuss(new Bruttbuss.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BOLTACTIONRIFLE = ITEMS.register("bolt_action_rifle",
            () -> new BoltActionRifle(new BoltActionRifle.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SINGLEACTIONREVOLVER = ITEMS.register("single_action_revolver",
            () -> new SingleActionRevolver(new SingleActionRevolver.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BREAKACTIONCOACHGUN = ITEMS.register("break_action_coachgun",
            () -> new BreakActionCoachgun(new BreakActionCoachgun.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TRAPDOORRIFLE = ITEMS.register("trapdoor_rifle",
            () -> new TrapdoorRifle(new TrapdoorRifle.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PUMPACTIONSHOTGUN = ITEMS.register("shotgun",
            () -> new PumpActionShotgun(new PumpActionShotgun.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SEMIAUTOPISTOL = ITEMS.register("handgun_pistol",
            () -> new SemiPistol(new SemiPistol.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OPENBOLTSUBMACHINEGUN = ITEMS.register("open_bolt_smg",
            () -> new OpenBoltSubmachineGun(new OpenBoltSubmachineGun.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CLOSEDBOLTBATTLERIFLE = ITEMS.register("closed_bolt_mag_rifle",
            () -> new ClosedBoltBattleRifle(new ClosedBoltBattleRifle.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
