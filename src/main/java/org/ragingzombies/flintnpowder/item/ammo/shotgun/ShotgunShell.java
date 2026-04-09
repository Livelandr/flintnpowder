package org.ragingzombies.flintnpowder.item.ammo.shotgun;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.BuckshotProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class ShotgunShell extends BaseAmmo {
    public ShotgunShell(Properties pProperties) {
        super(pProperties);
        this.damage = 1.1F;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            float angle = rand.nextFloat((float) (2.0F*Math.PI));
            float radius = rand.nextFloat(25);

            BuckshotProjectile proj = new BuckshotProjectile(level, shooter);

            proj.setOwner(shooter);
            proj.shootFromRotation(shooter, shooter.getXRot() + (float)(Math.cos(angle)*radius),
                    shooter.getYRot() + (float)(Math.sin(angle)*radius), 0.0F, 5F,2 * gun.accuracyModifier());
            proj.SetDamage(this.damage * gun.damageModifier());

            level.addFreshEntity(proj);
        }

        // Recoil
        float angleX = rand.nextFloat(4.0F);
        OffsetEntityCamera(shooter,(-25+(angleX-2))*gun.recoilModifierX(),(angleX-2)*gun.recoilModifierY());
    }
}
