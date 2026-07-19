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
import com.livelandr.flintcore.core.guns.GunBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronCannonballProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.HeavyCastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;

import java.util.Random;

import static com.livelandr.flintcore.core.util.CameraWork.OffsetEntityCamera;

public class CastIronCannonball extends BaseAmmo {
    public CastIronCannonball(Properties pProperties) {
        super(pProperties);

        addRequiredTag("cannonball");
    }

    @Override
    public void onAmmoShot(float xRotation, float yRotation, LivingEntity shooter, ItemStack gun, Level level) {
        CastIronCannonballProjectile proj = new CastIronCannonballProjectile(ModProjectiles.CASTIRONCANNONBALL.get(), shooter, level);
        proj.setOwner(shooter);

        proj.shootFromRotation(shooter,xRotation, yRotation, 0.0F, 6.5F, 2F * ((GunBase) gun.getItem()).accuracyModifier(shooter, gun));

        if (!level.isClientSide()) level.addFreshEntity(proj);
    }
}
