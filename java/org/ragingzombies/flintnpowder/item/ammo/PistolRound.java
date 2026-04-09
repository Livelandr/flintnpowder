package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class PistolRound extends BaseAmmo {
    public PistolRound(Properties pProperties) {
        super(pProperties);

        damage = 6;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        CastIronRoundshotProjectile proj = new CastIronRoundshotProjectile(level, shooter);

        proj.damage = this.damage * gun.damageModifier();
        proj.setOwner(shooter);
        proj.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 10F, 2F * gun.accuracyModifier());

        // Recoil
        Random rand = new Random();
        float angleX = rand.nextFloat(4.0F);
        OffsetEntityCamera(shooter,(-7+(angleX-2))*gun.recoilModifierX(),(angleX-2)*gun.recoilModifierY());

        level.addFreshEntity(proj);
    }
}
