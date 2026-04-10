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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.item.guns.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class BuckshotProjectile extends AbstractArrow implements ItemSupplier {

    public float damage = 1;

    public BuckshotProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public BuckshotProjectile(Level pLevel) {
        super(ModProjectiles.BUCKSHOTPROJECTILE.get(), pLevel);
    }
    public BuckshotProjectile(Level pLevel, LivingEntity livingEntity) {
        super(ModProjectiles.BUCKSHOTPROJECTILE.get(), livingEntity, pLevel);
    }

    public void SetDamage(float _dmg) {
        damage = _dmg;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.STEELNUGGET.get());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.STEELNUGGET.get());
    }
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide()) {
            Vec3 motion = this.getDeltaMovement();
            for (int i = 0; i < 2; i++) {
                double offset = i * 0.2;
                ((ServerLevel) this.level()).sendParticles(
                        ParticleTypes.POOF,
                        this.getX() - motion.x * offset,
                        this.getY() - motion.y * offset + 0.1,
                        this.getZ() - motion.z * offset,
                        0,
                        motion.x * 0.05, motion.y * 0.05, motion.z * 0.05,
                        0.06
                );
            }
        }
    }

    void collisionParticles() {
        ((ServerLevel) this.level()).sendParticles(
                ParticleTypes.SMOKE,
                this.getX(),
                this.getY(),
                this.getZ(),
                3,
                0.1, 0.1, 0.1,
                0.15
        );

        this.level().playSeededSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.BULLETHIT.get(), SoundSource.NEUTRAL, 0.25F, 1.0F, 0);
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
            DamageSource dmg = this.damageSources().mobProjectile(this, (LivingEntity ) this.getOwner());
            ((LivingEntity) pResult.getEntity()).invulnerableTime = 0;

            pResult.getEntity().hurt(dmg, damage);

            collisionParticles();
            this.discard();
        }

        super.onHitEntity(pResult);
    }

}
