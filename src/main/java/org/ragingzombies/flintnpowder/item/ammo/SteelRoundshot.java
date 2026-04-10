package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.SteelRoundshotProjectile;

import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class SteelRoundshot extends BaseAmmo {
    public SteelRoundshot(Properties pProperties) {
        super(pProperties);
        this.damage = 21;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        SteelRoundshotProjectile proj = new SteelRoundshotProjectile(level, shooter);

        proj.damage = this.damage * gun.damageModifier();
        proj.setOwner(shooter);
        proj.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 10F, 2F * gun.accuracyModifier());

        // Recoil
        Random rand = new Random();
        float angleX = rand.nextFloat(4.0F);
        OffsetEntityCamera(shooter,(-15+(angleX-2))*gun.recoilModifierX(),(angleX-2)*gun.recoilModifierY());

        level.addFreshEntity(proj);
    }
}
