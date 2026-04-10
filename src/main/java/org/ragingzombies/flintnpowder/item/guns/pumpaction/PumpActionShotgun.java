package org.ragingzombies.flintnpowder.item.guns.pumpaction;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import org.ragingzombies.flintnpowder.core.guns.PumpActionBase;
import org.ragingzombies.flintnpowder.item.ModItems;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellDragon;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellSlug;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class PumpActionShotgun extends PumpActionBase {
    public PumpActionShotgun(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean checkAmmo(Item ammo) {
        if (ammo instanceof ShotgunShell) {
            return true;
        }
        if (ammo instanceof ShotgunShellSlug) {
            return true;
        }
        if (ammo instanceof ShotgunShellDragon) {
            return true;
        }

        return false;
    }

    @Override
    public float accuracyModifier() {
        return 2;
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
    public void OnCockStart(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNPUMPBACK.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 8);
        }
    }

    @Override
    public void OnCockEnd(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand){
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNPUMPFORW.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 8);
        }
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (GetAmmoAmount(gun) == 0) return false;

        return true;
    }

    @Override
    public boolean checkAttachmentComparability(Player ply, ItemStack gun, Item attachment) {
        return (attachment == ModItems.SILENCER.get());
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }

        if (!isAttachmentValidAndEnabled(gunStack, "Silencer")) {
            pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                    ModSounds.SHOTGUNSHOT.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);
            pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                    ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 10.0F, 1.0F, 0);

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
        } else {
            pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                    ModSounds.SHOTGUNSHOTSILENCED.get(), SoundSource.NEUTRAL, 2.0F, 1.0F, 0);

            if (!pLevel.isClientSide()) {
                ServerLevel sLevel = (ServerLevel) pLevel;

                for (int index1 = 0; index1 < 5; index1++) {
                    double speed = 0.22;
                    double spread = 0.12;

                    sLevel.sendParticles(
                            ParticleTypes.SMOKE,
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

    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNRELOAD.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        setReloadAnimation(gun);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gun));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_2"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_3"));
        pTooltipComponents.add(Component.literal(""));

        int totalAttach = 0;
        if (isAttachmentValidAndEnabled(pStack, "Silencer")) {
            ItemStack item = getAttachmentStack(pStack, "Silencer");
            pTooltipComponents.add(Component.translatable("flintnpowder.attachment").append(item.getDisplayName()));
            item.getItem().appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

            totalAttach++;
        }

        if (totalAttach > 0) {
            pTooltipComponents.add(Component.literal(""));
        }


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
