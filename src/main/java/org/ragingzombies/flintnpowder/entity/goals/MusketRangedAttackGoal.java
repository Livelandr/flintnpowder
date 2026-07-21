package org.ragingzombies.flintnpowder.entity.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.ragingzombies.flintnpowder.entity.custom.HyperSkeletonEntity;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Flinter;

import java.util.EnumSet;

public class MusketRangedAttackGoal<T extends HyperSkeletonEntity & RangedAttackMob> extends Goal {
    private final T mob;
    private LivingEntity target;
    private ItemStack gun;

    private int attackDelay = -1;
    private int seeTime = 0;
    private final double speedModifier;
    private final float attackRadiusSqr;

    public MusketRangedAttackGoal(T mob, double speedModifier, float attackRadius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackRadiusSqr = attackRadius*attackRadius;
        this.seeTime = 100;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();

        if (livingentity != null) {
            double distanceSqr = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            if (distanceSqr > (double) (this.attackRadiusSqr * 1.5F)) {
                return false;
            }
        }

        return livingentity != null && livingentity.isAlive() && this.mob.isHolding(item -> item.is(ModItemsGuns.FLINTER.get()));
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive() || target.isRemoved()) {
            return false;
        }

        if (target != this.target) return false;

        if (target instanceof net.minecraft.world.entity.player.Player player) {
            if (player.isCreative() || player.isSpectator()) {
                return false;
            }
        }

        double distanceSqr = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean canSee = this.mob.getSensing().hasLineOfSight(target);
        if ((distanceSqr > (double)(this.attackRadiusSqr * 2F))) {
            return false;
        }
        if (!canSee) {
            if (--this.seeTime < 0) {
                return false;
            }
        } else {
            this.seeTime = 100;
        }

        return true;
    }

    public void interactMusket(boolean allowShooting) {
        if (gun.getOrCreateTag().getInt("Gunpowder") < ((Flinter) gun.getItem()).gunpowderRequired) {

            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GUNPOWDER));
            ((Flinter) (gun.getItem())).__internal_interaction(mob.level(), mob, gun, InteractionHand.MAIN_HAND,false, 0, 0, null);

        } else if (!gun.getTag().getBoolean("HasAmmo")) {

            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItemsAmmo.COPPERROUNDSHOT.get()));
            ((Flinter) (gun.getItem())).__internal_interaction(mob.level(), mob, gun, InteractionHand.MAIN_HAND,false, 0, 0, null);

        } else if (!gun.getTag().getBoolean("IsStuffed")) {

            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.STICK));
            ((Flinter) (gun.getItem())).__internal_interaction(mob.level(), mob, gun, InteractionHand.MAIN_HAND,false, 0, 0, null);
            ((Flinter) (gun.getItem())).setAimAnimation(gun);

        } else if (allowShooting) {
            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.FLINT));
            ((Flinter) (gun.getItem())).__internal_interaction(mob.level(), mob, gun, InteractionHand.MAIN_HAND,false, 0, 0, null);
        }
    }

    @Override
    public void start() {
        super.start();
        this.target = this.mob.getTarget();
        this.mob.setAggressive(true);
        this.seeTime = 100;
        gun = this.mob.getItemInHand(InteractionHand.MAIN_HAND);
    }

    @Override
    public void stop() {
        super.stop();
        this.target = null;
        this.seeTime = 0;
        this.attackDelay = -1;
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        double distanceSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean canSee = this.mob.getSensing().hasLineOfSight(this.target);

        if (canSee) {
            this.seeTime++;
        } else {
            this.seeTime = 0;
        }

        if (distanceSqr <= (double)this.attackRadiusSqr && canSee) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        }

        this.mob.getLookControl().setLookAt(this.target);

        if (--this.attackDelay == 0) {
            if (!canSee) {
                return;
            }

            interactMusket(true);

            this.attackDelay = 10;
        } else if (this.attackDelay < 0) {
            this.attackDelay = 5;
        }
    }
}
