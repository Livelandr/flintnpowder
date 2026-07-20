/*
 * Copyright (C) 2026 Livelandr
 *
 * This file is part of Flint'N'Powder.
 *
 * Flint'N'Powder is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Flint'N'Powder is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.ragingzombies.flintnpowder.item.guns.earlyendlock;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core_modified.guns.PumpActionBaseEnchantable;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class PumpActionShotgun extends PumpActionBaseEnchantable {
    public PumpActionShotgun(Properties pProperties) {
        super(pProperties);
        needCockToReload = false;

        addCompatibleCaliberTag("12gauge");
        addAttachmentSlot("silencer");
        addAttachmentSlot("optic");
    }

    @Override
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 2F * super.accuracyModifier(ply, gun);
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.EPIC;
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
        pLevel.playSound(null, shooter,
                ModSounds.SHOTGUNPUMPBACK.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        if (shooter instanceof Player) {
            setCooldown(shooter, gun, 8);
        }

        super.OnCockStart(pLevel, shooter, gun, pUsedHand);
    }

    @Override
    public void OnCockEnd(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand){
        pLevel.playSound(null, shooter,
                ModSounds.SHOTGUNPUMPFORW.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        setAimAnimation(gun);

        if (shooter instanceof Player) {
            setCooldown(shooter, gun, 8);
        }

        super.OnCockEnd(pLevel, shooter, gun, pUsedHand);
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (GetAmmoAmount(gun) == 0) return false;

        return true;
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {

        super.onShoot(rotationX, rotationY, pLevel, shooter, gunStack);

        if (!isAttachmentValidAndEnabled(gunStack, "silencer")) {
            pLevel.playSound(null, shooter,
                    ModSounds.SHOTGUNSHOT.get(), SoundSource.NEUTRAL, 5.0F, 1.0F);
            pLevel.playSound(null, shooter,
                    ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 10.0F, 1.0F);

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
                            shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
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
                            shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            1.0
                    );
                }
            }
        } else {
            pLevel.playSound(null, shooter,
                    ModSounds.SHOTGUNSHOTSILENCED.get(), SoundSource.NEUTRAL, 1.5F, 1.0F);

            if (!pLevel.isClientSide()) {
                ServerLevel sLevel = (ServerLevel) pLevel;

                for (int index1 = 0; index1 < 5; index1++) {
                    double speed = 0.22;
                    double spread = 0.12;

                    sLevel.sendParticles(
                            ParticleTypes.SMOKE,
                            shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.5, shooter.getZ(),
                            0,
                            shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                            1.0
                    );
                }
            }
        }

    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter,
                ModSounds.SHOTGUNRELOAD.get(), SoundSource.NEUTRAL, 5.0F, 1.0F);

        if (gun.getTag().getBoolean("IsUncocked")) {
            setReloadAnimation(gun);
        }

        

        if (shooter instanceof Player) {
            setCooldown(shooter, gun, ammoCooldown(shooter, gun));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_3"));


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
