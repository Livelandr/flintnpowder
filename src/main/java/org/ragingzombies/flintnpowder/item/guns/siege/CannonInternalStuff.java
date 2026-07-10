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

import com.livelandr.flintcore.core.guns.FlintlockBase;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.Vec3;
import org.ragingzombies.flintnpowder.core_modified.guns.FlintlockBaseEnchantable;
import org.ragingzombies.flintnpowder.entity.custom.CannonEntity;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class CannonInternalStuff extends FlintlockBaseEnchantable {
    public CannonInternalStuff(Properties pProperties) {
        super(pProperties);

        noCock = true;
        gunpowderRequired = 4;

        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 60;

        addCompatibleCaliberTag("cannonball");
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.UNCOMMON;
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

    // This is reason why I hate encapsulation
    protected final Vec3 calculateViewVector(float pXRot, float pYRot) {
        float f = pXRot * ((float)Math.PI / 180F);
        float f1 = -pYRot * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSound(null, shooter,
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 2.0F, 0.75F);

        setReloadAnimation(gunStack);
        shooter.addDeltaMovement(shooter.getForward().multiply(-1,0,-1));

        // Particles
        if (!pLevel.isClientSide()) {
            ((CannonEntity) shooter).aimAngleX(shooter.getXRot() - 10F);
            ServerLevel sLevel = (ServerLevel) pLevel;
            Vec3 lookAng = this.calculateViewVector(rotationX, rotationY);

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
                        shooter.getDeltaMovement().x + lookAng.x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + lookAng.y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + lookAng.z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
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
                        shooter.getDeltaMovement().x + lookAng.x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + lookAng.y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + lookAng.z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        1.0
                );
            }
        }

        setCooldown(shooter, gunStack, shootCooldown(shooter, gunStack));
    }
}
