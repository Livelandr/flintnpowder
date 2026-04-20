package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class Pistol extends FlintlockBase {
    public Pistol(Properties pProperties) {
        super(pProperties);
        shootCooldownTicks = 10;
        gunpowderCooldownTicks = 15;
        ramrodCooldownTicks = 40;

        addAllowedAmmo(ModItemsAmmo.CASTIRONROUNDSHOT.get());
        addAllowedAmmo(ModItemsAmmo.COPPERROUNDSHOT.get());
    }

    @Override
    public float accuracyModifier(UUID ply, ItemStack gun) {
        return 2 * super.accuracyModifier(ply, gun);
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.PISTOLSHOOT.get(), SoundSource.NEUTRAL, 3.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.PISTOLDISTANTSHOOT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setReloadAnimation(gunStack);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you.
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

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.pistol.description_0"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
