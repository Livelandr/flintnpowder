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

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.livelandr.flintcore.core.ammo.BaseAmmo;
import com.livelandr.flintcore.core.guns.GunBase;
import com.livelandr.flintcore.core.util.CameraWork;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CopperRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;

import java.util.Random;

import static com.livelandr.flintcore.core.util.CameraWork.OffsetEntityCamera;

public class CopperVolleyshot extends BaseAmmo {
    public CopperVolleyshot(Properties pProperties) {
        super(pProperties);
        this.damage = 7;

        this.addRequiredTag("volleyshot");
    }

    @Override
    public void onAmmoShot(float xRotation, float yRotation, LivingEntity shooter, ItemStack gun, Level level) {
        if (shooter.level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) shooter.level();
        int currentTick = serverLevel.getServer().getTickCount();

        for (int i = 0; i < 5; i++) {
            // Delay
            ServerTickHandler.createTask(i*2, () -> {
                serverLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                        SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 9.0F, 1.0F);

                CopperRoundshotProjectile proj = new CopperRoundshotProjectile(ModProjectiles.COPPERROUNDSHOTPROJECTILE.get(), shooter, level);

                proj.setDamage(this.damage * ((GunBase) gun.getItem()).damageModifier(shooter, gun));
                proj.setOwner(shooter);
            proj.moveTo(shooter.getX(), shooter.getEyeY()-0.1, shooter.getZ(), shooter.getXRot(), shooter.getYRot());

                proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter)-5, CameraWork.getPlayerViewY(shooter), 0.0F, 10F, 4F * ((GunBase) gun.getItem()).accuracyModifier(shooter, gun));

                // Recoil
                if (shooter instanceof Player) {
                    Random rand = new Random();
                    float angleX = rand.nextFloat(4.0F);
                    OffsetEntityCamera(shooter, (-5 + (angleX - 2)) * ((GunBase) gun.getItem()).recoilModifierX(shooter, gun), (angleX - 2) * ((GunBase) gun.getItem()).recoilModifierY(shooter, gun));
                }

                if (!level.isClientSide()) level.addFreshEntity(proj);
            });
        }
    }
}
