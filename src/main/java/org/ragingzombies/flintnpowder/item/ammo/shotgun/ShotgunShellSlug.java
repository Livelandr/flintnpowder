package org.ragingzombies.flintnpowder.item.ammo.shotgun;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
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
    public void onAmmoShot(LivingEntity shooter, ItemStack gun, Level level) {
        Random rand = new Random();

        SlugProjectile proj = new SlugProjectile(level, shooter);

        proj.damage = this.damage * ((GunBase) gun.getItem()).damageModifier(shooter.getUUID(), gun);
        proj.setOwner(shooter);
        proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter), CameraWork.getPlayerViewY(shooter), 0.0F, 3F, 0.1F * ((GunBase) gun.getItem()).accuracyModifier(shooter.getUUID(), gun));

        level.addFreshEntity(proj);

        // Recoil

        if (shooter instanceof Player) {
            float angleX = rand.nextFloat(5.0F);
            OffsetEntityCamera(shooter, (-30 + (angleX - 2)) * ((GunBase) gun.getItem()).recoilModifierX(shooter.getUUID(), gun), (angleX - 2) * ((GunBase) gun.getItem()).recoilModifierY(shooter.getUUID(), gun));
        }
    }
}
