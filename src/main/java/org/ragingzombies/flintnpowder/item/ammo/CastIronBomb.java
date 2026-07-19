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

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.livelandr.flintcore.core.ammo.BaseAmmo;
import com.livelandr.flintcore.core.guns.GunBase;
import com.livelandr.flintcore.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronBombProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.livelandr.flintcore.core.util.CameraWork.OffsetEntityCamera;

public class CastIronBomb extends BaseAmmo {
    public CastIronBomb(Properties pProperties) {
        super(pProperties);

        damage = 5;
        addRequiredTag("bigbomb");
    }

    @Override
    public void onAmmoShot(float xRotation, float yRotation, LivingEntity shooter, ItemStack gun, Level level) {
        CastIronBombProjectile proj = new CastIronBombProjectile(level, shooter);

        proj.damage = this.damage * ((GunBase) gun.getItem()).damageModifier(shooter, gun);
        proj.setOwner(shooter);

        proj.shootFromRotation(shooter,xRotation, yRotation, 0.0F, 3F, 1F * ((GunBase) gun.getItem()).accuracyModifier(shooter, gun));

        // Recoil

        if (shooter instanceof Player) {
            Random rand = new Random();
            float angleX = rand.nextFloat(4.0F);
            OffsetEntityCamera(shooter, (-7 + (angleX - 2)) * ((GunBase) gun.getItem()).recoilModifierX(shooter, gun), (angleX - 2) * ((GunBase) gun.getItem()).recoilModifierY(shooter, gun));
        }

        if (!level.isClientSide()) level.addFreshEntity(proj);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.cast_iron_bomb.description_0"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
