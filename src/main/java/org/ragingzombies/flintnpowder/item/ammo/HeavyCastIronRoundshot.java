package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.HeavyCastIronRoundshotProjectile;

import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class HeavyCastIronRoundshot extends BaseAmmo {
    public HeavyCastIronRoundshot(Properties pProperties) {
        super(pProperties);
        this.damage = 24;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        HeavyCastIronRoundshotProjectile proj = new HeavyCastIronRoundshotProjectile(level, shooter);

        proj.damage = this.damage * gun.damageModifier();
        proj.setOwner(shooter);

        proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter), CameraWork.getPlayerViewY(shooter), 0.0F, 5F, 3F * gun.accuracyModifier(shooter.getUUID()));

        // Recoil

        if (shooter instanceof Player) {
            Random rand = new Random();
            float angleX = rand.nextFloat(4.0F);
            OffsetEntityCamera(shooter, (-15 + (angleX - 2)) * gun.recoilModifierX(shooter.getUUID()), (angleX - 2) * gun.recoilModifierY(shooter.getUUID()));
        }

        level.addFreshEntity(proj);
    }
}
