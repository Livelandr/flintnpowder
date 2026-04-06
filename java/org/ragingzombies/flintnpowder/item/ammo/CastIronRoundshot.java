package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class CastIronRoundshot extends BaseAmmo {
    public CastIronRoundshot(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        Arrow snowball = new Arrow(level, shooter);

        snowball.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 25F, 0F);

        level.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.MUSKETFIRE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        level.addFreshEntity(snowball);
    }
}
