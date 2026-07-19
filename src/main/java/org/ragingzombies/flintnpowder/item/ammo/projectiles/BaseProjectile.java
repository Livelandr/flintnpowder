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

import com.livelandr.flintcore.Flintcore;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public abstract class BaseProjectile extends AbstractArrow implements ItemSupplier {
    public float damage;

    public BaseProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public BaseProjectile(EntityType<? extends AbstractArrow> pEntityType, LivingEntity livingEntity, Level pLevel) {
        super(pEntityType, livingEntity, pLevel);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    protected abstract Item getDefaultItem();

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(getDefaultItem());
    }
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.EMPTY;
    }

    public ParticleOptions getTrailParticleType() {
        return ParticleTypes.SMOKE;
    }

    public void spawnTrailParticles() {
        Vec3 motion = this.getDeltaMovement();
        for (int i = 0; i < 5; i++) {
            double offset = i * 0.2;
            this.level().addParticle(
                    getTrailParticleType(),
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

        if (level().isClientSide()) {
            spawnTrailParticles();
        }
    }

    public ParticleOptions getCollisionParticleType() {
        return ParticleTypes.POOF;
    }

    public void spawnCollisionParticles(BlockPos pos) {
        ((ServerLevel) this.level()).sendParticles(
                getCollisionParticleType(),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                10,
                0.05, 0.05, 0.05,
                0.06
        );

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.BULLETHIT.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide()) {
            spawnCollisionParticles(pResult.getBlockPos());
            this.discard();
        }

        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        pResult.getEntity().invulnerableTime = 0;
        if (!this.level().isClientSide()) {
            DamageSource dmg = this.damageSources().arrow( this, this.getOwner());

            double speed = this.getDeltaMovement().length();
            pResult.getEntity().hurt(dmg, this.damage);
            Flintcore.LOGGER.info( Float.toString(this.damage) );

            spawnCollisionParticles(pResult.getEntity().getOnPos());
        }
        this.discard();
    }

}
