package org.ragingzombies.flintnpowder.item.ammo.shotgun;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.SlugProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class ShotgunShellSlug extends BaseAmmo {
    public ShotgunShellSlug(Properties pProperties) {
        super(pProperties);
        this.damage = 25;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        Random rand = new Random();

        SlugProjectile proj = new SlugProjectile(level, shooter);

        proj.damage = this.damage * gun.damageModifier();
        proj.setOwner(shooter);
        proj.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 3F, 1.1F * gun.accuracyModifier());

        level.addFreshEntity(proj);

        // Recoil
        float angleX = rand.nextFloat(5.0F);
        OffsetEntityCamera(shooter,(-30+(angleX-2))*gun.recoilModifierX(),(angleX-2)*gun.recoilModifierY());
    }
}
