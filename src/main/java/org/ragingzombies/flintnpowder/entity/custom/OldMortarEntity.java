package org.ragingzombies.flintnpowder.entity.custom;

import com.livelandr.flintcore.core.SiegeEngineAdapter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.guns.siege.MortarModern;
import org.ragingzombies.flintnpowder.item.guns.siege.MortarOld;

public class OldMortarEntity extends Mob {
    public SiegeEngineAdapter weapon;

    private static final EntityDataAccessor<Float> TARGET_YAW = SynchedEntityData.defineId(OldMortarEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_PITCH = SynchedEntityData.defineId(OldMortarEntity.class, EntityDataSerializers.FLOAT);

    public OldMortarEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.navigation.setMaxVisitedNodesMultiplier(0);

        this.entityData.define(TARGET_YAW, 0.0F);
        this.entityData.define(TARGET_PITCH, 0.0F);

        weapon = new SiegeEngineAdapter();

        weapon.createGun(ModItemsGuns.MORTAR_OLD_SIEGE_ENGINE.get());
        weapon.setScapegoat(this);

        if (this.getXRot() == 0) {
            aimAngleX(-147);
        }
        aimAngleY(0);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("MortarYaw", this.entityData.get(TARGET_YAW));
        compound.putFloat("MortarPitch", this.entityData.get(TARGET_PITCH));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("MortarYaw")) {
            float yaw = compound.getFloat("MortarYaw");
            float pitch = compound.getFloat("MortarPitch");

            this.entityData.set(TARGET_YAW, yaw);
            this.entityData.set(TARGET_PITCH, pitch);

            this.aimAngleX(yaw);
            this.aimAngleY(pitch);
        }
    }

    public void setOwnerName(Component name) {
        this.setCustomName(Component.translatable("entity.flintnpowder.mortar").append(" [").append(name).append("]"));
    }
    @Override
    public boolean shouldShowName() {
        return false;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void tick() {
        weapon.tick();

        if (this.level().isClientSide) {
            float lockedYaw = this.entityData.get(TARGET_YAW);
            float lockedPitch = this.entityData.get(TARGET_PITCH);

            aimAngleX(lockedYaw);
            aimAngleY(lockedPitch);

            float currentYaw = this.getYRot();
            this.yHeadRot = currentYaw;
            this.yBodyRot = currentYaw;
            this.yHeadRotO = currentYaw;
        }

        super.tick();
    }

    // This looks like ass, and, well... it is
    public void aimAngleX(float ang) {
        this.entityData.set(TARGET_YAW, ang);
        this.setXRot(ang);
        weapon.setRotationX(ang);
        ((MortarOld) weapon.getGunBase()).angleX = ang;
    }
    public void aimAngleY(float ang) {
        this.entityData.set(TARGET_PITCH, ang);
        this.setYRot(ang);
        weapon.setRotationY(ang);
        ((MortarOld) weapon.getGunBase()).angleY = ang;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 0.5F;
    }

    @Override
    public void handleDamageEvent(net.minecraft.world.damagesource.DamageSource damageSource) {
        super.handleDamageEvent(damageSource);

        this.hurtTime = 0;
        this.hurtDuration = 0;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && player.getMainHandItem().is(ModItems.DIVIDER.get()) && !player.level().isClientSide()) {
            float curAng = Math.round(weapon.getRotationX());

            if (player.isCrouching()) {
                curAng--;
                if (curAng < -150) {
                    curAng = -150;
                }
            } else {
                curAng++;

                if (curAng > -91) {
                    curAng = -91;
                }
            }

            this.aimAngleX(curAng);

            player.displayClientMessage(Component.translatable("flintnpowder.mortarrotationX").append(Float.toString(180+curAng)), true);

            return InteractionResult.SUCCESS;
        }

        if (player.isCrouching() && !player.level().isClientSide()) {

            double dist = this.position().distanceTo(player.position());
            double dx = (player.position().x - this.position().x) / dist;
            double dz = (player.position().z - this.position().z) / dist;

            double ang;
            double arccos = Math.toDegrees(Math.acos(dz));

            if (dx >= 0) {
                ang = -arccos;
            } else {
                ang = arccos;
            }

            ang = 180 + Math.round(ang*100.0) / 100.0;

            player.displayClientMessage(Component.translatable("flintnpowder.mortarrotationY").append(Double.toString(Math.round((ang+90)*100)/100 - 90)), true);

            aimAngleY((float) ang);


            return InteractionResult.SUCCESS;
        }

        if (hand == InteractionHand.MAIN_HAND)
            weapon.transmitInteraction(this.level(), player, hand);

        return InteractionResult.SUCCESS;
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
