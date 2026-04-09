package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.item.ammo.CastIronRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class Musket extends FlintlockBase {
    public Musket(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float accuracyModifier() {
        return 2;
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTPRIME.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.MUSKETFIRE.get(), SoundSource.NEUTRAL, 3.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F, 0);



        setReloadAnimation(gunStack);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;

            for (int i = 0; i < 7; i++) {
                double speed = 0.55;
                double spread = 0.12;

                sLevel.sendParticles(
                        ParticleTypes.POOF,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.6, shooter.getZ(),
                        25,
                        shooter.getDeltaMovement().x * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        shooter.getDeltaMovement().y * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        shooter.getDeltaMovement().z * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        1.0
                );
            }
            for (int i = 0; i < 5; i++) {
                double speed = 0.22;
                double spread = 0.28;

                sLevel.sendParticles(
                        ParticleTypes.LARGE_SMOKE,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.5, shooter.getZ(),
                        10,
                        shooter.getDeltaMovement().x * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        shooter.getDeltaMovement().y * speed + Mth.nextDouble(RandomSource.create(), -spread, spread) + 0.03,
                        shooter.getDeltaMovement().z * speed + Mth.nextDouble(RandomSource.create(), -spread, spread),
                        1.0
                );
            }
        }

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    @Override
    public boolean checkAmmo(Item ammo) {
        if (ammo instanceof CastIronRoundshot) {
            return true;
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.musket.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.musket.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.musket.description_2"));
        pTooltipComponents.add(Component.literal(""));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
