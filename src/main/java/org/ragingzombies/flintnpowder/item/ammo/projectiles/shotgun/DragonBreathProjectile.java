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
package org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.BaseProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class DragonBreathProjectile extends BaseProjectile {

    public DragonBreathProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItemsAmmo.COPPERROUNDSHOT.get();
    }

    public DragonBreathProjectile(Level pLevel, LivingEntity livingEntity) {
        super(ModProjectiles.DRAGONBREATHPROJECTILE.get(), livingEntity, pLevel);
    }

    @Override
    public void spawnTrailParticles() {
        Vec3 motion = this.getDeltaMovement();
        for (int i = 0; i < 4; i++) {
            double offset = i * 0.2;
            this.level().addParticle(
                    ParticleTypes.FLAME,
                    this.getX() - motion.x * offset,
                    this.getY() - motion.y * offset + 0.1,
                    this.getZ() - motion.z * offset,
                    motion.x * 0.05, motion.y * 0.05, motion.z * 0.05
            );
        }
        for (int i = 0; i < 4; i++) {
            double offset = i * 0.2;
            this.level().addParticle(
                    ParticleTypes.SMOKE,
                    this.getX() - motion.x * offset,
                    this.getY() - motion.y * offset + 0.1,
                    this.getZ() - motion.z * offset,
                    motion.x * 0.05, motion.y * 0.05, motion.z * 0.05
            );
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount >= 20) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
    pResult.getEntity().invulnerableTime = 0;
        if (!this.level().isClientSide()) {
            DamageSource dmg = this.damageSources().mobProjectile(this, (LivingEntity ) this.getOwner());

            pResult.getEntity().hurt(dmg, damage);
            pResult.getEntity().setSecondsOnFire(20);

            spawnCollisionParticles(pResult.getEntity().getOnPos());
            this.discard();
        }


    }

}
