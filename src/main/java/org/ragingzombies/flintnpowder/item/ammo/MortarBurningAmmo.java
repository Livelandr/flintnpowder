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

import com.livelandr.flintcore.core.ammo.BaseAmmo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.mortar.FlamingShellProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.mortar.HEShellProjectile;

import javax.annotation.Nullable;
import java.util.List;

public class MortarBurningAmmo extends BaseAmmo {
    public MortarBurningAmmo(Properties pProperties) {
        super(pProperties);

        addRequiredTag("mortarold");
    }

    @Override
    public void onAmmoShot(float xRotation, float yRotation, LivingEntity shooter, ItemStack gun, Level level) {
        FlamingShellProjectile proj = new FlamingShellProjectile(level, shooter);

        proj.setOwner(shooter);
        proj.shootFromRotation(shooter, xRotation, yRotation, 0.0F, 4F, 0);

        if (!level.isClientSide()) level.addFreshEntity(proj);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.cast_iron_bomb.description_0"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
