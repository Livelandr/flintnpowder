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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.item.ModItems;
import org.ragingzombies.flintnpowder.item.ammo.CastIronRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.CopperRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Flinter extends FlintlockBase {
    public Flinter(Properties pProperties) {
        super(pProperties);
        shootCooldownTicks = 25;
    }

    @Override
    public float accuracyModifier() {
        return 2.5F;
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

            for (int i = 0; i < 7; i++) {
                double speed = 0.15;
                double spread = 0.02;

                sLevel.sendParticles(
                        ParticleTypes.POOF,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.6, shooter.getZ(),
                        25,
                        shooter.getDeltaMovement().x * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        shooter.getDeltaMovement().y * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        shooter.getDeltaMovement().z * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        0.5
                );
            }
        }
    }

    @Override
    public boolean checkAmmo(Item ammo) {
        if (ammo instanceof CopperRoundshot) {
            return true;
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.flinter.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.flinter.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.flinter.description_2"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.flinter.description_3"));
        pTooltipComponents.add(Component.literal(""));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
