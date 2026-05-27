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
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.BuckshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.DragonBreathProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.SlugProjectile;

public class ModProjectiles {
    public static final DeferredRegister<EntityType<?>> PROJECTILES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Flintnpowder.MOD_ID);

    public static final RegistryObject<EntityType<CastIronRoundshotProjectile>> CASTIRONROUNDSHOTPROJECTILE =
            PROJECTILES.register("cast_iron_roundshot_projectile", () -> EntityType.Builder.<CastIronRoundshotProjectile>of(CastIronRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .clientTrackingRange(10)
                    .updateInterval(15)
                    .build("cast_iron_roundshot_projectile"));

    public static final RegistryObject<EntityType<SteelRoundshotProjectile>> STEELROUNDSHOTPROJECTILE =
            PROJECTILES.register("steel_roundshot_projectile", () -> EntityType.Builder.<SteelRoundshotProjectile>of(SteelRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .clientTrackingRange(10)
                    .updateInterval(15)
                    .build("steel_roundshot_projectile"));

    public static final RegistryObject<EntityType<CopperRoundshotProjectile>> COPPERROUNDSHOTPROJECTILE =
            PROJECTILES.register("copper_roundshot_projectile", () -> EntityType.Builder.<CopperRoundshotProjectile>of(CopperRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .clientTrackingRange(10)
                    .updateInterval(15)
                    .build("copper_roundshot_projectile"));

    public static final RegistryObject<EntityType<BuckshotProjectile>> BUCKSHOTPROJECTILE =
            PROJECTILES.register("buckshot_projectile", () -> EntityType.Builder.<BuckshotProjectile>of(BuckshotProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("buckshot_projectile"));

    public static final RegistryObject<EntityType<SlugProjectile>> SLUGPROJECTILE =
            PROJECTILES.register("slug_projectile", () -> EntityType.Builder.<SlugProjectile>of(SlugProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("slug_projectile"));

    public static final RegistryObject<EntityType<DragonBreathProjectile>> DRAGONBREATHPROJECTILE =
            PROJECTILES.register("dragonbreath_projectile", () -> EntityType.Builder.<DragonBreathProjectile>of(DragonBreathProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(10)
                    .updateInterval(4)
                    .build("dragonbreath_projectile"));

    public static final RegistryObject<EntityType<PistolRoundProjectile>> PISTOLROUNDPROJECTILE =
            PROJECTILES.register("pistolround_projectile", () -> EntityType.Builder.<PistolRoundProjectile>of(PistolRoundProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(10)
                    .updateInterval(5)
                    .build("pistolround_projectile"));

    public static final RegistryObject<EntityType<TheRockProjectile>> THEROCKPROJECTILE =
            PROJECTILES.register("therock_projectile", () -> EntityType.Builder.<TheRockProjectile>of(TheRockProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(10)
                    .updateInterval(5)
                    .build("therock_projectile"));

    public static final RegistryObject<EntityType<HeavyCastIronRoundshotProjectile>> HEAVYCASTIRONPROJECTILE =
            PROJECTILES.register("heavycastiron_projectile", () -> EntityType.Builder.<HeavyCastIronRoundshotProjectile>of(HeavyCastIronRoundshotProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(10)
                    .updateInterval(5)
                    .build("heavycastiron_projectile"));

    public static final RegistryObject<EntityType<CastIronBombProjectile>> CASTIRONBOMB =
            PROJECTILES.register("castironbomb_projectile", () -> EntityType.Builder.<CastIronBombProjectile>of(CastIronBombProjectile::new, MobCategory.MISC)
                    .sized(0.025f,0.025f)
                    .clientTrackingRange(15)
                    .updateInterval(5)
                    .build("castironbomb_projectile"));




    public static void register(IEventBus eventBus) {PROJECTILES.register(eventBus);}
}
