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
import com.livelandr.flintcore.core.guns.GunBase;
import com.livelandr.flintcore.core.util.HookContext;
import com.livelandr.flintcore.core.util.HookSystem;
import com.livelandr.flintcore.core.util.ServerTickHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.core_modified.guns.FlintlockBaseEnchantable;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class MortarModern extends GunBase {
    public float angleX = 0;
    public float angleY = 0;

    public MortarModern(Properties pProperties) {
        super(pProperties);

        addCompatibleCaliberTag("mortarshell");
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack secondItemStack;
        secondItemStack = pPlayer.getItemInHand(pUsedHand);

        return secondItemStack.is(Items.FLINT_AND_STEEL);
    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo, InteractionHand pUsedHand) {
        super.onAmmo(pLevel, shooter, gun, ammo, pUsedHand);
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSound(null, shooter,
                ModSounds.MORTAR_FIRE.get(), SoundSource.NEUTRAL, 5.0F, 1.0F + Mth.nextFloat(shooter.getRandom(),-0.25F, 0.25F));

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you.
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

        super.onShoot(rotationX, rotationY, pLevel, shooter, gunStack);
    }


    @Override
    public void shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack, float rotationX, float rotationY) {
        super.shoot(pLevel, pPlayer, gunStack, rotationX, rotationY);

        ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
                if (HookSystem.calculateHookBool(new HookContext.Builder("processShooting")
                .shooter(pPlayer)
                .gun(gunStack)
                .rotationX(rotationX)
                .rotationY(rotationY)
                .ammoType(ammo)
                .build(), 1)) {
           ammo.onAmmoShot(rotationX, rotationY, pPlayer, gunStack, pLevel);
        }

        if (!pLevel.isClientSide()) {
            gunStack.getTag().putBoolean("HasAmmo", false);
        }
    }

    @Override
    public boolean interaction(Level pLevel, LivingEntity pPlayer, ItemStack gunStack, InteractionHand pUsedHand, boolean proxy, float proxyX, float proxyY, LivingEntity proxyUser) {
        if (!checkCooldown(gunStack)) {
            return false;
        }

        ItemStack secondItemStack;

        if (!proxy) {
            if (pUsedHand == InteractionHand.MAIN_HAND)
                secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
            else
                secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        } else {
            secondItemStack = proxyUser.getItemInHand(pUsedHand);
        }

        if (!gunStack.hasTag()) gunStack.setTag(new CompoundTag());

        // Try to add ammo
        if (!gunStack.getTag().getBoolean("HasAmmo")) {
            if (checkAmmoCompatibility(secondItemStack.getItem())) {
                // Putting Ammo
                CompoundTag ammoData = secondItemStack.serializeNBT();
                gunStack.getTag().put("AmmoType", ammoData);
                gunStack.getTag().putBoolean("HasAmmo", true);

                //secondItemStack.shrink(1);
                onAmmo(pLevel, pPlayer, gunStack, secondItemStack, pUsedHand);
                ((BaseAmmo) secondItemStack.getItem()).onAmmoInsert(pPlayer, gunStack, secondItemStack);

                ServerTickHandler.createTask(10, () -> {
                    if (gunStack == null || pPlayer == null) return;
                    shoot(pLevel, pPlayer, gunStack, proxyX, proxyY);
                });
            }

        }

        return true;
    }
}
