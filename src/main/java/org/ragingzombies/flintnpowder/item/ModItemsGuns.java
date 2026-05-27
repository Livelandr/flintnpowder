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
import org.ragingzombies.flintnpowder.item.guns.blazelocks.BreakActionCoachgun;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.Broomhandle;
import org.ragingzombies.flintnpowder.item.guns.blazelocks.TrapdoorRifle;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.*;
import org.ragingzombies.flintnpowder.item.guns.magfed.*;
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
    public static final RegistryObject<Item> SNIPERRIFLE = ITEMS.register("antimaterielrifle",
            () -> new SniperRifle(new SniperRifle.Properties().stacksTo(1)));


    public static final RegistryObject<Item> BROOMHANDLE = ITEMS.register("broomhandlepistol",
            () -> new Broomhandle(new Broomhandle.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SEMIAUTOPISTOL = ITEMS.register("handgun_pistol",
            () -> new SemiPistol(new SemiPistol.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OPENBOLTSUBMACHINEGUN = ITEMS.register("open_bolt_smg",
            () -> new OpenBoltSubmachineGun(new OpenBoltSubmachineGun.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CLOSEDBOLTBATTLERIFLE = ITEMS.register("closed_bolt_mag_rifle",
            () -> new ClosedBoltBattleRifle(new ClosedBoltBattleRifle.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GASOPERATEDSHOTGUN = ITEMS.register("gas_operated_shotgun",
            () -> new GasOperatedShotgun(new GasOperatedShotgun.Properties().stacksTo(1)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
