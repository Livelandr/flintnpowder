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
package org.ragingzombies.flintnpowder.item.guns.other;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.livelandr.flintcore.core.guns.GunBase;
import com.livelandr.flintcore.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.TheRockProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.livelandr.flintcore.core.util.CameraWork.OffsetEntityCamera;

// 🙏
public class LogCannon extends GunBase {
    public LogCannon(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 3 * super.accuracyModifier(ply, gun);
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
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        super.onShoot(rotationX, rotationY, pLevel, shooter, gunStack);
        pLevel.playSound(null, shooter,
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 5.0F, 1.5F);
        pLevel.playSound(null, shooter,
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 7.0F, 1.1F);

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
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        1.0
                );
            }
        }

        super.onShoot(rotationX, rotationY, pLevel, shooter, gunStack);

        gunStack.shrink(1);
    }


    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, pPlayer,
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        setAimAnimation(gun);

        if (pPlayer instanceof Player) {
            setCooldown(pPlayer, gun, 20);
        }

        Random generator = new Random();
        return generator.nextDouble() <= 0.1;
    }

    @Override
    public void shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        GunBase gun = (GunBase) gunStack.getItem();

        TheRockProjectile proj = new TheRockProjectile(pLevel, pPlayer);

        proj.damage = 15 * gun.damageModifier(pPlayer, gunStack);
        proj.setOwner(pPlayer);

        proj.shootFromRotation(pPlayer, CameraWork.getPlayerViewX(pPlayer), CameraWork.getPlayerViewY(pPlayer), 0.0F, 4F, 1.1F * gun.accuracyModifier(pPlayer, gunStack));

        // Recoil
        if (pPlayer instanceof Player) {
            OffsetEntityCamera(pPlayer, -50, 0);
        }

        if (!pLevel.isClientSide()) pLevel.addFreshEntity(proj);

        super.shoot(pLevel, pPlayer, gunStack);
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
