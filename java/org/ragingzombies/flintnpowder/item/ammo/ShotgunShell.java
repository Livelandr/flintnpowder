package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import java.util.Random;

public class ShotgunShell extends BaseAmmo {
    public ShotgunShell(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        Random rand = new Random();
        for (int i = 0; i < 120; i++) {
            float angle = rand.nextFloat((float) (2.0F*Math.PI));
            float radius = rand.nextFloat(25);

            Arrow snowball = new Arrow(level, shooter);
            snowball.shootFromRotation(shooter, shooter.getXRot() + (float)(Math.cos(angle)*radius), shooter.getYRot() + (float)(Math.sin(angle)*radius), 0.0F, 15F, 0F);

            level.addFreshEntity(snowball);
        }

        level.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.MUSKETFIRE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);
    }
}
