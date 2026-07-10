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
package org.ragingzombies.flintnpowder.core_modified.guns;

import com.livelandr.flintcore.core.guns.BlazelockBase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.enchantments.ModEnchantments;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class BlazelockBaseEnchantable extends BlazelockBase {
    public BlazelockBaseEnchantable(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == ModEnchantments.QUALITY_PROPELLANT.get() ||
                enchantment == ModEnchantments.TRIGGER_FINGER.get() ||
                enchantment == ModEnchantments.SWIFT_RELOAD.get() ||
                super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 22;
    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo ,InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter,
                SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onAmmo(pLevel, shooter, gun, ammo, pUsedHand);
    }

    @Override
    public void onTryFailure(Level pLevel, LivingEntity pPlayer, ItemStack gun) {
        pLevel.playSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onTryFailure(pLevel, pPlayer, gun);
    }

    @Override
    public int ammoCooldown(LivingEntity ply, ItemStack gun) {
        int amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SWIFT_RELOAD.get(), gun);
        return ammoCooldownTicks - (int) (ammoCooldownTicks/4F) * amoLevel;
    }

    @Override
    public int shootCooldown(LivingEntity ply, ItemStack gun) {
        int amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.TRIGGER_FINGER.get(), gun);
        return shootCooldownTicks - (int) (shootCooldownTicks/4F) * amoLevel;
    }

    @Override
    public void onCocking(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onCocking(pLevel, shooter, gun);
    }

    @Override
    public void onChamberOpen(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onChamberOpen(pLevel, shooter, gun);
    }

    @Override
    public void onChamberClose(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSound(null, shooter,
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onChamberClose(pLevel, shooter, gun);
    }

    @Override
    public void onAmmoInsert(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter,
                SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.0F);

        super.onAmmoInsert(pLevel, shooter, gun, pUsedHand);
    }

}
