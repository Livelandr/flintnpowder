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
package org.ragingzombies.flintnpowder.item.ammo.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.mortar.*;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.BuckshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.DragonBreathProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.SlugProjectile;

public class ModProjectiles {
    public static final DeferredRegister<EntityType<?>> PROJECTILES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Flintnpowder.MOD_ID);

    public static final RegistryObject<EntityType<CastIronRoundshotProjectile>> CASTIRONROUNDSHOTPROJECTILE =
            PROJECTILES.register("cast_iron_roundshot_projectile", () -> EntityType.Builder.<CastIronRoundshotProjectile>of(CastIronRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("cast_iron_roundshot_projectile"));

    public static final RegistryObject<EntityType<SteelRoundshotProjectile>> STEELROUNDSHOTPROJECTILE =
            PROJECTILES.register("steel_roundshot_projectile", () -> EntityType.Builder.<SteelRoundshotProjectile>of(SteelRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("steel_roundshot_projectile"));

    public static final RegistryObject<EntityType<CopperRoundshotProjectile>> COPPERROUNDSHOTPROJECTILE =
            PROJECTILES.register("copper_roundshot_projectile", () -> EntityType.Builder.<CopperRoundshotProjectile>of(CopperRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("copper_roundshot_projectile"));

    public static final RegistryObject<EntityType<BuckshotProjectile>> BUCKSHOTPROJECTILE =
            PROJECTILES.register("buckshot_projectile", () -> EntityType.Builder.<BuckshotProjectile>of(BuckshotProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("buckshot_projectile"));

    public static final RegistryObject<EntityType<SlugProjectile>> SLUGPROJECTILE =
            PROJECTILES.register("slug_projectile", () -> EntityType.Builder.<SlugProjectile>of(SlugProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("slug_projectile"));

    public static final RegistryObject<EntityType<DragonBreathProjectile>> DRAGONBREATHPROJECTILE =
            PROJECTILES.register("dragonbreath_projectile", () -> EntityType.Builder.<DragonBreathProjectile>of(DragonBreathProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("dragonbreath_projectile"));

    public static final RegistryObject<EntityType<FlamingBuckshotProjectile>> FLAMINGBUCKSHOTPROJECTILE =
            PROJECTILES.register("flamingbuckshot_projectile", () -> EntityType.Builder.<FlamingBuckshotProjectile>of(FlamingBuckshotProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("flamingbuckshot_projectile"));

    public static final RegistryObject<EntityType<PistolRoundProjectile>> PISTOLROUNDPROJECTILE =
            PROJECTILES.register("pistolround_projectile", () -> EntityType.Builder.<PistolRoundProjectile>of(PistolRoundProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("pistolround_projectile"));

    public static final RegistryObject<EntityType<InvisibleProjectile>> INVISIBLEPROJECTILE =
            PROJECTILES.register("invisibleround_projectile", () -> EntityType.Builder.<InvisibleProjectile>of(InvisibleProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("invisibleround_projectile"));

    public static final RegistryObject<EntityType<TheRockProjectile>> THEROCKPROJECTILE =
            PROJECTILES.register("therock_projectile", () -> EntityType.Builder.<TheRockProjectile>of(TheRockProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("therock_projectile"));

    public static final RegistryObject<EntityType<HeavyCastIronRoundshotProjectile>> HEAVYCASTIRONPROJECTILE =
            PROJECTILES.register("heavycastiron_projectile", () -> EntityType.Builder.<HeavyCastIronRoundshotProjectile>of(HeavyCastIronRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("heavycastiron_projectile"));

    public static final RegistryObject<EntityType<CastIronBombProjectile>> CASTIRONBOMB =
            PROJECTILES.register("castironbomb_projectile", () -> EntityType.Builder.<CastIronBombProjectile>of(CastIronBombProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("castironbomb_projectile"));

    public static final RegistryObject<EntityType<FoolsGoldRoundshotProjectile>> FOOLSGOLD =
            PROJECTILES.register("foolsgold_projectile", () -> EntityType.Builder.<FoolsGoldRoundshotProjectile>of(FoolsGoldRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("foolsgold_projectile"));

    public static final RegistryObject<EntityType<HEShellProjectile>> HESHELL =
            PROJECTILES.register("hemortarshell_projectile", () -> EntityType.Builder.<HEShellProjectile>of(HEShellProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("hemortarshell_projectile"));
    public static final RegistryObject<EntityType<FlamingShellProjectile>> FLAMINGMORTARSHELL =
            PROJECTILES.register("flamemortarshell_projectile", () -> EntityType.Builder.<FlamingShellProjectile>of(FlamingShellProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("flamemortarshell_projectile"));

    public static final RegistryObject<EntityType<ClusterShellProjectile>> CLUSTERMORTARSHELL =
            PROJECTILES.register("mortarclustershell_projectile", () -> EntityType.Builder.<ClusterShellProjectile>of(ClusterShellProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("mortarclustershell_projectile"));

    public static final RegistryObject<EntityType<ClusterPellet>> CLUSTERPELLET =
            PROJECTILES.register("mortarclusterpellet_projectile", () -> EntityType.Builder.<ClusterPellet>of(ClusterPellet::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("mortarclusterpellet_projectile"));

    public static final RegistryObject<EntityType<CastIronCannonballProjectile>> CASTIRONCANNONBALL =
            PROJECTILES.register("castironcannonball_projectile", () -> EntityType.Builder.<CastIronCannonballProjectile>of(CastIronCannonballProjectile::new, MobCategory.MISC)
                    .sized(0.1f,0.1f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("castironcannonball_projectile"));

    public static final RegistryObject<EntityType<SteelCannonballProjectile>> STEELCANNONBALL =
            PROJECTILES.register("steelcannonball_projectile", () -> EntityType.Builder.<SteelCannonballProjectile>of(SteelCannonballProjectile::new, MobCategory.MISC)
                    .sized(0.1f,0.1f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("steelcannonball_projectile"));

    public static final RegistryObject<EntityType<PoisonGasProjectile>> POISONGASPROJECTILE =
            PROJECTILES.register("poisongas_mortar_projectile", () -> EntityType.Builder.<PoisonGasProjectile>of(PoisonGasProjectile::new, MobCategory.MISC)
                    .sized(0.1f,0.1f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("poisongas_mortar_projectile"));

    public static final RegistryObject<EntityType<HealingGasProjectile>> HEALINGGASPROJECTILE =
            PROJECTILES.register("healinggas_mortar_projectile", () -> EntityType.Builder.<HealingGasProjectile>of(HealingGasProjectile::new, MobCategory.MISC)
                    .sized(0.1f,0.1f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("healinggas_mortar_projectile"));





    public static void register(IEventBus eventBus) {PROJECTILES.register(eventBus);}
}
