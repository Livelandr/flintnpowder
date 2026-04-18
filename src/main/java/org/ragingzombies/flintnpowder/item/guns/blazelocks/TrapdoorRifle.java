package org.ragingzombies.flintnpowder.item.guns.blazelocks;

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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.BlazelockBase;
import org.ragingzombies.flintnpowder.item.ammo.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ammo.PistolRound;
import org.ragingzombies.flintnpowder.item.ammo.RifleRound;
import org.ragingzombies.flintnpowder.item.attachments.HighProfileOptic;
import org.ragingzombies.flintnpowder.item.attachments.LowProfileOptic;
import org.ragingzombies.flintnpowder.item.attachments.ModItemsAttachments;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TrapdoorRifle extends BlazelockBase {
    public TrapdoorRifle(Properties pProperties) {
        super(pProperties);
        maxAmmo = 1;
        shootCooldownTicks = 15;
        needCocking = true;

        addAllowedAmmo(ModItemsAmmo.RIFLEROUND.get());

        addAllowedAttachment(ModItemsAttachments.HIGHPROFILEOPTIC.get());
        addAllowedAttachment(ModItemsAttachments.LOWPROFILEOPTIC.get());
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.RARE;
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (secondItemStack.is(Items.AIR)) return true;

        return false;
    }

    @Override
    public void onAmmoInsert(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RIFLERELOAD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 5);
        }
    }

    @Override
    public float accuracyModifier(UUID ply){
        return 1.25F * super.accuracyModifier(ply);
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTPRIME.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                 ModSounds.RIFLESHOOT.get(), SoundSource.NEUTRAL, 8.0F, 1.0F, 0);


        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you.
            for (int index0 = 0; index0 < 25; index0++) {
                double speed = 0.15;
                double spread = 0.32;

                sLevel.sendParticles(
                        ParticleTypes.FLAME,
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

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.trapdoor_rifle.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.trapdoor_rifle.description_1"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
