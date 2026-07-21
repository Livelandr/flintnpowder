package org.ragingzombies.flintnpowder.entity.custom;

import com.livelandr.flintcore.core.guns.GunBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.ragingzombies.flintnpowder.entity.goals.MusketRangedAttackGoal;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Flinter;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Musket;

public class HyperSkeletonEntity extends AbstractSkeleton {
    public HyperSkeletonEntity(EntityType<? extends HyperSkeletonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 45).add(Attributes.MOVEMENT_SPEED, 0.4F);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItemsGuns.FLINTER.get()));
    }

    @Override
    public int getMaxHeadXRot() {
        return 90;
    }

    @Override
    public int getMaxHeadYRot() {
        return 90;
    }

    @Override
    public void reassessWeaponGoal() {
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, this.getClass()));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, (targetEntity)-> {
            if (targetEntity instanceof Player player) {
                if (player.isCreative() || player.isSpectator()) {
                    return false;
                }
            }
            return true;
        }));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, 1, true, false, (targetEntity)-> {
            return targetEntity.getClass() != this.getClass();
        }));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MusketRangedAttackGoal<>(this, 1D, 35));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    public void tick() {
        super.tick();

        if (getItemInHand(InteractionHand.MAIN_HAND).is(ModItemsGuns.FLINTER.get())) {
            ((Flinter) getItemInHand(InteractionHand.MAIN_HAND).getItem()).inventoryTick(
                    getItemInHand(InteractionHand.MAIN_HAND),
                    this.level(),
                    this,
                    0,
                    true)
            ;
        }
    }

    public boolean canFreeze() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SHULKER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SHULKER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SHULKER_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.GRASS_STEP;
    }
}