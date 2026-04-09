package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.ragingzombies.flintnpowder.core.attachments.AttachmentBase;
import org.ragingzombies.flintnpowder.sound.ModSounds;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

public class GunBase extends Item {

    public int cooldownTicks = 20;
    public int shootCooldownTicks = 20;
    public int gunpowderCooldownTicks = 20;
    public int ramrodCooldownTicks = 20;
    public int reloadPitch = 1;

    public GunBase(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose GUN_AIM = HumanoidModel.ArmPose.create("GUN_AIM", true, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.body.yRot = 0.5F;

                    model.rightArm.xRot = (float) (-Math.PI*0.5F);
                    model.rightArm.x = -4;
                    model.rightArm.z = -1;

                    model.leftArm.xRot = (float) (-Math.PI*0.5F);
                    model.leftArm.yRot = (float) (Math.PI*0.25F);
                } else {
                    model.body.yRot = -0.5F;

                    model.leftArm.xRot = (float) -(Math.PI*0.5F);
                    model.leftArm.x = 4;
                    model.leftArm.z = 1;

                    model.rightArm.xRot = (float) -(Math.PI*0.5F);
                    model.rightArm.yRot = (float) -(Math.PI*0.25);
                }
            });

            private static final HumanoidModel.ArmPose GUN_RELOAD = HumanoidModel.ArmPose.create("GUN_RELOAD", true, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = (float) (-Math.PI*0.25F);
                    model.rightArm.yRot = (float) -(Math.PI*0.15F);
                    model.rightArm.zRot = (float) -(Math.PI*0.05F);

                    model.leftArm.xRot = (float) (-Math.PI*0.25F);
                    model.leftArm.yRot = (float) (Math.PI*0.25F);
                } else {
                    model.leftArm.xRot = (float) (-Math.PI*0.25F);
                    model.leftArm.yRot = (float) (Math.PI*0.15F);
                    model.leftArm.zRot = (float) (Math.PI*0.05F);

                    model.rightArm.xRot = (float) (-Math.PI*0.25F);
                    model.rightArm.yRot = (float) -(Math.PI*0.25F);
                }
            });

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (itemStack.getOrCreateTag().getBoolean("IsAiming")) {
                        return GUN_AIM;
                    } else {
                        return GUN_RELOAD;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }
        });
    }

    public void setAimAnimation(ItemStack gun) {
        gun.getOrCreateTag().putBoolean("IsAiming", true);
    }
    public void setReloadAnimation(ItemStack gun) {
        gun.getOrCreateTag().putBoolean("IsAiming", false);
    }

    public boolean checkAmmo(Item ammo) {
        return false;
    }

    // Attachments
    public boolean checkAttachment(ItemStack gun, Item attachment) {
        return false;
    }

    public void setAttachment(ItemStack gun, AttachmentBase attachment) {

    }


    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        return true;
    }

    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        return true;
    }

    public void onTryFailure(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);
    }


    public float damageModifier() {
        return 1;
    }
    public float recoilModifierX() {
        return 1;
    }
    public float recoilModifierY() {
        return 1;
    }
    public float accuracyModifier() { return 1; }
    public float gunpowderCooldown() { return 20; }
    public float ramrodCooldown() { return 60; }


    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {}

    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTPRIME.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo ,InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RIFLERELOAD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }
}
