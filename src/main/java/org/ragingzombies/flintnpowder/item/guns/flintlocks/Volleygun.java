package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Volleygun extends FlintlockBase {
    public Volleygun(Properties pProperties) {
        super(pProperties);
        shootCooldownTicks = 25;
        noCock = true;

        addAllowedAmmo(ModItemsAmmo.COPPERVOLLEYSHOT.get());
    }

    @Override
    public float accuracyModifier(UUID ply, ItemStack gun) {
        return 2F * super.accuracyModifier(ply, gun);
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
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (pPlayer instanceof Player) {
            ((Player) pPlayer).getCooldowns().addCooldown(this, 20);
        }

        Random generator = new Random();
        return generator.nextDouble() <= 0.2;
    }

    @Override
    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 35);
        }
    }

    @Override
    public boolean isRamrod(ItemStack item) {
        return item.is(Items.STICK);
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {

        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.PISTOLSHOOT.get(), SoundSource.NEUTRAL, 2.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.PISTOLDISTANTSHOOT.get(), SoundSource.NEUTRAL, 8.0F, 1.0F, 0);

        setReloadAnimation(gunStack);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you. (shnorfpf)
            // hiii :3 (livelandr)
            for (int index0 = 0; index0 < 25; index0++) {
                double speed = 0.55;
                double spread = 0.12;

                sLevel.sendParticles(
                        ParticleTypes.POOF,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.6, shooter.getZ(),
                        0,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        1.0
                );
            }
            for (int index1 = 0; index1 < 15; index1++) {
                double speed = 0.22;
                double spread = 0.28;

                sLevel.sendParticles(
                        ParticleTypes.LARGE_SMOKE,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.5, shooter.getZ(),
                        0,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        1.0
                );
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.volleygun.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.volleygun.description_1"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
