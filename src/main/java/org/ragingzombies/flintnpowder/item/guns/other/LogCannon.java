package org.ragingzombies.flintnpowder.item.guns.other;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.TheRockProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

// 🙏
public class LogCannon extends GunBase {
    public LogCannon(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float accuracyModifier(UUID ply) {
        return 3 * super.accuracyModifier(ply);
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack gunStack = pPlayer.getItemInHand(pUsedHand);

        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        return secondItemStack.is(Items.FLINT);
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 5.0F, 1.5F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 7.0F, 1.1F, 0);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            for (int index1 = 0; index1 < 25; index1++) {
                double speed = 0.22;
                double spread = 0.28;

                sLevel.sendParticles(
                        ParticleTypes.LARGE_SMOKE,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.5, shooter.getZ(),
                        5,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        1.0
                );
            }
        }

        gunStack.shrink(1);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }
    }


    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (pPlayer instanceof Player) {
            ((Player) pPlayer).getCooldowns().addCooldown(this, 20);
        }

        Random generator = new Random();
        return generator.nextDouble() <= 0.1;
    }

    @Override
    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        GunBase gun = (GunBase) gunStack.getItem();

        TheRockProjectile proj = new TheRockProjectile(pLevel, pPlayer);

        proj.damage = 15 * gun.damageModifier();
        proj.setOwner(pPlayer);

        proj.shootFromRotation(pPlayer, CameraWork.getPlayerViewX(pPlayer), CameraWork.getPlayerViewY(pPlayer), 0.0F, 4F, 1.1F * gun.accuracyModifier(pPlayer.getUUID()));

        // Recoil
        if (pPlayer instanceof Player) {
            OffsetEntityCamera(pPlayer, -50, 0);
        }

        pLevel.addFreshEntity(proj);

    }

    // Damn it's EMPTY, there is NOTHING unique, and it makes it MOST unique gun
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        // Getting hand and offhand item
        ItemStack gunStack = pPlayer.getItemInHand(pUsedHand);

        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (!pLevel.isClientSide()) {
            if (!gunStack.hasTag()) gunStack.setTag(new CompoundTag());

            if (allowPressingTrigger(pLevel, pPlayer, gunStack, pUsedHand)) {
                if (tryShoot(pLevel, pPlayer, gunStack, pUsedHand)) {
                    Shoot(pLevel, pPlayer, gunStack);
                    onShoot(pLevel, pPlayer, gunStack);
                } else {
                    onTryFailure(pLevel, pPlayer, gunStack);
                }
            }

        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.log_cannon.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.log_cannon.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.log_cannon.description_2"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.log_cannon.description_3"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.log_cannon.description_4"));

        //super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
