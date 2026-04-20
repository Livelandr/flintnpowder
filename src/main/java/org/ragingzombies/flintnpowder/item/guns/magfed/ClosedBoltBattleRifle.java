package org.ragingzombies.flintnpowder.item.guns.magfed;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.MagfedBase;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ModItemsAttachments;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ClosedBoltBattleRifle extends MagfedBase {
    public ClosedBoltBattleRifle(Properties pProperties) {
        super(pProperties);
        shootCooldownTicks = 15;

        addAllowedMagazine(ModItemsAmmo.BATTLERIFLEMAGAZINE.get());

        addAllowedAttachment(ModItemsAttachments.LOWPROFILEOPTIC.get());
        addAllowedAttachment(ModItemsAttachments.HIGHPROFILEOPTIC.get());
    }

    @Override
    public float accuracyModifier(UUID ply, ItemStack gun) {
        return 1.2F * super.accuracyModifier(ply, gun);
    }


    public void onMagExtract(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.BRMAGOUT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setReloadAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 45);
        }
    }

    public void onMagInsert(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.BRMAGOUT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 45);
        }
    }

    @Override
    public void onSlideStart(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.BRBOLTBACK.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 25);
        }
    }

    @Override
    public void onSlideEnd(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.BRBOLTFORW.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 25);
        }
    }

    @Override
    public float damageModifier(UUID shooter, ItemStack gun) {
        return 0.9F*super.damageModifier(shooter, gun);
    }

    @Override
    public float recoilModifierX(UUID id, ItemStack gun) {
        return 2F*super.recoilModifierX(id, gun);
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {

        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.BRFIRE.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F, 0);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            for (int index0 = 0; index0 < 3; index0++) {
                double speed = 0.15;
                double spread = 0.05;

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
        }

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.EPIC;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.open_bolt_smg.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.rifle.description_1"));


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
