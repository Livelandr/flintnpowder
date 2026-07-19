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

import net.minecraft.core.BlockPos;
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
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class CastIronCannonballProjectile extends BaseProjectile {
    public CastIronCannonballProjectile(EntityType<? extends AbstractArrow> pEntityType, LivingEntity livingEntity, Level pLevel) {
        super(pEntityType, livingEntity, pLevel);
    }

    public CastIronCannonballProjectile(EntityType<? extends AbstractArrow> pEntityType, Level level) {
        super(pEntityType, level);
    }

    @Override
    public Item getDefaultItem() {
        return ModItemsAmmo.CASTIRONCANNONBALL.get();
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide()) {
            spawnCollisionParticles(pResult.getBlockPos());

            this.level().explode(this, pResult.getBlockPos().getX(), pResult.getBlockPos().getY(), pResult.getBlockPos().getZ(), 4F, false, Level.ExplosionInteraction.TNT);
            this.level().explode(this, pResult.getBlockPos().getX(), pResult.getBlockPos().getY(), pResult.getBlockPos().getZ(), 7F, false, Level.ExplosionInteraction.NONE);

            this.discard();
        }

        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
    pResult.getEntity().invulnerableTime = 0;
        if (!this.level().isClientSide()) {

            DamageSource dmg = this.damageSources().mobProjectile(this, (LivingEntity ) this.getOwner());
            if (pResult.getEntity() instanceof LivingEntity) {
                ((LivingEntity) pResult.getEntity()).invulnerableTime = 0;}

            pResult.getEntity().hurt(dmg, 50);

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ANVIL_PLACE, SoundSource.NEUTRAL, 4.0F, .5F);

            this.level().explode(this, pResult.getEntity().getX(), pResult.getEntity().getY(), pResult.getEntity().getZ(), 4F, Level.ExplosionInteraction.TNT);
            this.level().explode(this, pResult.getEntity().getX(), pResult.getEntity().getY(), pResult.getEntity().getZ(), 7F, Level.ExplosionInteraction.NONE);
            spawnCollisionParticles(pResult.getEntity().getOnPos());
            this.discard();
        }

        
    }

}
