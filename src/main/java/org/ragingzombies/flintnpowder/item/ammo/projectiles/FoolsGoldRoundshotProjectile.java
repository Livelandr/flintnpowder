package org.ragingzombies.flintnpowder.item.ammo.projectiles;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class FoolsGoldRoundshotProjectile extends AbstractArrow implements ItemSupplier {

    private int entsPierced = 0;
    public float damage = 1;

    public FoolsGoldRoundshotProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public FoolsGoldRoundshotProjectile(Level pLevel) {
        super(ModProjectiles.STEELROUNDSHOTPROJECTILE.get(), pLevel);
    }
    public FoolsGoldRoundshotProjectile(Level pLevel, LivingEntity livingEntity) {
        super(ModProjectiles.STEELROUNDSHOTPROJECTILE.get(), livingEntity, pLevel);
        this.setPierceLevel((byte) 1);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide()) {
            Vec3 motion = this.getDeltaMovement();

            ((ServerLevel) this.level()).sendParticles(
                    ParticleTypes.FIREWORK,
                    this.getX() - motion.x,
                    this.getY() - motion.y + 0.1,
                    this.getZ() - motion.z,
                    1,
                    motion.x * 0.05, motion.y * 0.05, motion.z * 0.05,
                    0.06
            );
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.AIR);
    }
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.EMPTY;
    }

    void collisionParticles() {
        ((ServerLevel) this.level()).sendParticles(
                ParticleTypes.LARGE_SMOKE,
                this.getX(),
                this.getY(),
                this.getZ(),
                10,
                0.05, 0.05, 0.05,
                0.06
        );

        this.level().playSeededSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.BULLETHIT.get(), SoundSource.NEUTRAL, 2.0F, 1.0F, 0);
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide()) {
            collisionParticles();
            this.discard();
        }

        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level().isClientSide()) {
            DamageSource dmg = this.damageSources().arrow( this, this.getOwner());

            double speed = this.getDeltaMovement().length();
            pResult.getEntity().hurt(dmg, damage);

            collisionParticles();
            damage /= 1.25;
            if (++entsPierced > 1) {
                this.discard();
            }
        }

        //super.onHitEntity(pResult);
    }

}
