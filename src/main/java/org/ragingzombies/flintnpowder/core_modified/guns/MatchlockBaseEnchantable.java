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

import com.livelandr.flintcore.core.guns.FlintlockBase;
import com.livelandr.flintcore.core.guns.MatchlockBase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.enchantments.ModEnchantments;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class MatchlockBaseEnchantable extends MatchlockBase {
    public MatchlockBaseEnchantable(Properties pProperties) {
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
                 enchantment == ModEnchantments.RAMROD_MASTERY.get() ||
                super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo ,InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
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
    public int getEnchantmentValue(ItemStack stack) {
        return 22;
    }

    @Override
    public int gunpowderCooldown(LivingEntity ply, ItemStack gunStack) {
        int amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SWIFT_RELOAD.get(), gunStack);
        return gunpowderCooldownTicks - (gunpowderCooldownTicks/4) * amoLevel;
    }
    @Override
    public int ramrodCooldown(LivingEntity ply, ItemStack gunStack) {
        int amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RAMROD_MASTERY.get(), gunStack);
        return ramrodCooldownTicks - (ramrodCooldownTicks/4) * amoLevel;
    }

    @Override
    public void onGunpowder(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.SAND_BREAK, SoundSource.NEUTRAL, 1F, 1.0F);

        super.onGunpowder(pLevel, shooter, gun, pUsedHand);
    }

    @Override
    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1F, 1.0F);

        super.onStuff(pLevel, shooter, gun, pUsedHand);
    }

    @Override
    public void onCock(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTPRIME.get(), SoundSource.NEUTRAL, 0.15F, 1.0F);

        super.onCock(pLevel, shooter, gun, pUsedHand);
    }

    @Override
    public boolean isRamrod(ItemStack item) {
        return item.is(ModItems.RAMROD.get());
    }
}
