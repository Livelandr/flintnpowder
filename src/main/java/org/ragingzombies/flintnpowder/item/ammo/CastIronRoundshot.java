package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;

import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class CastIronRoundshot extends BaseAmmo {
    public CastIronRoundshot(Properties pProperties) {
        super(pProperties);
        this.damage = 18;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, ItemStack gun, Level level) {
        CastIronRoundshotProjectile proj = new CastIronRoundshotProjectile(level, shooter);

        proj.damage = this.damage * ((GunBase) gun.getItem()).damageModifier(shooter.getUUID(), gun);
        proj.setOwner(shooter);

        proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter), CameraWork.getPlayerViewY(shooter), 0.0F, 10F, 4F * ((GunBase) gun.getItem()).accuracyModifier(shooter.getUUID(), gun));

        // Recoil

        if (shooter instanceof Player) {
            Random rand = new Random();
            float angleX = rand.nextFloat(4.0F);
            OffsetEntityCamera(shooter, (-15 + (angleX - 2)) * ((GunBase) gun.getItem()).recoilModifierX(shooter.getUUID(), gun), (angleX - 2) * ((GunBase) gun.getItem()).recoilModifierY(shooter.getUUID(), gun));
        }

        level.addFreshEntity(proj);
    }
}
