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
package org.ragingzombies.flintnpowder.item.guns.siege;

import com.livelandr.flintcore.core.ammo.BaseAmmo;
import com.livelandr.flintcore.core.guns.FlintlockBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core_modified.guns.FlintlockBaseEnchantable;
import org.ragingzombies.flintnpowder.entity.custom.CannonEntity;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class MortarOld extends FlintlockBaseEnchantable {
    public float angleX = 0;
    public float angleY = 0;

    public MortarOld(Properties pProperties) {
        super(pProperties);

        noCock = true;
        gunpowderRequired = 2;

        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 60;

        addCompatibleCaliberTag("mortarold");
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.UNCOMMON;
    }


    @Override
    public void shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack, float rotationX, float rotationY) {
        //super.shoot(pLevel, pPlayer, gunStack, rotationX, rotationY);
        ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
        ammo.onAmmoShot((float) (-angleX+Math.toRadians(180.0)), angleY, pPlayer, gunStack, pLevel);

        onShoot(rotationX, rotationY, pLevel, pPlayer, gunStack);

        if (!pLevel.isClientSide()) {
            gunStack.getTag().putInt("Gunpowder", 0);
            gunStack.getTag().putBoolean("HasAmmo", false);
            gunStack.getTag().putBoolean("IsCocked", false);
            gunStack.getTag().putBoolean("IsStuffed", false);
        }
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.FLINT_AND_STEEL)) {
            return super.allowPressingTrigger(pLevel, pPlayer, gun, pUsedHand);
        }
        return false;
    }

    public float damageModifier(LivingEntity shooter, ItemStack gun) {
        return 1.05F*super.damageModifier(shooter, gun);
    }

    @Override
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 2F * super.accuracyModifier(ply, gun);
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSound(null, shooter,
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 2.0F, 0.75F);


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

        setCooldown(shooter, gunStack, shootCooldown(shooter, gunStack));
    }
}
