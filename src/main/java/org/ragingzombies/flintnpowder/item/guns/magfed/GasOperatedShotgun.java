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
package org.ragingzombies.flintnpowder.item.guns.magfed;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core_modified.guns.MagfedBaseEnchantable;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class GasOperatedShotgun extends MagfedBaseEnchantable {
    public GasOperatedShotgun(Properties pProperties) {
        super(pProperties);
        shootCooldownTicks = 7;

        addCompatibleCaliberTag("12gaugemag");

        addAttachmentSlot("optic");
        addCompatibleAttachmentTag("sniper");
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
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 1.2F * super.accuracyModifier(ply, gun);
    }


    public void onMagExtract(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.AUTOSHOTGUNMAGOUT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        setReloadAnimation(gun);

        if (shooter instanceof Player ply) {
            setCooldown(ply, gun,  45);
        }
    }

    public void onMagInsert(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.AUTOSHOTGUNMAGIN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        if (shooter instanceof Player ply) {
            setCooldown(ply, gun,  45);
        }
    }

    @Override
    public void onSlideStart(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.AUTOSHOTGUNBOLTBACK.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onSlideStart(pLevel, shooter, gun);
    }

    @Override
    public void onSlideEnd(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.AUTOSHOTGUNBOLTFORW.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onSlideEnd(pLevel, shooter, gun);
    }

    @Override
    public float damageModifier(LivingEntity shooter, ItemStack gun) {
        return 0.9F*super.damageModifier(shooter, gun);
    }

    @Override
    public float recoilModifierX(LivingEntity id, ItemStack gun) {
        return 0.5F*super.recoilModifierX(id, gun);
    }

    @Override
    public float recoilModifierY(LivingEntity id, ItemStack gun) {
        return 0.5F*super.recoilModifierY(id, gun);
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {

        pLevel.playSound(null, shooter,
                ModSounds.BRFIRE.get(), SoundSource.NEUTRAL, 5.0F, 1.0F);
        pLevel.playSound(null, shooter,
                ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F);

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
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        1.0
                );
            }
        }

        if (shooter instanceof Player) {
            setCooldown(shooter, gunStack, shootCooldown(shooter, gunStack));
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
