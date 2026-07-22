package org.ragingzombies.flintnpowder.entity.custom;

import com.livelandr.flintcore.core.SiegeEngineAdapter;
import com.livelandr.flintcore.core.guns.FlintlockBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.guns.siege.MortarModern;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class CannonEntity extends Mob {
    public SiegeEngineAdapter weapon;
    public LivingEntity gunner;
    public boolean isAiming = true;
    public float oldXRot = 0;

    private static final EntityDataAccessor<Float> TARGET_YAW = SynchedEntityData.defineId(MortarEntity.class, EntityDataSerializers.FLOAT);

    public CannonEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.navigation.setMaxVisitedNodesMultiplier(0);

        this.entityData.define(TARGET_YAW, 0.0F);

        weapon = new SiegeEngineAdapter();
        weapon.createGun(ModItemsGuns.CANNON_SIEGE_ENGINE.get());
        weapon.setScapegoat(this);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public void setOwnerName(Component name) {
        this.setCustomName(Component.translatable("entity.flintnpowder.cannon").append(" [").append(name).append("]"));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public boolean shouldShowName() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        if (isAiming) return true;

        return false;
    }

    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime >= 1) {
            this.createWitherRose(null);

            if ( this.isOnFire() && ((FlintlockBase) weapon.getGunBase()).getGunpowderAmount(weapon.getGun()) > 0) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3F, Level.ExplosionInteraction.BLOCK);
            }

            this.discard();

            this.playSound(ModSounds.SIEGE_DIE.get(), 1F, 1F);
        }
    }

    public void aimAngleX(float ang) {
        if (ang < -25) ang = -25;
        if (ang > 25) ang = 25;

        this.entityData.set(TARGET_YAW, ang);
        this.setXRot(ang);
        weapon.setRotationX(ang);
    }
    public void aimAngleY(float ang) {
        this.setYRot(ang);
        weapon.setRotationY(ang);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR;
    }

    @Override
    public void tick() {
        weapon.tick();

        if (isAiming && gunner == null) {
            isAiming = false;
        }

        if (isAiming) {
            gunner.addEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3, 2, false, false)
            );

            if (gunner.isCrouching()) {
                gunner = null;
                return;
            }


            if (!this.level().isClientSide()) {
                this.aimAngleX(gunner.getXRot());
                this.aimAngleY(gunner.getYRot());

                Vec3 gunPos = gunner.getPosition(0);
                gunPos = gunPos.add(gunner.getForward().multiply(2.5, 0, 2.5));

                this.setPos(gunPos);
            }
        }

        if (this.level().isClientSide()) {
            float lockedYaw = this.entityData.get(TARGET_YAW);
            oldXRot = (float) Math.toRadians(this.weapon.getRotationX());
            this.aimAngleX(lockedYaw);

            float currentYaw = this.getYRot();
            this.yHeadRot = currentYaw;
            this.yBodyRot = currentYaw;
            this.yHeadRotO = currentYaw;
        }

        super.tick();
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (pHand == InteractionHand.MAIN_HAND) {
            if (pPlayer.getItemInHand(pHand).isEmpty()) {
                isAiming = true;
                gunner = pPlayer;
            } else {
                weapon.transmitInteraction(this.level(), pPlayer, pHand);
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 1.15F;
    }

    @Override
    public void handleDamageEvent(net.minecraft.world.damagesource.DamageSource damageSource) {
        super.handleDamageEvent(damageSource);

        this.hurtTime = 0;
        this.hurtDuration = 0;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected void jumpFromGround() {
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public int getAirSupply() {
        return this.getMaxAirSupply();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayerSq) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
