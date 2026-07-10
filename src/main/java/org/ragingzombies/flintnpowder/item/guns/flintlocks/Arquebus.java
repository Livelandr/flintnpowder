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
package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import com.livelandr.flintcore.core.guns.MatchlockBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.livelandr.flintcore.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core_modified.guns.FlintlockBaseEnchantable;
import org.ragingzombies.flintnpowder.core_modified.guns.MatchlockBaseEnchantable;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class Arquebus extends FlintlockBaseEnchantable {

    public Arquebus(Properties pProperties) {
        super(pProperties);

        shootCooldownTicks = 30;
        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 60;

        noCock = true;

        addCompatibleCaliberTag("roundshot");

        addAttachmentSlot("optic");
    }


    @Override
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 1 * super.accuracyModifier(ply, gun);
    }

    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter,
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1F, 1.0F);

        setAimAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, ramrodCooldown(ply, gun));
        }
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack gunStack = pPlayer.getItemInHand(pUsedHand);

        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        return secondItemStack.is(Items.FLINT_AND_STEEL) && !pPlayer.isUnderWater();
    }

    @Override
    public void shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        gunStack.getTag().putInt("Gunpowder", 0);
        gunStack.getTag().putBoolean("HasAmmo", false);
        gunStack.getTag().putBoolean("IsCocked", false);
        gunStack.getTag().putBoolean("IsStuffed", false);

        pLevel.playSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, 1.0F, 1.0F);

        ServerTickHandler.createTask(25, () -> {
            triggerHooks("onShoot", pPlayer, gunStack);

            pLevel.playSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                    ModSounds.MUSKETFIRE.get(), SoundSource.NEUTRAL, 3.0F, 1.0F);
            pLevel.playSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                    ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F);

            ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

            BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
            ammo.onAmmoShot(pPlayer, gunStack, pLevel);

            setReloadAnimation(gunStack);
        });
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSound(null, shooter,
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        setReloadAnimation(gunStack);

        if (shooter instanceof Player) {
            setCooldown(shooter, gunStack, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.arquebus.description_0"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
