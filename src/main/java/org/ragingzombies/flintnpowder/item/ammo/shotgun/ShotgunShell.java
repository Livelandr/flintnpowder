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
package org.ragingzombies.flintnpowder.item.ammo.shotgun;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.livelandr.flintcore.core.ammo.BaseAmmo;
import com.livelandr.flintcore.core.guns.GunBase;
import com.livelandr.flintcore.core.util.CameraWork;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.BuckshotProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.livelandr.flintcore.core.util.CameraWork.OffsetEntityCamera;

public class ShotgunShell extends BaseAmmo {
    public ShotgunShell(Properties pProperties) {
        super(pProperties);
        this.damage = 4F;
        this.customDescription = true;

        addRequiredTag("12gauge");
    }

    @Override
    public void onAmmoShot(float xRotation, float yRotation, LivingEntity shooter, ItemStack gun, Level level) {
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            float angle = rand.nextFloat((float) (2.0F*Math.PI));
            float radius = rand.nextFloat(7 );

            BuckshotProjectile proj = new BuckshotProjectile(level, shooter);

            proj.setOwner(shooter);
            proj.moveTo(shooter.getX(), shooter.getEyeY()-0.1, shooter.getZ(), shooter.getXRot(), shooter.getYRot());
            proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter) + (float)(Math.cos(angle)*radius),
                    CameraWork.getPlayerViewY(shooter) + (float)(Math.sin(angle)*radius), 0.0F, 5F,2 * ((GunBase) gun.getItem()).accuracyModifier(shooter, gun));
            proj.setDamage(this.damage * ((GunBase) gun.getItem()).damageModifier(shooter, gun));

            if (!level.isClientSide()) level.addFreshEntity(proj);
        }


        // Recoil
        float angleX = rand.nextFloat(4.0F);
        OffsetEntityCamera(shooter, (-25 + (angleX - 2)) * ((GunBase) gun.getItem()).recoilModifierX(shooter, gun), (angleX - 2) * ((GunBase) gun.getItem()).recoilModifierY(shooter, gun));
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("flintcore.bullet_description"));
        pTooltipComponents.add(Component.translatable("flintcore.projectile_damage")
                .append(String.valueOf(Math.round(this.damage)))
                .append("x9").withStyle(ChatFormatting.DARK_GREEN));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
