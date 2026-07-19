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
package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.livelandr.flintcore.core.ammo.BaseAmmo;
import com.livelandr.flintcore.core.guns.GunBase;
import com.livelandr.flintcore.core.util.CameraWork;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.FlamingBuckshotProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class OilFlameshot extends BaseAmmo {
    public OilFlameshot(Properties pProperties) {
        super(pProperties);
        this.damage = 2;

        addRequiredTag("flaming");
    }

    @Override
    public void onAmmoShot(float xRotation, float yRotation, LivingEntity shooter, ItemStack gun, Level level) {
        if (shooter.level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) shooter.level();
        int currentTick = serverLevel.getServer().getTickCount();


        for (int i = 0; i < 10; i++) {
            // Delay
            ServerTickHandler.createTask(i*4, () -> {
                Random rand = new Random();
                serverLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                        SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 9.0F, 0.3F + rand.nextFloat(0.8F));

                FlamingBuckshotProjectile proj = new FlamingBuckshotProjectile(level, shooter);

                proj.damage = this.damage * ((GunBase) gun.getItem()).damageModifier(shooter, gun);
                proj.setOwner(shooter);

                proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter)-5, CameraWork.getPlayerViewY(shooter), 0.0F, 2F, 2F * ((GunBase) gun.getItem()).accuracyModifier(shooter, gun));

                if (!level.isClientSide()) level.addFreshEntity(proj);
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("flintnpowder.incendiary"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire_charge.description_1"));
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("flintcore.bullet_description"));
        pTooltipComponents.add(Component.translatable("flintcore.projectile_damage")
                .append(String.valueOf(Math.round(this.damage)))
                .append("x10").withStyle(ChatFormatting.DARK_GREEN));
    }
}
